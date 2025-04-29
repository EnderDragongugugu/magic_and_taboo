package enderdragon.magic_and_taboo.client.book.page;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

import static enderdragon.magic_and_taboo.client.book.page.PageChapter.PAGE_WIDTH;

public class ImageChunk extends Chunk {
    public static ImageChunk squared(ResourceLocation image, float u, float v, int size, int scale) {
        int scaled = size * scale;
        return new ImageChunk(image, u, v, scaled, scaled, scaled, scaled);
    }

    public final ResourceLocation image;
    public final float u;
    public final float v;
    public final int width;
    public final int height;
    public final int textureWidth;
    public final int textureHeight;

    public ImageChunk(ResourceLocation image, float u, float v, int width, int height, int textureWidth, int textureHeight) {
        this.image = image;
        this.u = u;
        this.v = v;
        this.width = width;
        this.height = height;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }

    @Override
    public int measure(Font font, int space) {
        return this.height;
    }

    @Override
    public void layout(int left, int top) {
        this.left = left + (PAGE_WIDTH - this.width) / 2;
        this.top = top;
    }

    @Override
    public void render(GuiGraphics graphics, Font font, int mouseX, int mouseY, float partialTicks) {
        graphics.blit(this.image, this.left, this.top, this.u, this.v, width, this.height, this.textureWidth, this.textureHeight);
    }
}
