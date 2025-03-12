package enderdragon.magic_and_taboo.util;

import enderdragon.magic_and_taboo.init.MATElements;
import enderdragon.magic_and_taboo.registry.AlchemyElement;
import enderdragon.magic_and_taboo.registry.Element;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatMaps;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.resources.ResourceKey;

import java.util.HashMap;
import java.util.Map;

public class ElementUtil {
    public static Map<String, Holder<Element>> getElements(HolderGetter<Element> registry) {
        Map<String, Holder<Element>> elements = new HashMap<>();
        elements.put("mercury", get(registry, MATElements.MERCURY));
        elements.put("nausea", get(registry, MATElements.CONFUSION));
        elements.put("night_vision", get(registry, MATElements.NIGHT_VISION));
        return elements;
    }

    public static Holder<Element> get(HolderGetter<Element> registry, ResourceKey<Element> key) {
        return registry.getOrThrow(key);
    }

    public static class Builder {
        public final Object2FloatMap<Holder<Element>> elements = new Object2FloatOpenHashMap<>();

        public Builder put(Holder<Element> element, float count) {
            this.elements.put(element, count);
            return this;
        }

        public AlchemyElement build(int time) {
            return new AlchemyElement(Object2FloatMaps.unmodifiable(this.elements), time);
        }

        public Object2FloatMap<Holder<Element>> build() {
            return elements;
        }
    }

}
