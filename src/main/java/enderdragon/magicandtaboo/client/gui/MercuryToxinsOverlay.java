package enderdragon.magicandtaboo.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import enderdragon.magicandtaboo.effect.MATEffect;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MercuryToxinsOverlay implements IGuiOverlay {
    private static final Logger LOGGER = LogManager.getLogger();
    protected static final ResourceLocation GUI_ICONS_LOCATION = new ResourceLocation("textures/gui/icons.png");
    protected static final ResourceLocation POWDER_SNOW_OUTLINE_LOCATION = new ResourceLocation("textures/misc/powder_snow_outline.png");

    @Override
    public void render(ForgeGui gui, GuiGraphics graphics, float partialTick, int screenWidth, int screenHeight) {
        var minecraft = gui.getMinecraft();
        if (!minecraft.options.hideGui && gui.shouldDrawSurvivalElements()) {
            var player = minecraft.player;
            if (player == null || !player.hasEffect(MATEffect.MERCURY_TOXINS.get())) return;
            gui.setupOverlayRenderState(true, false);
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            float test = (player.tickCount % 20) / 20F;
            graphics.setColor(1.0F, 1.0F, 1.0F, test);
            graphics.blit(POWDER_SNOW_OUTLINE_LOCATION, 0, 0, -90, 0.0F, 0.0F, screenWidth, screenHeight, screenWidth, screenHeight);
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
            graphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
