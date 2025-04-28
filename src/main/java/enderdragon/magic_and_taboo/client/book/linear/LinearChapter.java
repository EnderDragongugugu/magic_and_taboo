package enderdragon.magic_and_taboo.client.book.linear;

import com.google.common.collect.ImmutableList;
import enderdragon.magic_and_taboo.MagicAndTabooMod;
import enderdragon.magic_and_taboo.client.book.Book;
import enderdragon.magic_and_taboo.client.book.Chapter;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.inventory.PageButton;
import net.minecraft.resources.ResourceLocation;

import java.util.Collection;
import java.util.List;

public class LinearChapter implements Chapter {
    private static final ResourceLocation BACKGROUND = MagicAndTabooMod.makeId("textures/gui/book/book.png");
    public static final int PAGE_WIDTH = 118;
    public static final int PAGE_HEIGHT = 150;
    private static final int FRAME_WIDTH = 271;
    private static final int FRAME_HEIGHT = 179;
    private static final int LEFT_START = 15;
    private static final int RIGHT_START = 140;
    public final ImmutableList<Chunk> chunks;
    private final ObjectArrayList<ImmutableList<Chunk>> pages = new ObjectArrayList<>();
    private int page;
    private PageButton prevButton;
    private PageButton nextButton;

    public LinearChapter(ImmutableList.Builder<Chunk> chunks) {
        this.chunks = chunks.build();
    }

    public void setPage(int index) {
        this.page = index;
        this.updateButtonVisibility();

    }

    public void nextPage() {
        if (++this.page >= this.pages.size()) {
            this.page = 0;
        }
        this.updateButtonVisibility();
    }

    public void prevPage() {
        if (--this.page < 0) {
            this.page = 0;
        }
        this.updateButtonVisibility();
    }

    private void updateButtonVisibility() {
        this.prevButton.visible = this.page > 0;
        this.nextButton.visible = this.page < this.pages.size() - 1;
    }

    @Override
    public boolean onMouseDown(Book book, double x, double y, int button) {
        if (this.page < 0 || this.page >= this.pages.size()) return false;
        for (var chunk : this.pages.get(this.page)) {
            if (chunk.onMouseDown(book, x, y, button)) return true;
        }
        return false;
    }

    @Override
    public void render(GuiGraphics graphics, Font font, int width, int height, int mouseX, int mouseY, float partialTicks) {
        int left = (width - FRAME_WIDTH) / 2, top = (height - FRAME_HEIGHT) / 2;
        graphics.blit(BACKGROUND, left, top, 0, 0, FRAME_WIDTH, FRAME_HEIGHT, 512, 256);
        if (this.page < 0 || this.page >= this.pages.size()) return;
        for (var chunk : this.pages.get(this.page)) {
            chunk.render(graphics, font, mouseX, mouseY, partialTicks);
        }
    }

    @Override
    public Collection<AbstractWidget> init(Font font, int width, int height) {
        this.pages.clear();
        this.page = 0;
        var builder = ImmutableList.<Chunk>builder();
        boolean isRight = false;
        int bookLeft = (width - FRAME_WIDTH) / 2, bookTop = (height - FRAME_HEIGHT) / 2 + 15, left = bookLeft + LEFT_START, top = bookTop, space = PAGE_HEIGHT;
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
        this.prevButton = new PageButton(bookLeft - 2, bookTop + 159, false, ignored -> this.prevPage(), true);
        this.nextButton = new PageButton(bookLeft + 269 - 17 + 3, bookTop + 159, true, ignored -> this.nextPage(), true);
        this.updateButtonVisibility();
        return List.of(this.prevButton, this.nextButton);
    }
}
