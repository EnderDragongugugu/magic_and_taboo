package enderdragon.magic_and_taboo.client.book.linear;

import com.google.common.collect.ImmutableList;
import enderdragon.magic_and_taboo.client.book.Book;
import enderdragon.magic_and_taboo.client.book.OccultCodexChapters;
import enderdragon.magic_and_taboo.registry.Element;
import enderdragon.magic_and_taboo.util.RegistryAccessor;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static enderdragon.magic_and_taboo.client.ClientUtil.isMouseOver;
import static enderdragon.magic_and_taboo.util.CollectionUtil.collect;

public class ElementChunk extends Chunk {
    public static final int COLUMNS = 7;
    public static final int ICON_SIZE = 16;
    public static final int SMALL_PADDING = 1;
    public static final int LARGE_PADDING = 2;
    private static final Reference2ObjectOpenHashMap<Element, Component> TOOLTIPS = new Reference2ObjectOpenHashMap<>();
    private List<Element> cache = Collections.emptyList();

    @Override
    public int measure(Font font, int space) {
        var access = RegistryAccessor.access();
        if (access == null) return 0;
        this.cache = access.registryOrThrow(Element.RESOURCE_KEY).stream().collect(Collectors.toList());
        return Mth.ceil(this.cache.size() / (float) COLUMNS) * (ICON_SIZE + SMALL_PADDING);
    }

    @Override
    public void render(
            GuiGraphics graphics,
            Font font,
            int mouseX,
            int mouseY,
            float partialTicks
    ) {
        boolean showTooltip = true;
        int top = this.top, left = this.left, columns = 0;
        for (Element element : this.cache) {
            graphics.blit(element.icon(), left, top, 0, 0, ICON_SIZE, ICON_SIZE, 16, 16);
            if (showTooltip && isMouseOver(mouseX, mouseY, left, top, ICON_SIZE, ICON_SIZE)) {
                showTooltip = false;
                graphics.renderTooltip(font, TOOLTIPS.computeIfAbsent(element, Element::getDisplayName), mouseX, mouseY);
            }
            left += ICON_SIZE + LARGE_PADDING;
            if (++columns >= COLUMNS) {
                columns = 0;
                top += ICON_SIZE + SMALL_PADDING;
                left = this.left;
            }
        }
    }

    @Override
    public boolean onMouseDown(Book book, double x, double y, int button) {
        if (button != 0) return false;
        int currentLeft = this.left, currentTop = this.top, columns = 0, index = 0;

        for (Element element : this.cache) {
            if (isMouseOver(x, y, currentLeft, currentTop, ICON_SIZE, ICON_SIZE)) {
                book.turnTo(OccultCodexChapters.ELEMENTS, false).setPage(index / 2 + 1);
                return true;
            }
            currentLeft += ICON_SIZE + LARGE_PADDING;
            if (++columns >= COLUMNS) {
                columns = 0;
                currentTop += ICON_SIZE + SMALL_PADDING;
                currentLeft = this.left;
            }
            index++;
        }

        return false;
    }

    public static ImmutableList<Chunk> renderImage() {
        var chunks = ImmutableList.<Chunk>builder();
        chunks.add(Separator.NEXT_PAGE);
        var access = RegistryAccessor.access();
        if (access == null) return chunks.build();
        var elements = access.registryOrThrow(Element.RESOURCE_KEY);
        for (var element : elements) {
            var image = new ImageChunk(element.icon(), 26, 0, 0, 0, ICON_SIZE * 4, ICON_SIZE * 4, ICON_SIZE * 4, ICON_SIZE * 4);
            chunks.add(image);
            chunks.add(collect(TextChunk::new, lines -> lines
                    .add(Component.translatable("text.occult_codex.node.potion_1.content_1.name", element.getDisplayName()))
                    .add(Component.translatable("text.occult_codex.node.potion_1.content_1.max_level", element.maxLevel()))
                    .add(Component.translatable("text.occult_codex.node.potion_1.content_1.max_time", element.maxTime()))
                    .add(Component.translatable("text.occult_codex.node.potion_1.content_1.concentration", element.concentration().min(), element.concentration().max()))
                    .add(Component.translatable("text.occult_codex.node.potion_1.content_1.temperature_range", element.temperature().min(), element.temperature().max()))
            ));
            chunks.add(Separator.NEXT_PAGE);
        }
        return chunks.build();
    }
}
