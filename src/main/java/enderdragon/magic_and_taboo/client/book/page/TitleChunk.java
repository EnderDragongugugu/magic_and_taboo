package enderdragon.magic_and_taboo.client.book.page;

import com.mojang.blaze3d.systems.RenderSystem;
import enderdragon.magic_and_taboo.MagicAndTabooMod;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import static enderdragon.magic_and_taboo.client.book.page.PageChapter.PAGE_WIDTH;

public class TitleChunk extends Chunk {
    protected static final ResourceLocation SEPARATOR = MagicAndTabooMod.makeId("textures/gui/book/title.png");

    public static final int WIDTH = 110;
    public static final int HEIGHT = 7;

    public static TitleChunk of(String translationKey) {
        return new TitleChunk(Component.translatable(translationKey));
    }

    private final Component text;

    public TitleChunk(Component text) {
        this.text = text;
    }

    @Override
    public int measure(Font font, int space) {
        return font.wordWrapHeight(this.text, PAGE_WIDTH) + HEIGHT + 1;
    }

    @Override
    public void render(GuiGraphics graphics, Font font, int mouseX, int mouseY, float partialTicks) {
        int top = this.top, center = PAGE_WIDTH / 2 + this.left - 2, height = font.lineHeight;
        for (var line : font.split(this.text, PAGE_WIDTH)) {
            graphics.drawString(font, line, center - font.width(line) / 2, top, 0, false);
            top += height;
        }
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        graphics.blit(SEPARATOR, this.left, top, 0, 0, WIDTH, HEIGHT, WIDTH, HEIGHT);
        RenderSystem.disableBlend();
    }
}
