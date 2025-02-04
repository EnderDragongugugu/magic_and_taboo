package enderdragon.magic_and_taboo.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import enderdragon.magic_and_taboo.MagicAndTabooMod;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatMaps;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;

public class AlchemyElement {
    //    public static final AlchemyElement DEFAULT = of(null);
    public static final ResourceKey<Registry<AlchemyElement>> RESOURCE_KEY = ResourceKey.createRegistryKey(MagicAndTabooMod.makeId("alchemy_element"));
    public static final Codec<AlchemyElementMap> MAP_CODEC = Codec.unboundedMap(RegistryFixedCodec.create(Element.RESOURCE_KEY), Codec.FLOAT).xmap(AlchemyElementMap::of, AlchemyElementMap::getElement);
    public static final Codec<AlchemyElement> CODEC = RecordCodecBuilder.create(i -> i.group(
            MAP_CODEC.fieldOf("alchemy_element").forGetter(AlchemyElement::getElementMap),
            Codec.INT.fieldOf("max_time").forGetter(AlchemyElement::getTime)
    ).apply(i, AlchemyElement::new));
    public final AlchemyElementMap elementMap;
    public final int time;

    public AlchemyElement(AlchemyElementMap elementMap, int time) {
        this.elementMap = elementMap;
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public AlchemyElementMap getElementMap() {
        return elementMap;
    }

    public static class AlchemyElementMap {
        public final Object2FloatMap<Holder<Element>> element;

        public static AlchemyElementMap of(Map<Holder<Element>, Float> element) {
            var copy = element == null ? new Object2FloatOpenHashMap<Holder<Element>>() : new Object2FloatOpenHashMap<>(element);
            copy.defaultReturnValue(1.0F);
            return new AlchemyElementMap(copy);
        }

        private AlchemyElementMap(Object2FloatMap<Holder<Element>> element) {
            this.element = Object2FloatMaps.unmodifiable(element);
        }

        public Object2FloatMap<Holder<Element>> getElement() {
            return this.element;
        }

        public static class Builder {
            public final Object2FloatMap<Holder<Element>> element = new Object2FloatOpenHashMap<>();

            public Builder put(Holder<Element> element, float count) {
                this.element.put(element, count);
                return this;
            }

            public AlchemyElementMap build() {
                return new AlchemyElementMap(element);
            }
        }
    }


    public void register(BootstapContext<AlchemyElement> context, Item item) {
        var key = ForgeRegistries.ITEMS.getKey(item);
        if (key == null) throw new NullPointerException();
        context.register(ResourceKey.create(AlchemyElement.RESOURCE_KEY, key), this);
    }
}
