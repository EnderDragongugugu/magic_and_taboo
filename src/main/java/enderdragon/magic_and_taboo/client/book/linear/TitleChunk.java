package enderdragon.magic_and_taboo.client.book.linear;

import com.mojang.blaze3d.systems.RenderSystem;
import enderdragon.magic_and_taboo.MagicAndTabooMod;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import static enderdragon.magic_and_taboo.client.book.linear.Page.PAGE_WIDTH;

public class TitleChunk implements IChunk {
    private static final ResourceLocation BACKGROUND = MagicAndTabooMod.makeId("textures/gui/book/book.png");

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
    public int render(LinearChapter chapter, GuiGraphics graphics, Font font, int x, int y, int mouseX, int mouseY, float partialTicks) {
        int top = y, center = PAGE_WIDTH / 2 + x, height = font.lineHeight;
        for (var line : font.split(this.text, PAGE_WIDTH)) {
            graphics.drawString(font, line, center - font.width(line) / 2, top, 0, false);
            top += height;
        }
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        graphics.blit(BACKGROUND, x + 3, top, 140, 180, 110, 3, 512, 256);
        RenderSystem.disableBlend();
        top += 4;
        return top;
    }
}
