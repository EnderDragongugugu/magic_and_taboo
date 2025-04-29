package enderdragon.magic_and_taboo.client.book.page;

import com.google.common.collect.ImmutableList;
import enderdragon.magic_and_taboo.client.book.Book;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class IndexedChapter<T> extends PageChapter {
    public final Catalog<T> catalog;
    public final Supplier<Iterable<T>> entries;
    public final Function<T, List<Chunk>> descriptor;
    protected final List<Chunk> preface;
    private int contentLeft;

    public IndexedChapter(
            Catalog<T> catalog,
            List<Chunk> preface,
            Supplier<Iterable<T>> entries,
            Function<T, List<Chunk>> descriptor
    ) {
        this.catalog = catalog;
        this.preface = preface;
        this.entries = entries;
        this.descriptor = descriptor;
    }

    @Override
    public void render(GuiGraphics graphics, Font font, int width, int height, int mouseX, int mouseY, float partialTicks) {
        super.render(graphics, font, width, height, mouseX, mouseY, partialTicks);
        this.catalog.render(graphics, font, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean onMouseDown(Book book, double x, double y, int button) {
        return x < this.contentLeft
                ? this.catalog.onMouseDown(this, x, y, button)
                : super.onMouseDown(book, x, y, button);
    }

    public final int fillPage(Iterable<Chunk> chunks, Font font, int top) {
        int index = this.pages.size(), chunkLeft = this.contentLeft, chunkTop = top, space = PAGE_HEIGHT;
        var builder = ImmutableList.<Chunk>builder();
        for (var chunk : chunks) {
            int measuredHeight = chunk.measure(font, space);
            if (measuredHeight <= space) {
                chunk.layout(chunkLeft, chunkTop);
                builder.add(chunk);
                chunkTop += measuredHeight;
                space -= measuredHeight;
            } else if (space != PAGE_HEIGHT || !(chunk instanceof Separator)) {
                this.pages.add(builder.build());
                builder = ImmutableList.<Chunk>builder().add(chunk);
                chunk.layout(chunkLeft, top);
                chunkTop = top + measuredHeight;
                space = PAGE_HEIGHT - measuredHeight;
            }
        }
        this.pages.add(builder.build());
        return index;
    }

    @Override
    protected void layout(Font font, int left, int top) {
        this.contentLeft = left + RIGHT_START;
        this.fillPage(this.preface, font, top);
        this.catalog.layout(this, font, left + LEFT_START, top);
    }
}
