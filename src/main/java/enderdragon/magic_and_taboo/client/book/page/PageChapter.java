package enderdragon.magic_and_taboo.client.book.page;

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

public abstract class PageChapter implements Chapter {
    protected static final ResourceLocation BACKGROUND = MagicAndTabooMod.makeId("textures/gui/book/book.png");
    protected static final ResourceLocation SLOT = MagicAndTabooMod.makeId("textures/gui/book/slot.png");
    public static final int PAGE_WIDTH = 116;
    public static final int PAGE_HEIGHT = 157;
    protected static final int FRAME_WIDTH = 269;
    protected static final int FRAME_HEIGHT = 176;
    protected static final int LEFT_START = 17;
    protected static final int RIGHT_START = 145;
    protected final ObjectArrayList<ImmutableList<Chunk>> pages = new ObjectArrayList<>();
    protected int page;
    private PageButton prevButton;
    private PageButton nextButton;

    protected abstract void layout(Font font, int left, int top);

    public void turnTo(int page) {
        if (page < 0 || page >= this.pages.size()) return;
        this.page = page;
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

    protected void updateButtonVisibility() {
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
    public final Collection<AbstractWidget> init(Font font, int width, int height) {
        this.pages.clear();
        this.page = 0;
        int left = (width - FRAME_WIDTH) / 2, top = (height - FRAME_HEIGHT) / 2 + 10;
        this.layout(font, left, top);
        this.prevButton = new PageButton(left - 2, top + 164, false, ignored -> this.prevPage(), true);
        this.nextButton = new PageButton(left + 269 - 17 + 3, top + 164, true, ignored -> this.nextPage(), true);
        this.updateButtonVisibility();
        return List.of(this.prevButton, this.nextButton);
    }

    @Override
    public void render(GuiGraphics graphics, Font font, int width, int height, int mouseX, int mouseY, float partialTicks) {
        graphics.blit(
                BACKGROUND,
                (width - FRAME_WIDTH) / 2,
                (height - FRAME_HEIGHT) / 2,
                0,
                0,
                FRAME_WIDTH,
                FRAME_HEIGHT,
                353,
                256
        );
        if (this.page < 0 || this.page >= this.pages.size()) return;
        for (var chunk : this.pages.get(this.page)) {
            chunk.render(graphics, font, mouseX, mouseY, partialTicks);
        }
    }
}
