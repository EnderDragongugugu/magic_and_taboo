package enderdragon.magic_and_taboo.client.gui;

import enderdragon.magic_and_taboo.client.book.Book;
import enderdragon.magic_and_taboo.client.book.Chapter;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Style;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Stack;

public class OccultCodexScreen extends Screen implements Book {
    private final Reference2ObjectOpenHashMap<Chapter, Collection<AbstractWidget>> cache = new Reference2ObjectOpenHashMap<>();
    private final Stack<Chapter> hidden = new Stack<>();
    private @Nonnull Chapter chapter;

    public OccultCodexScreen(@Nonnull Chapter chapter) {
        super(CommonComponents.EMPTY);
        this.chapter = chapter;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return this.chapter.onMouseDown(this, mouseX, mouseY, button) || super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return this.chapter.onMouseUp(this, mouseX, mouseY, button) || super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        return this.chapter.onDrag(dragX, dragY, button) || super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    protected void init() {
        super.init();
        this.cache.clear();
        this.openImpl(this.chapter);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(graphics);
        this.chapter.render(graphics, this.font, this.width, this.height, mouseX, mouseY, partialTicks);
        super.render(graphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public void onClose() {
        if (this.hidden.empty()) {
            super.onClose();
        } else {
            this.openImpl(this.hidden.pop());
        }
    }

    @Override
    public Chapter turnTo(@Nullable Chapter chapter, boolean isOverlay) {
        if (chapter == null) {
            super.onClose();
            return null;
        }
        if (isOverlay) {
            this.hidden.push(this.chapter);
        }
        this.openImpl(chapter);
        return chapter;
    }

    @Override
    public Font getFont() {
        return this.font;
    }

    @Override
    public boolean onClickComponent(@Nullable Style style) {
        return this.handleComponentClicked(style);
    }

    private void openImpl(@Nonnull Chapter chapter) {
        var widgets = this.cache.get(this.chapter);
        if (widgets != null) {
            for (var widget : widgets) {
                this.removeWidget(widget);
            }
        }
        widgets = this.cache.get(chapter);
        if (widgets == null) {
            this.cache.put(chapter, widgets = chapter.init(this.font, this.width, this.height));
        }
        for (var widget : widgets) {
            this.addRenderableWidget(widget);
        }
        this.chapter = chapter;
    }
}