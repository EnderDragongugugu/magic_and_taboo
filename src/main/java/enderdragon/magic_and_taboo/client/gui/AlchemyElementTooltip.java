package enderdragon.magic_and_taboo.client.gui;

import enderdragon.magic_and_taboo.registry.AlchemyElement;
import enderdragon.magic_and_taboo.registry.Element;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.joml.Math;
import org.joml.Matrix4f;

public class AlchemyElementTooltip implements ClientTooltipComponent {
    public static final int COLUMNS = 3;
    public static final int ICON_SIZE = 16;
    public static final int SMALL_PADDING = 1;
    public static final int LARGE_PADDING = 2;
    public final ObjectList<Widget> widgets;
    private final int[] widgetWidth = new int[COLUMNS];
    private int widgetHeight;
    private int totalWidth = -1;
    private int totalHeight = LARGE_PADDING;

    public AlchemyElementTooltip(AlchemyElement element) {
        if (Screen.hasShiftDown()) {
            var widgets = new ObjectArrayList<Widget>();
            for (var entry : element.elementMap().object2FloatEntrySet()) {
                float amount = entry.getFloatValue();
                if (amount > 0) {
                    widgets.add(new Widget(entry.getKey().value(), amount));
                }
            }
            this.widgets = widgets;
        } else {
            this.widgets = ObjectLists.emptyList();
        }
    }

    @Override
    public int getWidth(Font font) {
        if (this.totalWidth != -1) return this.totalWidth;
        if (this.widgets.isEmpty()) return this.totalWidth = 0;
        this.widgetHeight = Math.max(font.lineHeight * 2 + SMALL_PADDING, ICON_SIZE);
        int column = 0;
        for (var widget : this.widgets) {
            if (column == 0) {
                this.totalHeight += this.widgetHeight;
            }
            int width = widget.width = Math.max(
                    font.width(widget.name),
                    font.width(widget.amount)
            ) + ICON_SIZE + SMALL_PADDING;
            if (width > this.widgetWidth[column]) {
                this.widgetWidth[column] = width;
            }
            if (++column == COLUMNS) {
                column = 0;
            }
        }
        int totalWidth = -1;
        for (int width : this.widgetWidth) {
            totalWidth += width + LARGE_PADDING;
        }
        return this.totalWidth = totalWidth;
    }

    @Override
    public int getHeight() {
        return this.totalHeight;
    }

    @Override
    public void renderText(Font font, int x, int y, Matrix4f matrix, MultiBufferSource.BufferSource buffers) {
        int left = x, column = 0;
        int offsetX = ICON_SIZE + 1, offsetY = font.lineHeight + SMALL_PADDING;
        // 其实还有一种情况是图标比文本高，但是懒得写了
        for (var widget : this.widgets) {
            int start = x + offsetX;
            font.drawInBatch(widget.name, start, y, -1, true, matrix, buffers, Font.DisplayMode.NORMAL, 0, 15728880);
            font.drawInBatch(widget.amount, start, y + offsetY, -1, true, matrix, buffers, Font.DisplayMode.NORMAL, 0, 15728880);
            if (column + 1 == COLUMNS) {
                x = left;
                column = 0;
                y += this.widgetHeight;
            } else {
                x += this.widgetWidth[column++] + LARGE_PADDING;
            }
        }
    }

    @Override
    public void renderImage(Font font, int x, int y, GuiGraphics graphics) {
        int left = x, column = 0;
        y += (this.widgetHeight - ICON_SIZE) / 2;
        for (var widget : this.widgets) {
            graphics.blit(widget.icon, x, y, 0, 0, 16, 16, 16, 16);
            if (column + 1 == COLUMNS) {
                x = left;
                column = 0;
                y += this.widgetHeight;
            } else {
                x += this.widgetWidth[column++] + LARGE_PADDING;
            }
        }
    }

    public static class Widget {
        public final ResourceLocation icon;
        public final Component name;
        public final String amount;
        public int width;

        public Widget(Element element, float amount) {
            this.icon = element.icon();
            this.name = element.getDisplayName();
            this.amount = Float.toString(amount);
        }
    }
}
