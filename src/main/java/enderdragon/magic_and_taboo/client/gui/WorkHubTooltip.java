package enderdragon.magic_and_taboo.client.gui;

import enderdragon.magic_and_taboo.capability.WorkHubResult;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix4f;

public class WorkHubTooltip implements ClientTooltipComponent {
    protected int totalWidth = -1;
    protected int totalHeight;
    public final ItemStack stack;
    public final Component text;
    public final boolean preview;

    public WorkHubTooltip(WorkHubResult result) {
        var stack = this.stack = result.getStack();
        if (stack.isEmpty()) {
            this.text = Component.translatable("tooltip.magic_and_taboo.work_hub_item_empty");
            this.preview = false;
        } else if (Screen.hasShiftDown()) {
            this.text = Component.translatable("tooltip.magic_and_taboo.work_hub_item_show", stack.getDisplayName(), stack.getCount());
            this.preview = false;
        } else {
            this.text = Component.translatable("tooltip.magic_and_taboo.work_hub_item");
            this.preview = true;
        }
    }

    @Override
    public int getWidth(Font font) {
        if (this.totalWidth != -1) return this.totalWidth;
        this.totalHeight = this.stack.isEmpty() || !this.preview ? font.lineHeight + 2 : Math.max(font.lineHeight + 2, 18);
        return this.totalWidth = font.width(this.text);
    }

    @Override
    public int getHeight() {
        return this.totalHeight;
    }

    @Override
    public void renderText(Font font, int x, int y, Matrix4f matrix, MultiBufferSource.BufferSource buffers) {
        font.drawInBatch(this.text, x, this.preview ? y + Math.abs(16 - font.lineHeight) : y, -1, true, matrix, buffers, Font.DisplayMode.NORMAL, 0, 15728880);
    }

    @Override
    public void renderImage(Font font, int x, int y, GuiGraphics graphics) {
        if (this.preview) {
            x += font.width(this.text);
            graphics.renderItem(this.stack, x, y);
            graphics.renderItemDecorations(font, this.stack, x, y);
        }
    }
}
