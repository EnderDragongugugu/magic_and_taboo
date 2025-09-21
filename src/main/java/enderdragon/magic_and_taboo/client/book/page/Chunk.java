package enderdragon.magic_and_taboo.client.book.page;

import enderdragon.magic_and_taboo.client.book.Book;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

public abstract class Chunk {
    protected int left;
    protected int top;

    public void layout(int left, int top) {
        this.left = left;
        this.top = top;
    }

    /// @return the measured height of the chunk
    public abstract int measure(Font font, int space);

    public abstract void render(
            GuiGraphics graphics,
            Font font,
            int mouseX,
            int mouseY,
            float partialTicks
    );

    public boolean onMouseDown(Book book, double x, double y, int button) {
        return false;
    }
}
