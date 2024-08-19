package enderdragon.magicandtaboo.init;

import enderdragon.magicandtaboo.capability.IPurenessStorage;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;


public class MATCapabilities {
    public static final Capability<IPurenessStorage> PURENESS = CapabilityManager.get(new CapabilityToken<>() {});
}
