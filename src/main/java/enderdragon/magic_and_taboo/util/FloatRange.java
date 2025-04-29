package enderdragon.magic_and_taboo.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.floats.FloatPredicate;
import net.minecraft.network.chat.Component;

import java.util.function.Function;

public record FloatRange(float min, float max) implements FloatPredicate {
    public static final Codec<FloatRange> CODEC = RecordCodecBuilder.<FloatRange>create(instance -> instance.group(
            Codec.FLOAT.fieldOf("min").forGetter(FloatRange::min),
            Codec.FLOAT.fieldOf("max").forGetter(FloatRange::max)
    ).apply(instance, FloatRange::new)).comapFlatMap(FloatRange::validate, Function.identity());

    @Override
    public boolean test(float value) {
        return value >= this.min && value <= this.max;
    }

    public float normalize(float value) {
        return value <= this.min
                ? 0
                : value >= this.max
                ? 1
                : (value - this.min) / (this.max - this.min);
    }

    public Component format(String translationKey) {
        return Component.translatable(translationKey, this.min, this.max);
    }

    public DataResult<FloatRange> validate() {
        return this.max < this.min
                ? DataResult.error(() -> "Max must be larger than min: [" + this.min + ", " + this.max + "]")
                : DataResult.success(this);
    }
}
