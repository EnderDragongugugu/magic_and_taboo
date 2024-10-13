package enderdragon.magicandtaboo.client;

import enderdragon.magicandtaboo.MagicAndTabooMod;
import enderdragon.magicandtaboo.capability.IPurenessStorage;
import enderdragon.magicandtaboo.client.gui.MercuryToxinsOverlay;
import enderdragon.magicandtaboo.client.gui.WorkHubScreen;
import enderdragon.magicandtaboo.init.MATBlocks;
import enderdragon.magicandtaboo.init.MATItems;
import enderdragon.magicandtaboo.init.MATMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static enderdragon.magicandtaboo.init.MATCapabilities.PURENESS;

@Mod.EventBusSubscriber(modid = MagicAndTabooMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MATClient {
    @SubscribeEvent
    public static void init(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            Sheets.addWoodType(MATBlocks.FIR_WOOD_TYPE);
            MenuScreens.register(MATMenuTypes.WORK_HUB.get(), WorkHubScreen::new);
        });
    }

    @SubscribeEvent
    public static void modelBake(ModelEvent.ModifyBakingResult event) {
        final ResourceLocation bloody = new ResourceLocation("bloody");
        ClampedItemPropertyFunction isBloody = (stack, $, entity, i) -> stack.getCapability(PURENESS).orElse(IPurenessStorage.EMPTY).isValid() ? 1.0F : 0.0F;
        ItemProperties.register(MATItems.SACRIFICIAL_DAGGER.get(), bloody, isBloody);
    }

    @SubscribeEvent
    public static void registerOverlay(RegisterGuiOverlaysEvent event) {
        event.registerAbove(new ResourceLocation("player_health"), "mercury_toxins", new MercuryToxinsOverlay());
    }

    @SubscribeEvent
    public static void loadItemGroup(BuildCreativeModeTabContentsEvent event) {
        var tab = event.getTabKey();
        if (CreativeModeTabs.BUILDING_BLOCKS.equals(tab)) {
            event.accept(MATItems.FIR_LOG);
            event.accept(MATItems.FIR_WOOD);
            event.accept(MATItems.STRIPPED_FIR_LOG);
            event.accept(MATItems.STRIPPED_FIR_WOOD);
            event.accept(MATItems.FIR_PLANKS);
            event.accept(MATItems.FIR_STAIRS);
            event.accept(MATItems.FIR_SLAB);
            event.accept(MATItems.FIR_FENCE);
            event.accept(MATItems.FIR_FENCE_GATE);
            event.accept(MATItems.FIR_DOOR);
            event.accept(MATItems.FIR_TRAPDOOR);
            event.accept(MATItems.FIR_PRESSURE_PLATE);
            event.accept(MATItems.FIR_BUTTON);
            event.accept(MATItems.GILDED_MARBLE);
            event.accept(MATItems.GILDED_MARBLE_STAIRS);
            event.accept(MATItems.GILDED_MARBLE_SLAB);
            event.accept(MATItems.GILDED_MARBLE_WALL);
            event.accept(MATItems.CHISELED_GILDED_MARBLE);
            event.accept(MATItems.POLISHED_GILDED_MARBLE);
            event.accept(MATItems.POLISHED_GILDED_MARBLE_STAIRS);
            event.accept(MATItems.POLISHED_GILDED_MARBLE_SLAB);
            event.accept(MATItems.POLISHED_GILDED_MARBLE_WALL);
            event.accept(MATItems.POLISHED_GILDED_MARBLE_PRESSURE_PLATE);
            event.accept(MATItems.POLISHED_GILDED_MARBLE_BUTTON);
        } else if (CreativeModeTabs.NATURAL_BLOCKS.equals(tab)) {
            event.accept(MATItems.MERCURY_ORE);
            event.accept(MATItems.FIR_LOG);
            event.accept(MATItems.FIR_LEAVES);
            event.accept(MATItems.FIR_SAPLING);
        } else if (CreativeModeTabs.FUNCTIONAL_BLOCKS.equals(tab)) {
            event.accept(MATItems.FIR_SIGN);
            event.accept(MATItems.FIR_HANGING_SIGN);
        }
    }
}
