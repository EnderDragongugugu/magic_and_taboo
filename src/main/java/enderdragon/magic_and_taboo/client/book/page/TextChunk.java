package enderdragon.magic_and_taboo.client.book.page;

import enderdragon.magic_and_taboo.client.book.Book;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

import static enderdragon.magic_and_taboo.client.ClientUtil.isMouseOver;
import static enderdragon.magic_and_taboo.client.book.page.PageChapter.PAGE_WIDTH;

public class TextChunk extends Chunk {
    public static TextChunk of(Component... texts) {
        return new TextChunk(List.of(texts));
    }

    public static TextChunk of(String translationKey) {
        return new TextChunk(Collections.singletonList(Component.translatable(translationKey)));
    }

    private final ObjectArrayList<FormattedCharSequence> lines = new ObjectArrayList<>();
    /// make it private because {@link Component} might be mutable
    private final List<Component> texts;
    private int height;

    protected TextChunk(List<Component> texts) {
        this.texts = texts;
    }

    public @Nullable Style getComponentStyleAt(Font font, double x, double y) {
        assert isMouseOver(x, y, this.left, this.top, PAGE_WIDTH, this.height); // check it yourself
        int line = Mth.floor(y - this.top) / font.lineHeight;
        if (line < 0 || line >= this.lines.size()) return null;
        return font.getSplitter().componentStyleAtWidth(this.lines.get(line), Mth.floor(x - this.left));
    }

    @Override
    public boolean onMouseDown(Book book, double x, double y, int button) {
        if (button != 0 || !isMouseOver(x, y, this.left, this.top, PAGE_WIDTH, this.height)) return false;
        Style style = this.getComponentStyleAt(book.getFont(), x, y);
        return style != null && book.onClickComponent(style);
    }

    @Override
    public int measure(Font font, int space) {
        this.lines.clear();
        for (var text : this.texts) {
            var lines = font.split(text, PAGE_WIDTH);
            this.lines.addAll(lines);
        }
        return this.height = font.lineHeight * this.lines.size();
    }

    @Override
    public void render(GuiGraphics graphics, Font font, int mouseX, int mouseY, float partialTicks) {
        int top = this.top, left = this.left, height = font.lineHeight;
        for (var line : this.lines) {
            graphics.drawString(font, line, left, top, 0, false);
            top += height;
        }
        if (isMouseOver(mouseX, mouseY, left, this.top, PAGE_WIDTH, this.height)) {
            Style style = this.getComponentStyleAt(font, mouseX, mouseY);
            if (style != null) {
                graphics.renderComponentHoverEffect(font, style, mouseX, mouseY);
            }
        }
    }
}
