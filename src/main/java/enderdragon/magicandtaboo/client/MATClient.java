package enderdragon.magicandtaboo.client;

import enderdragon.magicandtaboo.client.gui.MercuryToxinsOverlay;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class MATClient {
    public static final void init(final FMLClientSetupEvent event){
        MercuryToxinsOverlay.init();
    }
}
