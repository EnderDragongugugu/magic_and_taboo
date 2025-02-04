package enderdragon.magic_and_taboo.client.gui;

import enderdragon.magic_and_taboo.registry.Element;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import org.joml.Math;
import org.joml.Matrix4f;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class AlchemyElementTooltip implements ClientTooltipComponent {
    private final ResourceLocation GAMEMODE_SWITCH = new ResourceLocation("textures/gui/container/gamemode_switcher.png");
    public AlchemyElementTooltipComp comp;

    public AlchemyElementTooltip(AlchemyElementTooltipComp comp) {
        this.comp = comp;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public int getWidth(Font pFont) {
        return 0;
    }

    @Override
    public void renderText(Font pFont, int pMouseX, int pMouseY, Matrix4f pMatrix, MultiBufferSource.BufferSource pBufferSource) {
//        comp.alchemyElement.forEach((key, value) -> {
//            pFont.drawInBatch(Component.translatable(key.get().icon().getPath()), pMouseX, pMouseY + 2, -1, true, pMatrix, pBufferSource, Font.DisplayMode.POLYGON_OFFSET, 0, 15728880);
//        });
    }

    @Override
    public void renderImage(Font pFont, int pX, int pY, GuiGraphics pGuiGraphics) {
        if (Screen.hasShiftDown()) {
            simple(pFont, pX, pY, pGuiGraphics);
        }
    }

    public void simple(Font font, int pX, int pY, GuiGraphics guiGraphics) {
        AtomicInteger i = new AtomicInteger(0);
        var y = pY + (int) (Math.max((float) (i.get() + 1) / 6.0F, 0.0F)) - 40;
        comp.alchemyElement.forEach((key, value) -> {
            var element = key.get();
            var x = pX + i.get() * 20;
            if (value > 0.0F) {
//                guiGraphics.blit(GAMEMODE_SWITCH, x - 2, y - 2, 22, 22, 26.0F, 75.0F, 26, 26, 128, 128);
                guiGraphics.blit(element.getIcon(), x, y, 0, 0, 16, 16, 16, 16);
                renderElementText(font, x, y, guiGraphics, value);
                i.addAndGet(1);
            }
        });
    }

    public void renderElementText(Font pFont, int x, int y, GuiGraphics guiGraphics, float count) {
        String c = String.valueOf(count);
        guiGraphics.drawString(pFont, c, x + 19 - 2 - pFont.width(c), y + 6 + 3, 16777215, true);
    }

    public static class AlchemyElementTooltipComp implements TooltipComponent {
        public Map<Holder<Element>, Float> alchemyElement;

        public AlchemyElementTooltipComp(Map<Holder<Element>, Float> alchemyElement) {
            this.alchemyElement = alchemyElement;
        }
    }
}
