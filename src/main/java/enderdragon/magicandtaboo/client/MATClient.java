package enderdragon.magicandtaboo.client;

import enderdragon.magicandtaboo.MagicAndTabooMod;
import enderdragon.magicandtaboo.client.gui.MercuryToxinsOverlay;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = MagicAndTabooMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MATClient {
    public static void init(final FMLClientSetupEvent event) {
    }

    @SubscribeEvent
    public static void registerOverlay(RegisterGuiOverlaysEvent event) {
        event.registerAbove(new ResourceLocation("player_health"), "mercury_toxins", new MercuryToxinsOverlay());
    }
}
