package enderdragon.magic_and_taboo.client.book;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;

import java.util.Collection;

public interface Chapter {
    void render(
            GuiGraphics graphics,
            Font font,
            int width,
            int height,
            int mouseX,
            int mouseY,
            float partialTicks
    );

    default void setPage(int index) {
    }

    ;

    Collection<AbstractWidget> init(Font font, int width, int height);

    default boolean onDrag(double deltaX, double deltaY, int button) {
        return false;
    }

    default boolean onMouseDown(Book book, double x, double y, int button) {
        return false;
    }

    default boolean onMouseUp(Book book, double x, double y, int button) {
        return false;
    }
}
