package enderdragon.magic_and_taboo.client.book.linear;

import enderdragon.magic_and_taboo.registry.Element;
import enderdragon.magic_and_taboo.util.RegistryAccessor;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.joml.Math;

public class ElementChunk implements IChunk {

    public static final int COLUMNS = 7;
    public static final int ICON_SIZE = 16;
    public static final int SMALL_PADDING = 1;
    public static final int LARGE_PADDING = 2;
    private int widgetHeight;


    @Override
    public int measureHeight(Font font) {
        return this.widgetHeight = Math.max(font.lineHeight * 2 + SMALL_PADDING, ICON_SIZE);
    }


    @Override
    public int render(LinearChapter chapter, GuiGraphics graphics, Font font, int x, int y, int mouseX, int mouseY, float partialTicks) {
        var registryAccess = RegistryAccessor.access();
        Element e = null;
        if (registryAccess != null) {
            var elements = registryAccess.registryOrThrow(Element.RESOURCE_KEY);
            int left = x, column = 0;
            y += (this.widgetHeight - ICON_SIZE) / 2;
            for (var element : elements) {
//                graphics.blit(element.icon(), x + column * ICON_SIZE + LARGE_PADDING, y, 0, 0, 16, 16, 16, 16);
                if (isHovered(x + column * ICON_SIZE + LARGE_PADDING, y, mouseX, mouseY)) {
                    e = element;
                }
                if (column + 1 == COLUMNS) {
                    x = left;
                    column = 0;
                    y += this.widgetHeight;
                } else {
                    column++;
                }
            }
            if (e != null) {
                graphics.renderTooltip(font, Component.translatable(e.translationKey()), mouseX, mouseY);
            }
        }
        return this.widgetHeight;
    }

    public boolean isHovered(int offsetX, int offsetY, int mouseX, int mouseY) {
        return mouseX >= offsetX && mouseX <= offsetX + ICON_SIZE &&
                mouseY >= offsetY && mouseY <= offsetY + ICON_SIZE;
    }

}
