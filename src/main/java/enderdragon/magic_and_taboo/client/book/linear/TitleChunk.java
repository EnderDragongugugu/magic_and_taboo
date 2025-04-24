package enderdragon.magic_and_taboo.client.book.linear;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import static enderdragon.magic_and_taboo.MagicAndTabooMod.makeId;
import static enderdragon.magic_and_taboo.client.book.linear.LinearChapter.PAGE_WIDTH;

public class TitleChunk extends Chunk {
    private static final ResourceLocation SEPARATOR = makeId("textures/gui/book/book.png");

    public static TitleChunk of(String translationKey) {
        return new TitleChunk(Component.translatable(translationKey));
    }

    private final Component text;

    public TitleChunk(Component text) {
        this.text = text;
    }

    @Override
    public int measure(Font font, int space) {
        return font.wordWrapHeight(this.text, PAGE_WIDTH) + 4;
    }

    @Override
    public void render(GuiGraphics graphics, Font font, int mouseX, int mouseY, float partialTicks) {
        int top = this.top, center = PAGE_WIDTH / 2 + this.left, height = font.lineHeight;
        for (var line : font.split(this.text, PAGE_WIDTH)) {
            graphics.drawString(font, line, center - font.width(line) / 2, top, 0, false);
            top += height;
        }
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        graphics.blit(SEPARATOR, this.left + 3, top, 140, 180, 110, 3, 512, 256);
        RenderSystem.disableBlend();
    }
}
