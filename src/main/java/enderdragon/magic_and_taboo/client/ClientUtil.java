package enderdragon.magic_and_taboo.client;

import enderdragon.magic_and_taboo.client.book.OccultCodexChapters;
import enderdragon.magic_and_taboo.client.gui.OccultCodexScreen;
import net.minecraft.client.Minecraft;

public class ClientUtil {
    public static void openOccultCodexScreen() {
        Minecraft.getInstance().setScreen(new OccultCodexScreen(OccultCodexChapters.ENTRY));
    }
}
