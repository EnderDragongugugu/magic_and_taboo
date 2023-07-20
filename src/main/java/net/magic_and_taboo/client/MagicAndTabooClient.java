package net.magic_and_taboo.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.magic_and_taboo.MagicAndTabooMod;
import net.magic_and_taboo.client.gui.screen.SpyglassSextantUIScreen;
import net.magic_and_taboo.client.gui.screen.handler.SpyglassSextantUIScreenHandler;
import net.magic_and_taboo.init.MATBlocks;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class MagicAndTabooClient implements ClientModInitializer {
//    public static ScreenHandlerType<SpyglassSextantUIScreenHandler> SpyglassSextantUIScreenHandler;
//    static {
//        SpyglassSextantUIScreenHandler = ScreenHandlerRegistry.registerSimple(new Identifier(MagicAndTabooMod.MOD_ID,"spyglass_sextant_ui"), SpyglassSextantUIScreenHandler::new);
//    }
    public void onInitializeClient() {
        ScreenRegistry.register(MagicAndTabooMod.SpyglassSextantUIScreenHandler, SpyglassSextantUIScreen::new);
//        ScreenRegistry.register(SpyglassSextantUIScreenHandler, SpyglassSextantUIScreen::new);
        BlockRenderLayerMap.INSTANCE.putBlock(MATBlocks.SPYGLASS_SEXTANT_BLOCK, RenderLayer.getCutout());
    }
}