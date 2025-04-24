package enderdragon.magic_and_taboo.client;

import enderdragon.magic_and_taboo.client.book.OccultCodexChapters;
import enderdragon.magic_and_taboo.client.gui.OccultCodexScreen;
import net.minecraft.client.Minecraft;

public class ClientUtil {
    public static void openOccultCodexScreen() {
        Minecraft.getInstance().setScreen(new OccultCodexScreen(OccultCodexChapters.ENTRY));
    }

    public static boolean isMouseOver(int mouseX, int mouseY, int left, int top, int width, int height) {
        return mouseX >= left && mouseX <= left + width && mouseY >= top && mouseY <= top + height;
    }

    public static boolean isMouseOver(double mouseX, double mouseY, int left, int top, int width, int height) {
        return mouseX >= left && mouseX <= left + width && mouseY >= top && mouseY <= top + height;
    }
}
