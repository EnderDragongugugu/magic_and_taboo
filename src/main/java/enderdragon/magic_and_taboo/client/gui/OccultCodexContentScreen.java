package enderdragon.magic_and_taboo.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import enderdragon.magic_and_taboo.MagicAndTabooMod;
import enderdragon.magic_and_taboo.client.book.IBook;
import enderdragon.magic_and_taboo.client.book.PageContent;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.PageButton;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;

public class OccultCodexContentScreen extends Screen implements IBook {
    private static final int WIDTH = 271;
    private static final int HEIGHT = 179;
    private static final int TEXT_WIDTH = 114;
    private static final int TEXT_START_X = 36;
    private static final int TEXT_START_Y = 30;

    private @Nonnull PageContent pages;

    private int index = 0;
    private PageButton nextPageButton;
    private PageButton prevPageButton;

    private ReferenceOpenHashSet<PageContent> cache = new ReferenceOpenHashSet<>();
    private static final ResourceLocation BACKGROUND = MagicAndTabooMod.makeId("textures/gui/book/book.png");


    public OccultCodexContentScreen(PageContent pages) {
        super(Component.literal(""));
        this.pages = pages;
    }

    @Override
    protected void init() {
        int w = (this.width - WIDTH) / 2;
        int h = (this.height - HEIGHT) / 2;
        nextPageButton = this.addRenderableWidget(
                new PageButton(w + 269 - 17 + 3, h + 174, true, button -> setPage(1), true)
        );
        prevPageButton = this.addRenderableWidget(
                new PageButton(w - 2, h + 174, false, button -> setPage(-1), true)
        );

        updateButtonVisibility();
    }

    private void setPage(int delta) {
        index = Mth.clamp(index + delta, 0, pages.contents.size() - 1);
        updateButtonVisibility();
    }

    private void updateButtonVisibility() {
        prevPageButton.visible = index > 0;
        nextPageButton.visible = index < pages.contents.size() - 1;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics);

        int x = (this.width - WIDTH) / 2;
        int y = (this.height - HEIGHT) / 2;
        graphics.blit(BACKGROUND, x, y, 0, 0, WIDTH, HEIGHT, 512, 256);

        renderTitle(graphics, x, y);
        List<FormattedCharSequence> lines = this.font.split(pages.contents.get(index).text, TEXT_WIDTH);

        for (int i = 0; i < lines.size(); ++i) {
            graphics.drawString(this.font, lines.get(i), x + 15, y + 18 * 2 + i * 9, 0x000000, false);
        }

        super.render(graphics, mouseX, mouseY, partialTick);
    }

    private void renderTitle(GuiGraphics graphics, int x, int y) {
        var title = pages.contents.get(index).title;
        int w = font.width(title);
        graphics.drawString(this.font, title, x + (148 - w) / 2, y + 18, 0x000000, false);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        graphics.blit(BACKGROUND, x + 18, y + 30, 140, 180, 110, 3, 512, 256);
        RenderSystem.disableBlend();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 263) {
            setPage(-1);
            return true;
        } else if (keyCode == 262) {
            setPage(1);
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void open(@Nullable PageContent page) {
        if (page == null) {
            super.onClose();
        } else {
            this.pages = page;
        }
    }
}