package enderdragon.magic_and_taboo.client.book.linear;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;

import java.util.ArrayList;
import java.util.Collection;

public interface IChunk {
    default void reload() {
    }

    int measureHeight(Font font);

    default Collection<AbstractWidget> getWidget(Font font, int width, int height) {
        return new ArrayList<>();
    }

    int render(
            LinearChapter chapter,
            GuiGraphics graphics,
            Font font,
            int x,
            int y,
            int mouseX,
            int mouseY,
            float partialTicks
    );
}
