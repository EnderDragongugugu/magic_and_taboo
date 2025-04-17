package enderdragon.magic_and_taboo.client.book.linear;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

import static enderdragon.magic_and_taboo.client.book.linear.Page.PAGE_WIDTH;

public class TitleChunk implements IChunk {
    public static TitleChunk of(String translationKey) {
        return new TitleChunk(Component.translatable(translationKey));
    }

    private final Component text;

    public TitleChunk(Component text) {
        this.text = text;
    }

    @Override
    public int measureHeight(Font font) {
        return font.wordWrapHeight(this.text, PAGE_WIDTH);
    }

    @Override
    public int render(GuiGraphics graphics, Font font, int x, int y, float partialTicks) {
        int top = y, center = PAGE_WIDTH / 2 + x, height = font.lineHeight;
        for (var line : font.split(this.text, PAGE_WIDTH)) {
            graphics.drawString(font, line, center - font.width(line) / 2, top, 0, false);
            top += height;
        }
        return top;
    }
}
