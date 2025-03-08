package enderdragon.magic_and_taboo.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.function.Function;

public record FloatRange(float min, float max) {
    public static final Codec<FloatRange> CODEC = RecordCodecBuilder.<FloatRange>create(instance -> instance.group(
            Codec.FLOAT.fieldOf("min").forGetter(FloatRange::min),
            Codec.FLOAT.fieldOf("max").forGetter(FloatRange::max)
    ).apply(instance, FloatRange::new)).comapFlatMap(range -> range.max < range.min
                    ? DataResult.error(() -> "Max must be larger than min: [" + range.min + ", " + range.max + "]")
                    : DataResult.success(range),
            Function.identity()
    );
}
