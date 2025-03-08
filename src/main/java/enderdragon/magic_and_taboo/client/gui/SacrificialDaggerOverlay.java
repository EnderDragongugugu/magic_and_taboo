package enderdragon.magic_and_taboo.client.gui;

import enderdragon.magic_and_taboo.MagicAndTabooMod;
import enderdragon.magic_and_taboo.init.MATCapabilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class SacrificialDaggerOverlay implements IGuiOverlay {
//    private static final ResourceLocation TEXTURE = MagicAndTabooMod.makeId("textures/gui/container/work_hub.png");

    private static final ResourceLocation TEXTURE = MagicAndTabooMod.makeId("textures/gui/sacrificial_dagger.png");

    @Override
    public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
        float imageX = screenWidth / 1.9F, imageY = screenHeight / 1.9F;
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        var level = mc.level;
        if (player == null || level == null) return;
        ItemStack itemStack = player.getMainHandItem();
        if (itemStack.isEmpty()) return;
        MultiBufferSource.BufferSource buffer = mc.renderBuffers().bufferSource();
        var font = mc.font;
        var matrix = guiGraphics.pose().last().pose();
        itemStack.getCapability(MATCapabilities.PURENESS).ifPresent(itemHandler -> {
            var pureness = itemHandler.getPureness();
            var a = itemHandler.getFormattedPureness();
            Component text = Component.translatable("tooltip.magic_and_taboo.blood_bottle_pureness", a);
            float textW = font.width(text), textH = font.lineHeight;
            float textX = (screenWidth - textW) / 2.0F, textY = (screenHeight - textH) / 2.0F;
//            renderImage(imageX, imageY, pureness, guiGraphics);
            font.drawInBatch(text, textX, textY * 1.15F, -1, true, matrix, buffer, Font.DisplayMode.NORMAL, 0, 15728880);
            buffer.endBatch();
        });
    }

    public void renderImage(float x, float y, int pureness, GuiGraphics guiGraphics) {
        int tw = 8 * 2;
        int th = 15 * 2;
        int k = (int) ((float) pureness / 2400.0F * 15.0F * 2.0F);
        int o = k == th ? 0 : 2;
        guiGraphics.blit(TEXTURE, (int) x, (int) y, 0, 0, 5 * 2, th, tw, th);
        guiGraphics.blit(TEXTURE, (int) x, (int) y + th - k - o, 8, 0, 5 * 2, k, tw, th);
    }
}
