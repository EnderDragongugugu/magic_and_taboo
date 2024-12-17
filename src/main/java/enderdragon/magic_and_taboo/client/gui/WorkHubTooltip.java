package enderdragon.magic_and_taboo.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix4f;

public class WorkHubTooltip implements ClientTooltipComponent {
    protected ItemStack itemStack;
    protected int lineHeight = Minecraft.getInstance().font.lineHeight + 1;

    public WorkHubTooltip(WorkHubTooltipComponent comp) {
        this.itemStack = comp.itemStack;
    }

    public MutableComponent getText(ItemStack itemStack) {
        return !Screen.hasShiftDown() ? Component.translatable("tooltip.magic_and_taboo.work_hub_item") : Component.translatable("tooltip.magic_and_taboo.work_hub_item_show", Component.translatable(itemStack.getDescriptionId()), itemStack.getCount());
    }


    @Override
    public int getHeight() {
        return lineHeight + 16;
    }

    @Override
    public int getWidth(Font pFont) {
        if (!itemStack.isEmpty()) {
            return pFont.width(getText(itemStack));
        } else {
            return pFont.width(Component.translatable("tooltip.magic_and_taboo.work_hub_item_empty"));
        }
    }

    @Override
    public void renderText(Font pFont, int pMouseX, int pMouseY, Matrix4f pMatrix, MultiBufferSource.BufferSource pBufferSource) {
        if (!itemStack.isEmpty()) {
            pFont.drawInBatch(getText(itemStack), pMouseX, pMouseY + 5, -1, true, pMatrix, pBufferSource, Font.DisplayMode.POLYGON_OFFSET, 0, 15728880);
        } else {
            pFont.drawInBatch(Component.translatable("tooltip.magic_and_taboo.work_hub_item_empty"), pMouseX, pMouseY + 2, -1, true, pMatrix, pBufferSource, Font.DisplayMode.POLYGON_OFFSET, 0, 15728880);
        }
    }

    @Override
    public void renderImage(Font pFont, int pX, int pY, GuiGraphics pGuiGraphics) {
        if (!itemStack.isEmpty()) {
            int x = pX + pFont.width(Component.translatable("tooltip.magic_and_taboo.work_hub_item"));
            if (!Screen.hasShiftDown()) {
                pGuiGraphics.renderItem(itemStack, x, pY);
                pGuiGraphics.renderItemDecorations(pFont, itemStack, x, pY);
            }
        }
    }

    public static record WorkHubTooltipComponent(ItemStack itemStack) implements TooltipComponent {

    }
}
