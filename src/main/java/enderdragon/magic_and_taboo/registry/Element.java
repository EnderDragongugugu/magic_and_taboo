package enderdragon.magic_and_taboo.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import enderdragon.magic_and_taboo.MagicAndTabooMod;
import enderdragon.magic_and_taboo.util.FloatMaps;
import enderdragon.magic_and_taboo.util.FloatRange;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collection;
import java.util.function.Function;

public record Element(
        MobEffect effect,
        String translationKey,
        ResourceLocation icon,
        FloatRange concentration,
        FloatRange temperature,
        Object2FloatMap<Holder<Element>> resistanceElementMap,
        Object2FloatMap<Holder<Element>> fusionElementMap
) {
    public static final ResourceKey<Registry<Element>> RESOURCE_KEY = ResourceKey.createRegistryKey(MagicAndTabooMod.makeId("element"));
    public static final Codec<Element> CODEC = RecordCodecBuilder.create(elementInstance -> elementInstance.group(
            ForgeRegistries.MOB_EFFECTS.getCodec().fieldOf("effect").forGetter(Element::effect),
            Codec.STRING.fieldOf("name").forGetter(Element::translationKey),
            ResourceLocation.CODEC.fieldOf("icon").forGetter(Element::icon),
            FloatRange.CODEC.fieldOf("blood_drug_concentration").forGetter(Element::concentration),
            FloatRange.CODEC.fieldOf("temperature_range").forGetter(Element::temperature),
            Codec.unboundedMap(RegistryFixedCodec.create(Element.RESOURCE_KEY), Codec.FLOAT)
                    .xmap(FloatMaps::unmodifiableCopy, Function.identity())
                    .fieldOf("resistance_element").forGetter(Element::resistanceElementMap),
            Codec.unboundedMap(RegistryFixedCodec.create(Element.RESOURCE_KEY), Codec.FLOAT)
                    .xmap(FloatMaps::unmodifiableCopy, Function.identity())
                    .fieldOf("fusion_element").forGetter(Element::fusionElementMap)
    ).apply(elementInstance, Element::new));

    public static Object2FloatOpenHashMap<Element> fromStacks(RegistryAccess registry, Collection<ItemStack> stacks, int temperature) {
        var elements = new Object2FloatOpenHashMap<Element>();
        for (var stack : stacks) {
            if (stack.isEmpty()) continue;
            var instance = AlchemyElement.fromItem(registry, stack.getItem());
            if (instance == null) continue;
            for (var entry : instance.elementMap().object2FloatEntrySet()) {
                var element = entry.getKey().value();
                if (element.temperature().test(temperature)) {
                    elements.addTo(element, entry.getFloatValue());
                }
            }
        }
        return elements;
    }
}
