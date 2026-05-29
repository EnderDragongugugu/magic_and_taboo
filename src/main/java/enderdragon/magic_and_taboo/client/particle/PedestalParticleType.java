package enderdragon.magic_and_taboo.client.particle;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.util.StringRepresentable;

public enum PedestalParticleType implements StringRepresentable {
    NONE("none"),
    RISE("rise"),
    SPIRAL("spiral");

    public static final Codec<PedestalParticleType> CODEC = StringRepresentable.fromEnum(PedestalParticleType::values);
    public static final MapCodec<PedestalParticleType> MAP_CODEC = CODEC.fieldOf("particle");

    private final String name;

    PedestalParticleType(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
