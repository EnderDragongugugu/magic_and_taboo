package enderdragon.magic_and_taboo.client.book.graph;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import enderdragon.magic_and_taboo.MagicAndTabooMod;
import enderdragon.magic_and_taboo.client.book.IBook;
import enderdragon.magic_and_taboo.client.book.IChapter;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class GraphChapter implements IChapter {
    private static final ResourceLocation BACKGROUND = MagicAndTabooMod.makeId("textures/block/fir/fir_planks.png");
    private static final ResourceLocation PAGE_FRAME = MagicAndTabooMod.makeId("textures/gui/book/frame_full.png");
    private static final int WIDTH = 283;
    private static final int HEIGHT = 182;
    public final ImmutableList<Node> nodes;
    public final ImmutableList<Line> lines;
    private double scrollX;
    private double scrollY;
    private boolean dragging;
    private boolean clickable;

    @SuppressWarnings("UnstableApiUsage")
    public GraphChapter(ImmutableList.Builder<Node> nodes) {
        this.nodes = nodes.build();
        var builder = ImmutableList.<Line>builderWithExpectedSize(this.nodes.size());
        for (Node node : this.nodes) {
            if (node.parent == null) continue;
            builder.add(new Line(node.parent, node));
        }
        this.lines = builder.build();
    }

    @Override
    public void render(
            GuiGraphics graphics,
            Font font,
            int width,
            int height,
            int mouseX,
            int mouseY,
            float partialTicks
    ) {
        var pose = graphics.pose();
        RenderSystem.enableBlend();

        int startX = (int) scrollX;
        int startY = (int) scrollY;
//        test
        int x = (width - WIDTH) / 2;
        int y = (height - HEIGHT) / 2;

        pose.pushPose();
        pose.translate(0, 0, 200);
        graphics.blit(PAGE_FRAME, x, y, 0, 0, 283, 182, 283, 182);
        pose.popPose();

        graphics.enableScissor(x + 8, y + 8, x + 283 - 8, y + 182 - 8);
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

        for (Line line : this.lines) {
            line.render(graphics, startX, startY);
        }

        Node hovered = null;

        for (Node node : this.nodes) {
            node.render(graphics, font, startX, startY);
            if (node.isHovered(startX, startY, mouseX, mouseY)) {
                hovered = node;
            }
        }
        graphics.disableScissor();

        if (hovered != null) {
            pose.pushPose();
            pose.translate(0, 0, 4000.0F);
            RenderSystem.enableDepthTest();
            graphics.renderTooltip(font, hovered.getTooltip(), Optional.empty(), mouseX, mouseY);
            RenderSystem.disableDepthTest();
            pose.popPose();
        }
    }

    @Override
    public Collection<AbstractWidget> init(Font font, int width, int height) {
        this.scrollX = width / 2.0;
        this.scrollY = height / 2.0;
        this.nodes.forEach(Node::reload);
        return Collections.emptyList();
    }

    @Override
    public boolean onMouseDown(double x, double y, int button) {
        if (button != 0) return false;
        this.dragging = true;
        this.clickable = true;
        return true;
    }

    @Override
    public boolean onMouseUp(IBook book, double x, double y, int button) {
        if (button != 0) return false;
        this.dragging = false;
        if (this.clickable) {
            int offsetX = (int) this.scrollX, offsetY = (int) this.scrollY;
            for (Node node : this.nodes) {
                if (node.isHovered(offsetX, offsetY, x, y)) {
                    node.onClick.trigger(book);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean onDrag(double deltaX, double deltaY, int button) {
        if (button != 0 || !this.dragging) return false;
        this.scrollX += deltaX;
        this.scrollY += deltaY;
        if (deltaX > 0.5 || deltaX < -0.5 || deltaY > 0.5 || deltaY < -0.5) {
            this.clickable = false;
        }
        return true;
    }
}
