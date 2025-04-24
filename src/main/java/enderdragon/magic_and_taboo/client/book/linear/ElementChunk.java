package enderdragon.magic_and_taboo.client.book.linear;

import enderdragon.magic_and_taboo.client.ClientUtil;
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
            if (showTooltip && ClientUtil.isMouseOver(mouseX, mouseY, left, top, ICON_SIZE, ICON_SIZE)) {
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
}
