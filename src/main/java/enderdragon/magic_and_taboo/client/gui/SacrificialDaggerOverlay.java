package enderdragon.magic_and_taboo.client.gui;

import enderdragon.magic_and_taboo.MagicAndTabooMod;
import enderdragon.magic_and_taboo.capability.PurenessStorage;
import enderdragon.magic_and_taboo.init.MATCapabilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class SacrificialDaggerOverlay implements IGuiOverlay {
    private static final ResourceLocation TEXTURE = MagicAndTabooMod.makeId("textures/gui/sacrificial_dagger.png");

    @Override
    public void render(ForgeGui gui, GuiGraphics graphics, float partialTick, int screenWidth, int screenHeight) {
        var mc = Minecraft.getInstance();
        var player = mc.player;
        if (player == null) return;
        var stack = player.getMainHandItem();
        if (stack.isEmpty()) return;
        var pureness = stack.getCapability(MATCapabilities.PURENESS).orElse(PurenessStorage.EMPTY);
        if (pureness == PurenessStorage.EMPTY) return;
        var font = mc.font;
        var text = Component.translatable("tooltip.magic_and_taboo.blood_bottle_pureness", pureness.getFormattedPureness());
        int textX = (screenWidth - font.width(text)) / 2, textY = (screenHeight - font.lineHeight) / 2;
        float imageX = screenWidth / 1.9F, imageY = screenHeight / 1.9F;
//            renderImage(imageX, imageY, pureness, guiGraphics);
        graphics.drawString(font, text, textX, (int) (textY * 1.15F), -1, true);

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
