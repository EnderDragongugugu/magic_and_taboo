package enderdragon.magic_and_taboo.init;

import enderdragon.magic_and_taboo.capability.IMagicPotionData;
import enderdragon.magic_and_taboo.capability.IPurenessStorage;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;


public class MATCapabilities {
    public static final Capability<IPurenessStorage> PURENESS = CapabilityManager.get(new CapabilityToken<>() {
    });
    public static final Capability<IMagicPotionData> MAGIC_POTION_DATA = CapabilityManager.get(new CapabilityToken<>() {
    });
}
