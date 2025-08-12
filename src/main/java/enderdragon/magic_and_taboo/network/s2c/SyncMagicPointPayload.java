package enderdragon.magic_and_taboo.network.s2c;

import net.minecraft.network.FriendlyByteBuf;

public record SyncMagicPointPayload(int magic) {
    public SyncMagicPointPayload(FriendlyByteBuf buffer) {
        this(buffer.readVarInt());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeVarInt(this.magic);
    }
}
