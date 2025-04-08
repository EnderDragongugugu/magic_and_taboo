package enderdragon.magic_and_taboo.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import enderdragon.magic_and_taboo.client.book.IBook;
import enderdragon.magic_and_taboo.client.book.Line;
import enderdragon.magic_and_taboo.client.book.Node;
import enderdragon.magic_and_taboo.client.book.Page;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public class OccultCodexScreen extends Screen implements IBook {
    private double scrollX = 0, scrollY = 0;
    private boolean isDragging = false;
    private boolean clickable;
    private static final ResourceLocation WINDOW_TEXTURE = new ResourceLocation("textures/gui/advancements/window.png");
    private static final ResourceLocation TABS_TEXTURE = new ResourceLocation("textures/gui/advancements/tabs.png");
    private ReferenceOpenHashSet<Page> cache = new ReferenceOpenHashSet<>();
    private @Nonnull Page page;

    public OccultCodexScreen(@Nonnull Page page) {
        super(Component.literal(""));
        this.page = page;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            isDragging = true;
            this.clickable = true;
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0) {
            isDragging = false;
            if (this.clickable) {
                int offsetX = (int) scrollX, offsetY = (int) scrollY;
                for (Node node : this.page.nodes) {
                    if (node.isHovered(offsetX, offsetY, mouseX, mouseY)) {
                        node.onClick.trigger(this);
                        return true;
                    }
                }
            }
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }


    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (isDragging && button == 0) {
            scrollX += dragX;
            scrollY += dragY;
            if (dragX > 0.5 || dragX < -0.5 || dragY > 0.5 || dragY < -0.5) {
                this.clickable = false;
            }
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    protected void init() {
        super.init();
        scrollX = this.width / 2.0;
        scrollY = this.height / 2.0;
        this.cache.clear();
        this.open(this.page);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(graphics);
        RenderSystem.enableBlend();

        int startX = (int) scrollX;
        int startY = (int) scrollY;

        graphics.drawString(this.font, title, startX + 10, startY + 10, 0xFFFFFF, false);

        for (Line line : this.page.lines) {
            line.render(graphics, startX, startY);
        }

        Node hoveredNode = null;

        for (Node node : this.page.nodes) {
            node.render(graphics, this.font, startX, startY);
            if (node.isHovered(startX, startY, mouseX, mouseY)) {
                hoveredNode = node;
            }
        }

        if (hoveredNode != null) {
            renderTooltip(graphics, hoveredNode.getTooltip(), mouseX, mouseY);
        }

        super.render(graphics, mouseX, mouseY, partialTicks);
    }

    private void renderTooltip(GuiGraphics graphics, List<Component> text, int mouseX, int mouseY) {
        graphics.renderTooltip(font, text, Optional.empty(), mouseX, mouseY);
    }

    @Override
    public void onClose() {
        this.page.onClose.trigger(this);
    }

    @Override
    public void open(@Nullable Page page) {
        if (page == null) {
            super.onClose();
        } else {
            if (this.cache.add(page)) {
                page.nodes.forEach(Node::reload);
            }
            this.page = page;
        }
    }
}