package enderdragon.magic_and_taboo.network;

import enderdragon.magic_and_taboo.MagicAndTabooMod;
import enderdragon.magic_and_taboo.init.MATCapabilities;
import enderdragon.magic_and_taboo.network.s2c.InitMagicPointPayload;
import enderdragon.magic_and_taboo.network.s2c.SyncMagicPointPayload;
import enderdragon.magic_and_taboo.util.CapabilityUtil;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class NetworkHandler {
    private static final String PROTOCOL_VERSION = "1.0";

    public static SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            MagicAndTabooMod.makeId("main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void register() {
        int uid = 0;
        CHANNEL.registerMessage(++uid, InitMagicPointPayload.class, InitMagicPointPayload::encode, InitMagicPointPayload::new, NetworkHandler::handle);
        CHANNEL.registerMessage(++uid, SyncMagicPointPayload.class, SyncMagicPointPayload::encode, SyncMagicPointPayload::new, NetworkHandler::handle);
    }

    public static void handle(Supplier<NetworkEvent.Context> supplier, Runnable action) {
        var context = supplier.get();
        context.enqueueWork(action);
        context.setPacketHandled(true);
    }

    public static void handle(InitMagicPointPayload packet, Supplier<NetworkEvent.Context> context) {
        if (FMLLoader.getDist().isDedicatedServer()) return;
        handle(context, () -> {
            var player = Minecraft.getInstance().player;
            if (player == null) return;
            var capability = CapabilityUtil.getCapability(player, MATCapabilities.PLAYER_MAGIC_POINT);
            if (capability == null) return;
            capability.setMagic(packet.max(), packet.magic());
        });
    }

    public static void handle(SyncMagicPointPayload packet, Supplier<NetworkEvent.Context> context) {
        if (FMLLoader.getDist().isDedicatedServer()) return;
        handle(context, () -> {
            var player = Minecraft.getInstance().player;
            if (player == null) return;
            var capability = CapabilityUtil.getCapability(player, MATCapabilities.PLAYER_MAGIC_POINT);
            if (capability == null) return;
            capability.setMagic(packet.magic());
        });
    }
}
