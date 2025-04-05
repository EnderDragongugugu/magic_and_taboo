package enderdragon.magic_and_taboo.network;

import enderdragon.magic_and_taboo.MagicAndTabooMod;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {
    private static final String PROTOCOL_VERSION = "1.0";
    private static int packetId = 0;

    public static SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            MagicAndTabooMod.makeId("main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void register() {
        INSTANCE.registerMessage(packetId++, OccultCodexGUIPacket.class, OccultCodexGUIPacket::encode, OccultCodexGUIPacket::decode, OccultCodexGUIPacket::handle);
    }

    public static <MSG> void sendToPlayer(ServerPlayer player, MSG msg) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), msg);
    }

    public static <MSG> void sendToAllPlayer(MSG msg) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), msg);
    }
}
