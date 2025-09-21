package enderdragon.magic_and_taboo.client.book.graph;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import enderdragon.magic_and_taboo.MagicAndTabooMod;
import enderdragon.magic_and_taboo.client.book.Book;
import enderdragon.magic_and_taboo.client.book.Chapter;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class GraphChapter implements Chapter {
    private static final int WIDTH = 269;
    private static final int HEIGHT = 176;
    private static final ResourceLocation PAGE_FRAME = MagicAndTabooMod.makeId("textures/gui/book/book_1.png");
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
        for (var node : this.nodes) {
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
        int startX = (int) scrollX;
        int startY = (int) scrollY;
//        test
        int x = (width - WIDTH) / 2;
        int y = (height - HEIGHT) / 2;

        RenderSystem.enableBlend();

        pose.pushPose();
        RenderSystem.disableBlend();
        graphics.fillGradient(
                RenderType.endGateway(),
                x + 6, y + 14, x + WIDTH - 6, y + HEIGHT - 6,
                0, 0, 0
        );

        RenderSystem.enableBlend();
        pose.popPose();

        pose.pushPose();
        pose.translate(0, 0, 200);
        graphics.blit(PAGE_FRAME, x, y, 0, 0, WIDTH, HEIGHT, 353, 256);
        pose.popPose();

        graphics.enableScissor(x + 8, y + 16, x + WIDTH - 4, y + HEIGHT - 4);

        for (Line line : this.lines) {
            line.render(graphics, startX, startY);
        }

        Node hovered = null;

        for (Node node : this.nodes) {
            node.render(graphics, font, startX, startY);
            if (Node.isMouseOver(node, mouseX, mouseY, startX, startY)) {
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
        this.clickable = false;
        return Collections.emptyList();
    }

    @Override
    public boolean onMouseDown(Book book, double x, double y, int button) {
        if (button != 0) return false;
        this.dragging = true;
        this.clickable = true;
        return true;
    }

    @Override
    public boolean onMouseUp(Book book, double x, double y, int button) {
        if (button != 0) return false;
        this.dragging = false;
        if (this.clickable) {
            int offsetX = (int) this.scrollX, offsetY = (int) this.scrollY;
            for (Node node : this.nodes) {
                if (Node.isMouseOver(node, x, y, offsetX, offsetY)) {
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
