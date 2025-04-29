package enderdragon.magic_and_taboo.client.book.page;

import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

public abstract class Catalog<E> {
    protected final Reference2IntOpenHashMap<E> index;

    public Catalog() {
        this.index = new Reference2IntOpenHashMap<>();
        this.index.defaultReturnValue(-1);
    }

    public int indexOf(E entry) {
        return this.index.getInt(entry);
    }

    /// Remember to invoke {@link Reference2IntOpenHashMap#clear()} of {@link #index}
    public abstract void layout(
            IndexedChapter<E> chapter,
            Font font,
            int left,
            int top
    );

    public abstract void render(
            GuiGraphics graphics,
            Font font,
            int mouseX,
            int mouseY,
            float partialTicks
    );

    public abstract boolean onMouseDown(IndexedChapter<E> chapter, double x, double y, int button);
}
