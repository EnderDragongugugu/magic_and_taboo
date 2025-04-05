package enderdragon.magic_and_taboo.network;

import enderdragon.magic_and_taboo.client.gui.OccultCodexScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class OccultCodexGUIPacket {
    public static void encode(OccultCodexGUIPacket packet, FriendlyByteBuf buffer) {
//        buffer.writeUtf();
    }

    public static OccultCodexGUIPacket decode(FriendlyByteBuf buffer) {
        return new OccultCodexGUIPacket();
    }

    public static void handle(OccultCodexGUIPacket packet, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        ctx.enqueueWork(() -> {
            Minecraft.getInstance().setScreen(new OccultCodexScreen());
        });
        ctx.setPacketHandled(true);
    }
}
