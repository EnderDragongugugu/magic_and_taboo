package enderdragon.magic_and_taboo.client.book.linear;

import com.google.common.collect.ImmutableList;
import enderdragon.magic_and_taboo.MagicAndTabooMod;
import enderdragon.magic_and_taboo.client.book.IChapter;
import it.unimi.dsi.fastutil.objects.ObjectArrayFIFOQueue;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.inventory.PageButton;
import net.minecraft.resources.ResourceLocation;

import java.util.Collection;
import java.util.List;

import static enderdragon.magic_and_taboo.client.book.linear.Page.PAGE_HEIGHT;

public class LinearChapter implements IChapter {
    private static final ResourceLocation BACKGROUND = MagicAndTabooMod.makeId("textures/gui/book/book.png");
    private static final int FRAME_WIDTH = 271;
    private static final int FRAME_HEIGHT = 179;
    private static final int LEFT_START = 20;
    private static final int RIGHT_START = 150;
    public final ImmutableList<IChunk> chunks;
    private final ObjectArrayList<Page> pages = new ObjectArrayList<>();
    private int page;
    private PageButton prevButton;
    private PageButton nextButton;

    public LinearChapter(ImmutableList.Builder<IChunk> chunks) {
        this.chunks = chunks.build();
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

    public static void renderImpl(List<IChunk> chunks, GuiGraphics graphics, Font font, int left, int top, float partialTicks) {
        for (var chunk : chunks) {
            top = chunk.render(graphics, font, left, top, partialTicks);
        }
    }

    @Override
    public void render(GuiGraphics graphics, Font font, int width, int height, int mouseX, int mouseY, float partialTicks) {
        int left = (width - FRAME_WIDTH) / 2, top = (height - FRAME_HEIGHT) / 2;
        graphics.blit(BACKGROUND, left, top, 0, 0, FRAME_WIDTH, FRAME_HEIGHT, 512, 256);
        if (this.page < 0 || this.page >= this.pages.size()) return;
        top += 15;
        var page = this.pages.get(this.page);
        renderImpl(page.left(), graphics, font, left + LEFT_START, top, partialTicks);
        renderImpl(page.right(), graphics, font, left + RIGHT_START, top, partialTicks);
    }

    @Override
    public Collection<AbstractWidget> init(Font font, int width, int height) {
        this.pages.clear();
        this.page = 0;
        var sides = new ObjectArrayFIFOQueue<ImmutableList<IChunk>>();
        var builder = ImmutableList.<IChunk>builder();
        int left = (width - FRAME_WIDTH) / 2, top = (height - FRAME_HEIGHT) / 2, totalHeight = 0, measuredHeight;
        for (var chunk : this.chunks) {
            chunk.reload();
            measuredHeight = chunk.measureHeight(font);
            if (measuredHeight <= 0) {
                if (totalHeight != 0) {
                    sides.enqueue(builder.build());
                    builder = ImmutableList.builder();
                    totalHeight = 0;
                }
            } else if ((totalHeight += measuredHeight) > PAGE_HEIGHT) {
                if (totalHeight == measuredHeight) {
                    sides.enqueue(builder.add(chunk).build());
                    builder = ImmutableList.builder();
                    totalHeight = 0;
                } else {
                    sides.enqueue(builder.build());
                    builder = ImmutableList.<IChunk>builder().add(chunk);
                    totalHeight = measuredHeight;
                }
            } else {
                builder.add(chunk);
            }
        }
        if (totalHeight != 0) {
            sides.enqueue(builder.build());
        }
        for (int len = sides.size(); len > 1; len -= 2) {
            this.pages.add(new Page(sides.dequeue(), sides.dequeue()));
        }
        if (!sides.isEmpty()) {
            this.pages.add(new Page(sides.dequeue(), ImmutableList.of()));
        }
        this.prevButton = new PageButton(left - 2, top + 174, false, ignored -> this.prevPage(), true);
        this.nextButton = new PageButton(left + 269 - 17 + 3, top + 174, true, ignored -> this.nextPage(), true);
        this.updateButtonVisibility();
        return List.of(this.prevButton, this.nextButton);
    }
}
