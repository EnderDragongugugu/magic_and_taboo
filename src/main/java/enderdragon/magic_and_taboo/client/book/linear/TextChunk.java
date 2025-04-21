package enderdragon.magic_and_taboo.client.book.linear;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

import static enderdragon.magic_and_taboo.client.book.linear.Page.PAGE_WIDTH;

public class TextChunk implements IChunk {
    /// make it private because {@link Component} is mutable
    private final ImmutableList<Component> texts;

    public TextChunk(ImmutableList.Builder<Component> texts) {
        this.texts = texts.build();
    }

    @Override
    public int measureHeight(Font font) {
        int height = 0;
        for (var text : this.texts) {
            height += font.wordWrapHeight(text, PAGE_WIDTH);
        }
        return height;
    }

    @Override
    public int render(LinearChapter chapter, GuiGraphics graphics, Font font, int x, int y, int mouseX, int mouseY, float partialTicks) {
        int top = y, height = font.lineHeight;
        for (var text : this.texts) {
            for (var line : font.split(text, PAGE_WIDTH)) {
                graphics.drawString(font, line, x, top, 0, false);
                top += height;
            }
        }
        return top;
    }
}
