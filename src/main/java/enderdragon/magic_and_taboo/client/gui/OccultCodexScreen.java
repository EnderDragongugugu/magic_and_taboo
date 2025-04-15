package enderdragon.magic_and_taboo.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import enderdragon.magic_and_taboo.MagicAndTabooMod;
import enderdragon.magic_and_taboo.client.book.IBook;
import enderdragon.magic_and_taboo.client.book.Line;
import enderdragon.magic_and_taboo.client.book.Node;
import enderdragon.magic_and_taboo.client.book.Page;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public class OccultCodexScreen extends Screen implements IBook {
    private static final int WIDTH = 283;
    private static final int HEIGHT = 182;
    private double scrollX = 0, scrollY = 0;
    private boolean isDragging = false;
    private boolean clickable;
    private static final ResourceLocation BACKGROUND = MagicAndTabooMod.makeId("textures/block/fir/fir_planks.png");
    private static final ResourceLocation BACKGROUND_1 = MagicAndTabooMod.makeId("textures/gui/book/frame_full.png");
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
        var pose = graphics.pose();
        RenderSystem.enableBlend();

        int startX = (int) scrollX;
        int startY = (int) scrollY;
//        test
        int x = (this.width - WIDTH) / 2;
        int y = (this.height - HEIGHT) / 2;

        pose.pushPose();
        pose.translate(0, 0, 200);
        graphics.blit(BACKGROUND_1, x, y, 0, 0, 283, 182, 283, 182);
        pose.popPose();

        graphics.enableScissor(x + 8, y + 8, x + 283 - 8, y + 182 - 8);

        renderBackground(graphics, pose, x, y);

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
        graphics.disableScissor();

        if (hoveredNode != null) {
            pose.pushPose();
            pose.translate(0, 0, 4000.0F);
            RenderSystem.enableDepthTest();
            renderTooltip(graphics, hoveredNode.getTooltip(), mouseX, mouseY);
            RenderSystem.disableDepthTest();
            pose.popPose();
        }

        super.render(graphics, mouseX, mouseY, partialTicks);
    }

    private void renderBackground(GuiGraphics graphics, PoseStack pose, int x, int y) {
        pose.pushPose();
        pose.translate((float) x + 8, (float) y + 8, 0.0F);
        int i = Mth.floor(this.scrollX);
        int j = Mth.floor(this.scrollY);
        int k = i % 16;
        int l = j % 16;

        for (int i1 = -1; i1 <= 15; ++i1) {
            for (int j1 = -1; j1 <= 8; ++j1) {
                graphics.blit(BACKGROUND, k + 16 * i1, l + 16 * j1, 0.0F, 0.0F, 16, 16, 16, 16);
            }
        }
        pose.popPose();
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