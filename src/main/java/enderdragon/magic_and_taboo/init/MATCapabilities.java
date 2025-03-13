package enderdragon.magic_and_taboo.init;

import enderdragon.magic_and_taboo.capability.IMagicPotion;
import enderdragon.magic_and_taboo.capability.IPurenessStorage;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;


public class MATCapabilities {
    public static final Capability<IPurenessStorage> PURENESS = CapabilityManager.get(new CapabilityToken<>() {});
    public static final Capability<IMagicPotion> MAGIC_POTION = CapabilityManager.get(new CapabilityToken<>() {});
}
