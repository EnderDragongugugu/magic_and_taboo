package enderdragon.magic_and_taboo.client.book.page;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.Font;

import java.util.List;

public class ContentChapter extends PageChapter {
    public static ContentChapter of(Chunk... chunks) {
        return new ContentChapter(List.of(chunks));
    }

    protected final List<Chunk> chunks;

    protected ContentChapter(List<Chunk> chunks) {
        this.chunks = chunks;
    }

    @Override
    protected void layout(Font font, int bookLeft, int bookTop) {
        int left = bookLeft + LEFT_START, top = bookTop, space = PAGE_HEIGHT;
        var builder = ImmutableList.<Chunk>builder();
        boolean isRight = false;
        for (var chunk : this.chunks) {
            int measuredHeight = chunk.measure(font, space);
            if (measuredHeight <= space) {
                chunk.layout(left, top);
                builder.add(chunk);
                top += measuredHeight;
                space -= measuredHeight;
            } else if (space != PAGE_HEIGHT || !(chunk instanceof Separator)) {
                if (isRight) {
                    left = bookLeft + LEFT_START;
                    this.pages.add(builder.build());
                    builder = ImmutableList.<Chunk>builder().add(chunk);
                    isRight = false;
                } else {
                    left = bookLeft + RIGHT_START;
                    builder.add(chunk);
                    isRight = true;
                }
                chunk.layout(left, bookTop);
                top = bookTop + measuredHeight;
                space = PAGE_HEIGHT - measuredHeight;
            }
        }
        if (isRight || space != PAGE_HEIGHT) {
            this.pages.add(builder.build());
        }
    }
}
