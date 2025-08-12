package enderdragon.magic_and_taboo.client.gui;

import enderdragon.magic_and_taboo.init.MATCapabilities;
import enderdragon.magic_and_taboo.util.CapabilityUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class PlayerMagicPointScreen implements IGuiOverlay {
    @Override
    public void render(ForgeGui gui, GuiGraphics graphics, float partialTick, int screenWidth, int screenHeight) {
        var mc = Minecraft.getInstance();
        var player = mc.player;
        if (player == null) return;
        int x = 10;
        int y = 10;
        int width = 100;
        int height = 10;
        var capability = CapabilityUtil.getCapability(player, MATCapabilities.PLAYER_MAGIC_POINT);
        if (capability == null) return;
        int magic = capability.getMagic();
        int max = capability.getMaxMagic();
        int filled = (int) ((magic / (float) max) * width);
        graphics.fill(x, y, x + width, y + height, 0xFF444444);
        graphics.fill(x, y, x + filled, y + height, 0xFF00BFFF);
//        graphics.drawString(mc.font, "MP: " + player.getHealth() + "/" + h.getValue(), x, y, 0x00BFFF, true);
        graphics.drawString(mc.font, "MP: " + magic + "/" + max, x, y, 0x00BFFF, true);
    }
}
