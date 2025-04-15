package enderdragon.magic_and_taboo.client.book;

import enderdragon.magic_and_taboo.client.gui.OccultCodexContentScreen;
import net.minecraft.client.Minecraft;

import javax.annotation.Nullable;

public interface IBook {
    default void open(@Nullable Page page) {
    }

    default void open(@Nullable PageContent page) {
        Minecraft.getInstance().setScreen(new OccultCodexContentScreen(page));
    }

}
