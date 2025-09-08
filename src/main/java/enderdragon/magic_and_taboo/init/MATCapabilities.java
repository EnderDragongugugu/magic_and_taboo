package enderdragon.magic_and_taboo.init;

import enderdragon.magic_and_taboo.capability.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.event.AttachCapabilitiesEvent;


public class MATCapabilities {
    public static final Capability<PurenessStorage> PURENESS = CapabilityManager.get(new CapabilityToken<>() {});
    public static final Capability<MagicPotion> MAGIC_POTION = CapabilityManager.get(new CapabilityToken<>() {});
    public static final Capability<ElementSource> ELEMENT_SOURCE = CapabilityManager.get(new CapabilityToken<>() {});
    public static final Capability<ElementStorage> ELEMENT_STORAGE = CapabilityManager.get(new CapabilityToken<>() {});
    public static final Capability<PlayerMagicPoint> PLAYER_MAGIC_POINT = CapabilityManager.get(new CapabilityToken<>() {});

    public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player player) {
            event.addCapability(PlayerMagicPoint.IDENTIFIER, new PlayerMagicPointImpl(player));
        }
    }
}
