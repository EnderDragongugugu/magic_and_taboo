package enderdragon.magic_and_taboo.client.gui;

import enderdragon.magic_and_taboo.init.MATCapabilities;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;

public class PlayerMagicPointScreen {
    public static void onRenderGuiOverlay(RenderGuiOverlayEvent.Post event) {
        var mc = Minecraft.getInstance();
        var player = mc.player;
        if (player == null) return;
        player.getCapability(MATCapabilities.PLAYER_MAGIC_POINT).ifPresent(magic -> {
            int MP = magic.getMagic();
            int MaxMP = magic.getMaxMagic();
            var graphics = event.getGuiGraphics();
            int x = 10;
            int y = 10;
            graphics.drawString(mc.font, "MP: " + MP + "/" + MaxMP, x, y, 0x00BFFF, true);
        });
    }
}
