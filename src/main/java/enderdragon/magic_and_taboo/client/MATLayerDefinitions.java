package enderdragon.magic_and_taboo.client;

import enderdragon.magic_and_taboo.MagicAndTabooMod;
import enderdragon.magic_and_taboo.client.model.WorkHubToolModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MagicAndTabooMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MATLayerDefinitions {
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(WorkHubToolModel.LAYER_LOCATION, WorkHubToolModel::createBodyLayer);
    }
}
