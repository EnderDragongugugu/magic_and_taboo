package enderdragon.magic_and_taboo.client.book.page;

import com.mojang.blaze3d.systems.RenderSystem;
import enderdragon.magic_and_taboo.registry.Element;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

import static enderdragon.magic_and_taboo.client.ClientUtil.isMouseOver;
import static enderdragon.magic_and_taboo.client.book.page.PageChapter.PAGE_WIDTH;

public class ElementCatalog extends Catalog<Element> {
    private static final Reference2ObjectOpenHashMap<Element, Component> TOOLTIPS = new Reference2ObjectOpenHashMap<>();
    public static final int COLUMNS = 6;
    public static final int ICON_SIZE = 16;
    public static final int SMALL_PADDING = 1;
    public static final int LARGE_PADDING = 2;
    protected final ObjectArrayList<Icon> icons = new ObjectArrayList<>();
    private final Component title;
    protected int left;
    protected int top;

    public ElementCatalog(Component title) {
        this.title = title;
    }

    @Override
    public void layout(IndexedChapter<Element> chapter, Font font, int left, int top) {
        this.index.clear();
        this.icons.clear();
        this.left = left;
        this.top = top;
        var elements = chapter.entries.get();
        if (elements == null) return;
        int iconLeft = left, iconTop = top + font.wordWrapHeight(this.title, PAGE_WIDTH) + 4, columns = 0;
        for (var element : elements) {
            this.index.put(element, chapter.fillPage(chapter.descriptor.apply(element), font, top));
            this.icons.add(new Icon(element, iconLeft, iconTop));
            iconLeft += ICON_SIZE + LARGE_PADDING;
            if (++columns >= COLUMNS) {
                columns = 0;
                iconTop += ICON_SIZE + SMALL_PADDING;
                iconLeft = left;
            }
        }
    }

    @Override
    public void render(GuiGraphics graphics, Font font, int mouseX, int mouseY, float partialTicks) {
        int top = this.top, center = PAGE_WIDTH / 2 + this.left, height = font.lineHeight;
        for (var line : font.split(this.title, PAGE_WIDTH)) {
            graphics.drawString(font, line, center - font.width(line) / 2, top, 0, false);
            top += height;
        }
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        graphics.blit(TitleChunk.SEPARATOR, this.left + 3, top, 140, 180, 110, 3, 512, 256);
        RenderSystem.disableBlend();
        Component tooltip = null;
        for (var icon : this.icons) {
            graphics.blit(icon.element.icon(), icon.left, icon.top, 0, 0, ICON_SIZE, ICON_SIZE, ICON_SIZE, ICON_SIZE);
            if (tooltip == null && isMouseOver(mouseX, mouseY, icon.left, icon.top, ICON_SIZE, ICON_SIZE)) {
                tooltip = TOOLTIPS.computeIfAbsent(icon.element, Element::getDisplayName);
            }
        }
        if (tooltip != null) {
            graphics.renderTooltip(font, tooltip, mouseX, mouseY);
        }
    }

    @Override
    public boolean onMouseDown(IndexedChapter<Element> chapter, double x, double y, int button) {
        if (button != 0) return false;
        for (var icon : this.icons) {
            if (isMouseOver(x, y, icon.left, icon.top, ICON_SIZE, ICON_SIZE)) {
                chapter.turnTo(this.indexOf(icon.element));
                return true;
            }
        }
        return false;
    }

    public record Icon(Element element, int left, int top) {}
}
