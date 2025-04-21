package enderdragon.magic_and_taboo.client.book.linear;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

public record Separator(int distance) implements IChunk {
    public static final Separator NEXT_PAGE = new Separator(0);

    @Override
    public int measureHeight(Font font) {
        return this.distance;
    }

    @Override
    public int render(LinearChapter chapter, GuiGraphics graphics, Font font, int x, int y, int mouseX, int mouseY, float partialTicks) {
        return this.distance;
    }
}
