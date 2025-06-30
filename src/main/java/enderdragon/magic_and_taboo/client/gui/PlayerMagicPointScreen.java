package enderdragon.magic_and_taboo.client.gui;

import enderdragon.magic_and_taboo.capability.IPlayerMagicPoint;
import enderdragon.magic_and_taboo.init.MATCapabilities;
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
        var magic = player.getCapability(MATCapabilities.PLAYER_MAGIC_POINT).orElse(IPlayerMagicPoint.EMPTY);
        if (magic == IPlayerMagicPoint.EMPTY) return;
        int MP = magic.getMagic();
        System.out.println(MP);
        int maxMP = magic.getMaxMagic();
        int filled = (int) ((MP / (float) maxMP) * width);
        graphics.fill(x, y, x + width, y + height, 0xFF444444);
        graphics.fill(x, y, x + filled, y + height, 0xFF00BFFF);
        graphics.drawString(mc.font, "MP: " + MP + "/" + maxMP, x, y, 0x00BFFF, true);
    }
}
