package enderdragon.magic_and_taboo.client.gui;

import enderdragon.magic_and_taboo.client.book.IBook;
import enderdragon.magic_and_taboo.client.book.IChapter;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Stack;

public class OccultCodexScreen extends Screen implements IBook {
    private final Reference2ObjectOpenHashMap<IChapter, Collection<AbstractWidget>> cache = new Reference2ObjectOpenHashMap<>();
    private final Stack<IChapter> hidden = new Stack<>();
    private @Nonnull IChapter chapter;

    public OccultCodexScreen(@Nonnull IChapter chapter) {
        super(CommonComponents.EMPTY);
        this.chapter = chapter;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return this.chapter.onMouseDown(mouseX, mouseY, button) || super.mouseClicked(mouseX, mouseY, button);
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
    public void turnTo(@Nullable IChapter chapter) {
        if (chapter == null) {
            super.onClose();
        } else {
            this.hidden.push(this.chapter);
            this.openImpl(chapter);
        }
    }

    private void openImpl(@Nonnull IChapter chapter) {
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