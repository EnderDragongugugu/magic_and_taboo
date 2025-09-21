package enderdragon.magic_and_taboo.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import enderdragon.magic_and_taboo.init.MATCapabilities;
import enderdragon.magic_and_taboo.tag.MATItemTags;
import enderdragon.magic_and_taboo.util.CapabilityUtil;
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

public record AlchemyElement(Object2FloatMap<Holder<Element>> concentrations, int time) implements TooltipComponent {
    public static final ResourceKey<Registry<AlchemyElement>> RESOURCE_KEY = ResourceKey.createRegistryKey(makeId("alchemy_element"));
    public static final Codec<AlchemyElement> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.unboundedMap(RegistryFixedCodec.create(Element.RESOURCE_KEY), Codec.FLOAT)
                    .xmap(FloatMaps::defaultedUnmodifiableCopy, Function.identity())
                    .fieldOf("alchemy_element").forGetter(AlchemyElement::concentrations),
            Codec.INT.fieldOf("max_time").forGetter(AlchemyElement::time)
    ).apply(instance, AlchemyElement::new));

    public static @Nullable AlchemyElement fromStack(RegistryAccess registries, ItemStack stack) {
        if (stack.is(MATItemTags.IS_ALCHEMY)) {
            var source = CapabilityUtil.getCapability(stack, MATCapabilities.ELEMENT_SOURCE);
            if (source != null) {
                var registry = registries.registryOrThrow(Element.RESOURCE_KEY);
                var concentrations = new Object2FloatOpenHashMap<Holder<Element>>();
                for (var entry : source.getConcentrations().reference2FloatEntrySet()) {
                    concentrations.put(registry.wrapAsHolder(entry.getKey()), entry.getFloatValue());
                }
                return new AlchemyElement(concentrations, 20);
            }
        }
        return registries.registryOrThrow(RESOURCE_KEY).get(ForgeRegistries.ITEMS.getKey(stack.getItem()));
    }
}
