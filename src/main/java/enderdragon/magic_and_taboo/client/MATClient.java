package enderdragon.magic_and_taboo.client;

import com.mojang.datafixers.util.Either;
import enderdragon.magic_and_taboo.MagicAndTabooMod;
import enderdragon.magic_and_taboo.capability.MagicPotion;
import enderdragon.magic_and_taboo.capability.PurenessStorage;
import enderdragon.magic_and_taboo.capability.WorkHubResult;
import enderdragon.magic_and_taboo.client.gui.*;
import enderdragon.magic_and_taboo.client.model.WorkHubToolModel;
import enderdragon.magic_and_taboo.client.renderer.EnchantedCrucibleRender;
import enderdragon.magic_and_taboo.client.renderer.PedestalRender;
import enderdragon.magic_and_taboo.client.renderer.WorkHubRender;
import enderdragon.magic_and_taboo.init.*;
import enderdragon.magic_and_taboo.item.AlchemyElementItem;
import enderdragon.magic_and_taboo.registry.AlchemyElement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;

import static enderdragon.magic_and_taboo.init.MATCapabilities.PURENESS;

@Mod.EventBusSubscriber(modid = MagicAndTabooMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MATClient {
    @Mod.EventBusSubscriber(modid = MagicAndTabooMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeBusEvent {
        @SubscribeEvent
        public static void onGatherTooltips(RenderTooltipEvent.GatherComponents event) {
            var stack = event.getItemStack();
            var level = Minecraft.getInstance().level;
            var key = ForgeRegistries.ITEMS.getKey(stack.getItem());
            if (level == null || key == null) return;
            level.registryAccess().registryOrThrow(AlchemyElement.RESOURCE_KEY).getOptional(
                    ResourceKey.create(AlchemyElement.RESOURCE_KEY, key)
            ).ifPresent(element -> event.getTooltipElements().add(1, Either.right(element)));
        }

    }

    @SubscribeEvent
    public static void init(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            Sheets.addWoodType(MATBlocks.FIR_WOOD_TYPE);
            MenuScreens.register(MATMenuTypes.WORK_HUB.get(), WorkHubScreen::new);
        });
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register((stack, layer) -> layer > 0 ? -1 :
                        stack.getCapability(MATCapabilities.MAGIC_POTION).orElse(MagicPotion.EMPTY).getColor(),
                MATItems.POTION_BOTTLE.get(),
                MATItems.POTION_BOTTLE_RED.get(),
                MATItems.POTION_BOTTLE_GLOW.get(),
                MATItems.POTION_SYRINGE.get()
        );
        event.register(AlchemyElementItem::getColor, MATItems.ALCHEMY_ELEMENT.get());
    }

    @SubscribeEvent
    public static void registerClientTooltipComponentFactoriesEvent(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(WorkHubResult.class, WorkHubTooltip::new);
        event.register(AlchemyElement.class, AlchemyElementTooltip::new);
    }

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(MATBlockEntities.ENCHANTED_CRUCIBLE.get(), EnchantedCrucibleRender::new);
        event.registerBlockEntityRenderer(MATBlockEntities.MAGIC_PERFUSION_PEDESTAL.get(), PedestalRender::new);
        event.registerBlockEntityRenderer(MATBlockEntities.PEDESTAL.get(), PedestalRender::new);
        event.registerBlockEntityRenderer(MATBlockEntities.WORK_HUB.get(), WorkHubRender::new);
    }

    @SubscribeEvent
    public static void modelBake(ModelEvent.ModifyBakingResult event) {
        var bloody = new ResourceLocation("bloody");
        ClampedItemPropertyFunction isBloody = (stack, $, entity, i) -> stack.getCapability(PURENESS).orElse(PurenessStorage.EMPTY).isValid() ? 1.0F : 0.0F;
        ItemProperties.register(MATItems.SACRIFICIAL_DAGGER.get(), bloody, isBloody);
    }

    @SubscribeEvent
    public static void registerOverlay(RegisterGuiOverlaysEvent event) {
        event.registerAbove(VanillaGuiOverlay.PLAYER_HEALTH.id(), "mercury_toxins", new MercuryToxinsOverlay());
        event.registerAbove(VanillaGuiOverlay.CROSSHAIR.id(), "sacrificial_dagger", new SacrificialDaggerOverlay());
        event.registerAbove(VanillaGuiOverlay.CROSSHAIR.id(), "magic_point", new PlayerMagicPointScreen());
    }


    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(WorkHubToolModel.LAYER_LOCATION, WorkHubToolModel::createBodyLayer);
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
