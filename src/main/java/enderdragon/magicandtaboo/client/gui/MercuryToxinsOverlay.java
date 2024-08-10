package enderdragon.magicandtaboo.client.gui;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import enderdragon.magicandtaboo.MagicAndTabooMod;
import enderdragon.magicandtaboo.effect.MATEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.GuiOverlayManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MagicAndTabooMod.MODID, value = Dist.CLIENT)
public class MercuryToxinsOverlay {
    protected static final ResourceLocation GUI_ICONS_LOCATION = new ResourceLocation("textures/gui/icons.png");
//        protected static final ResourceLocation PUMPKIN_BLUR_LOCATION = new ResourceLocation("textures/misc/pumpkinblur.png");
    protected static final ResourceLocation POWDER_SNOW_OUTLINE_LOCATION = new ResourceLocation("textures/misc/powder_snow_outline.png");

    private static final Minecraft minecraft = Minecraft.getInstance();
    static ResourceLocation PLAYER_HEALTH_ELEMENT = new ResourceLocation("minecraft", "player_health");
    private static int tick = 0;
    private static int healthIconsOffset;

    public static void init() {
        MinecraftForge.EVENT_BUS.register(new MercuryToxinsOverlay());
    }

    @SubscribeEvent
    public void onRenderGuiOverlayPost(RenderGuiOverlayEvent.Post event) {
        if (event.getOverlay() == GuiOverlayManager.findOverlay(PLAYER_HEALTH_ELEMENT)) {
            Minecraft mc = Minecraft.getInstance();
            ForgeGui gui = (ForgeGui) mc.gui;
            if (!mc.options.hideGui && gui.shouldDrawSurvivalElements()) {
                renderMercuryToxinsOverlay(gui, event.getGuiGraphics());
            }
        }
    }

    public void renderMercuryToxinsOverlay(ForgeGui gui, GuiGraphics graphics) {
        healthIconsOffset = gui.leftHeight;
        Window window = minecraft.getWindow();
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;

        if (player != null && player.hasEffect(MATEffect.mercuryToxins.get())) {
//            int top = window.getGuiScaledHeight() - healthIconsOffset + 10;
//            int left = window.getGuiScaledWidth() / 2 - 91;
            drawMercuryToxinsOverlay(player,graphics);
        }
    }

    public void drawMercuryToxinsOverlay(Player player,GuiGraphics graphics) {
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        graphics.setColor(1.0F, 1.0F, 1.0F, 0.01F);
        MagicAndTabooMod.LOGGER.warn(player.getPercentFrozen());
        graphics.blit(POWDER_SNOW_OUTLINE_LOCATION, 0, 0, -90, 0.0F, 0.0F, graphics.guiWidth(), graphics.guiHeight(), graphics.guiWidth(), graphics.guiHeight());
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        graphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
