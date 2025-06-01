package enderdragon.magic_and_taboo.init;

import enderdragon.magic_and_taboo.capability.AlchemyElement;
import enderdragon.magic_and_taboo.capability.MagicPotion;
import enderdragon.magic_and_taboo.capability.PurenessStorage;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;


public class MATCapabilities {
    public static final Capability<PurenessStorage> PURENESS = CapabilityManager.get(new CapabilityToken<>() {
    });
    public static final Capability<MagicPotion> MAGIC_POTION = CapabilityManager.get(new CapabilityToken<>() {
    });
    public static final Capability<AlchemyElement> ALCHEMY_ELEMENT = CapabilityManager.get(new CapabilityToken<>() {
    });
}
