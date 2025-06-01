package enderdragon.magic_and_taboo.util;

import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.Level;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public abstract class RegistryAccessor {
    /// get server side registries
    public static @Nullable RegistryAccess getOptionalRegistries() {
        var server = ServerLifecycleHooks.getCurrentServer();
        return server == null ? null : server.registryAccess();
    }

    public static @NotNull RegistryAccess getRegistries(@Nullable Level level) {
        return level == null ? Objects.requireNonNull(getOptionalRegistries()) : level.registryAccess();
    }

    private RegistryAccessor() {}
}
