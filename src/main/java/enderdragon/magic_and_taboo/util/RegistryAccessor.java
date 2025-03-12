package enderdragon.magic_and_taboo.util;

import net.minecraft.core.RegistryAccess;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.event.AddReloadListenerEvent;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public abstract class RegistryAccessor {
    private static RegistryAccess INSTANCE;

    @Nullable
    public static RegistryAccess access() {
        return INSTANCE;
    }

    public static class ServerHook implements Consumer<AddReloadListenerEvent> {
        @Override
        public void accept(AddReloadListenerEvent event) {
            INSTANCE = event.getRegistryAccess();
        }
    }

    public static class ClientHook implements Consumer<ClientPlayerNetworkEvent.LoggingIn> {
        @Override
        public void accept(ClientPlayerNetworkEvent.LoggingIn event) {
            if (!event.getConnection().isMemoryConnection()) {
                INSTANCE = event.getPlayer().level().registryAccess();
            }
        }
    }

    private RegistryAccessor() {
    }
}
