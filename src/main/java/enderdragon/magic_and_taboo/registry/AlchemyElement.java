package enderdragon.magic_and_taboo.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import enderdragon.magic_and_taboo.MagicAndTabooMod;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatMaps;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.inventory.tooltip.TooltipComponent;

import java.util.Map;
import java.util.function.Function;

public record AlchemyElement(Object2FloatMap<Holder<Element>> elementMap, int time) implements TooltipComponent {
    public static final ResourceKey<Registry<AlchemyElement>> RESOURCE_KEY = ResourceKey.createRegistryKey(MagicAndTabooMod.makeId("alchemy_element"));
    public static final Codec<AlchemyElement> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.unboundedMap(RegistryFixedCodec.create(Element.RESOURCE_KEY), Codec.FLOAT)
                    .xmap(AlchemyElement::of, Function.identity())
                    .fieldOf("alchemy_element").forGetter(AlchemyElement::elementMap),
            Codec.INT.fieldOf("max_time").forGetter(AlchemyElement::time)
    ).apply(instance, AlchemyElement::new));

    public static class Builder {
        public final Object2FloatMap<Holder<Element>> elements = new Object2FloatOpenHashMap<>();

        public Builder put(Holder<Element> element, float count) {
            this.elements.put(element, count);
            return this;
        }

        public AlchemyElement build(int time) {
            return new AlchemyElement(Object2FloatMaps.unmodifiable(this.elements), time);
        }
    }

    public static Object2FloatMap<Holder<Element>> of(Map<Holder<Element>, Float> element) {
        var copy = element == null ? new Object2FloatOpenHashMap<Holder<Element>>() : new Object2FloatOpenHashMap<>(element);
        copy.defaultReturnValue(1.0F);
        return Object2FloatMaps.unmodifiable(copy);
    }
}
