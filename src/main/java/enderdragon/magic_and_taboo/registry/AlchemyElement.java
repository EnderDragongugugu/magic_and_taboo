package enderdragon.magic_and_taboo.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import enderdragon.magic_and_taboo.capability.ElementHolder;
import enderdragon.magic_and_taboo.init.MATCapabilities;
import enderdragon.magic_and_taboo.init.MATItems;
import enderdragon.magic_and_taboo.util.FloatMaps;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.function.Function;

import static enderdragon.magic_and_taboo.MagicAndTabooMod.makeId;

public record AlchemyElement(Object2FloatMap<Holder<Element>> elementMap, int time) implements TooltipComponent {
    public static final ResourceKey<Registry<AlchemyElement>> RESOURCE_KEY = ResourceKey.createRegistryKey(makeId("alchemy_element"));
    public static final Codec<AlchemyElement> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.unboundedMap(RegistryFixedCodec.create(Element.RESOURCE_KEY), Codec.FLOAT)
                    .xmap(FloatMaps::defaultedUnmodifiableCopy, Function.identity())
                    .fieldOf("alchemy_element").forGetter(AlchemyElement::elementMap),
            Codec.INT.fieldOf("max_time").forGetter(AlchemyElement::time)
    ).apply(instance, AlchemyElement::new));

    public static @Nullable AlchemyElement fromStack(RegistryAccess registry, ItemStack stack) {
        if (stack.is(MATItems.ALCHEMY_ELEMENT.get())) {
            var holder = stack.getCapability(MATCapabilities.ELEMENT_HOLDER).orElse(ElementHolder.EMPTY);
            if (holder.getElement() == null) return null;
            var elements = new Object2FloatOpenHashMap<Holder<Element>>();
            elements.put(registry.registryOrThrow(Element.RESOURCE_KEY).wrapAsHolder(holder.getElement()), holder.getAmount());
            return new AlchemyElement(elements, 20);
        }
        return registry.registryOrThrow(AlchemyElement.RESOURCE_KEY).get(ForgeRegistries.ITEMS.getKey(stack.getItem()));
    }
}
