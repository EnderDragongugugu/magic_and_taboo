package net.magic_and_taboo.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.magic_and_taboo.MagicAndTabooMod;
import net.magic_and_taboo.client.gui.screen.SpyglassSextantScreen;
import net.magic_and_taboo.init.MATBlocks;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class MagicAndTabooClient implements ClientModInitializer {
    public void onInitializeClient() {
        HandledScreens.register(MagicAndTabooMod.SPYGLASS_SEXTANT_SCREEN_HANDLER, SpyglassSextantScreen::new);
        BlockRenderLayerMap.INSTANCE.putBlock(MATBlocks.SPYGLASS_SEXTANT_BLOCK, RenderLayer.getCutout());
    }
}