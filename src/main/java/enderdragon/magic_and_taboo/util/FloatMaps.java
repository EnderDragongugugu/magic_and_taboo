package enderdragon.magic_and_taboo.util;

import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatMaps;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;

import javax.annotation.Nullable;
import java.util.Map;

public class FloatMaps {
    public static <T> Object2FloatMap<T> unmodifiableCopy(@Nullable Map<T, Float> map) {
        return Object2FloatMaps.unmodifiable(map == null ? new Object2FloatOpenHashMap<>() : new Object2FloatOpenHashMap<>(map));
    }

    public static <T> Object2FloatMap<T> defaultedUnmodifiableCopy(@Nullable Map<T, Float> map) {
        var copy = map == null ? new Object2FloatOpenHashMap<T>() : new Object2FloatOpenHashMap<>(map);
        copy.defaultReturnValue(1.0F);
        return Object2FloatMaps.unmodifiable(map == null ? new Object2FloatOpenHashMap<>() : new Object2FloatOpenHashMap<>(map));
    }

    public record Builder<T>(Object2FloatMap<T> map) {
        public Builder() {
            this(new Object2FloatOpenHashMap<>());
        }

        public Builder<T> put(T element, float count) {
            this.map.put(element, count);
            return this;
        }

        public Object2FloatMap<T> build() {
            return unmodifiableCopy(this.map);
        }
    }
}
