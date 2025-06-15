package enderdragon.magic_and_taboo.util;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;

public class CapabilityUtil {
    @SuppressWarnings("DataFlowIssue")
    public static <T> @Nullable T getCapability(ICapabilityProvider provider, Capability<T> capability) {
        return provider.getCapability(capability).orElse(null);
    }
}
