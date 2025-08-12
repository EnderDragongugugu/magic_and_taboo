package enderdragon.magic_and_taboo.network.s2c;

import net.minecraft.network.FriendlyByteBuf;

public record InitMagicPointPayload(int max, int magic) {
    public InitMagicPointPayload(FriendlyByteBuf buffer) {
        this(buffer.readVarInt(), buffer.readVarInt());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeVarInt(this.max).writeVarInt(this.magic);
    }
}