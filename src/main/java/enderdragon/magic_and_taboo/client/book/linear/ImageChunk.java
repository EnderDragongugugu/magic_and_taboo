package enderdragon.magic_and_taboo.client.book.linear;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class ImageChunk extends Chunk {
    ResourceLocation resourceLocation;
    int x;
    int y;
    float UOffset;
    float VOffset;
    int width;
    int height;
    int textureWidth;
    int textureHeight;

    public ImageChunk(ResourceLocation resourceLocation, int x, int y, float UOffset, float VOffset, int width, int height, int textureWidth, int textureHeight) {
        this.resourceLocation = resourceLocation;
        this.x = x;
        this.y = y;
        this.UOffset = UOffset;
        this.VOffset = VOffset;
        this.width = width;
        this.height = height;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }

    @Override
    public int measure(Font font, int space) {
        return y + height;
    }

    @Override
    public void render(GuiGraphics graphics, Font font, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        graphics.blit(resourceLocation, this.left + x, top + y, UOffset, VOffset, width, height, textureWidth, textureHeight);
        RenderSystem.disableBlend();
    }
}
