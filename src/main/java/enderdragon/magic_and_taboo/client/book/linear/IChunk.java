package enderdragon.magic_and_taboo.client.book.linear;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

public interface IChunk {
    default void reload() {}

    int measureHeight(Font font);

    int render(
            GuiGraphics graphics,
            Font font,
            int x,
            int y,
            float partialTicks
    );
}
