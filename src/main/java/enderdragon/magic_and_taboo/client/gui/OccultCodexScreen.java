package enderdragon.magic_and_taboo.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class OccultCodexScreen extends Screen {
    private double scrollX = 0, scrollY = 0;
    private boolean isDragging = false;
    private double lastMouseX, lastMouseY;
    private boolean initialized = false;
    private static final ResourceLocation WINDOW_TEXTURE = new ResourceLocation("textures/gui/advancements/window.png");
    private static final ResourceLocation TABS_TEXTURE = new ResourceLocation("textures/gui/advancements/tabs.png");
    private final List<Node> nodes = new ArrayList<>();
    private Node hoveredNode = null; // 记录当前鼠标悬停的节点

    public OccultCodexScreen() {
        super(Component.literal(""));
        registerNodes();
    }

    private void registerNodes() {
        Node root = new Node("a", 0, 0);
        Node child1 = new Node("a 1", root.x + 50, root.y + 50, root);
        Node child2 = new Node("a 2", root.x - 50, root.y + 50, root);
        nodes.add(root);
        nodes.add(child1);
        nodes.add(child2);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) { // 左键
            for (Node node : nodes) {
                if (isMouseOverNode(node, mouseX, mouseY)) {
                    System.out.println("Clicked on node: " + node.label);
                    return true;
                }
            }
            isDragging = true;
            lastMouseX = mouseX;
            lastMouseY = mouseY;
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0) {
            isDragging = false;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (isDragging && button == 0) {
            scrollX += (mouseX - lastMouseX);
            scrollY += (mouseY - lastMouseY);
            lastMouseX = mouseX;
            lastMouseY = mouseY;
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        if (!initialized) {
            scrollX = (this.width / 2.0) - 10;
            scrollY = (this.height / 2.0) - 10;
            initialized = true;
        }

        this.renderBackground(guiGraphics);
        RenderSystem.enableBlend();

        int startX = (int) scrollX;
        int startY = (int) scrollY;

        guiGraphics.drawString(this.font, title, startX + 10, startY + 10, 0xFFFFFF, false);

        hoveredNode = null;

        // 绘制连接线
        for (Node node : nodes) {
            if (node.parent != null) {
                int x1 = (int) (startX + node.parent.x + 10);
                int y1 = (int) (startY + node.parent.y + 10);
                int x2 = (int) (startX + node.x + 10);
                int y2 = (int) (startY + node.y + 10);
                drawThickLine(x1, y1, x2, y2, 0xFFFFFFFF, 3.0f);
            }
        }

        // 绘制节点
        for (Node node : nodes) {
            int nodeX = (int) (startX + node.x);
            int nodeY = (int) (startY + node.y);
            int nodeSize = 20;

            // 判断鼠标是否悬停
            if (isMouseOverNode(node, mouseX, mouseY)) {
                hoveredNode = node;
            }

            // 绘制节点方块
            guiGraphics.fill(nodeX, nodeY, nodeX + nodeSize, nodeY + nodeSize, 0xFF00FF00);
            guiGraphics.drawString(this.font, node.label, nodeX + 5, nodeY + 5, 0xFFFFFF, false);
        }

        // 显示 Tooltip
        if (hoveredNode != null) {
            renderTooltip(guiGraphics, hoveredNode.label, mouseX, mouseY);
        }

        super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    private void drawThickLine(int x1, int y1, int x2, int y2, int color, float thickness) {
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder buffer = tesselator.getBuilder();

        // 计算线条方向
        double dx = x2 - x1;
        double dy = y2 - y1;
        double length = Math.sqrt(dx * dx + dy * dy);
        double nx = -dy / length * thickness / 2;
        double ny = dx / length * thickness / 2;

        // 颜色分量
        float red = (color >> 16 & 255) / 255.0F;
        float green = (color >> 8 & 255) / 255.0F;
        float blue = (color & 255) / 255.0F;
        float alpha = ((color >> 24) & 255) / 255.0F;

        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        buffer.vertex(x1 + nx, y1 + ny, 0).color(red, green, blue, alpha).endVertex();
        buffer.vertex(x1 - nx, y1 - ny, 0).color(red, green, blue, alpha).endVertex();
        buffer.vertex(x2 - nx, y2 - ny, 0).color(red, green, blue, alpha).endVertex();
        buffer.vertex(x2 + nx, y2 + ny, 0).color(red, green, blue, alpha).endVertex();
        tesselator.end();

        RenderSystem.disableBlend();
    }


    private boolean isMouseOverNode(Node node, double mouseX, double mouseY) {
        int nodeSize = 20;
        int startX = (int) (scrollX + node.x);
        int startY = (int) (scrollY + node.y);
        return mouseX >= startX && mouseX <= startX + nodeSize &&
                mouseY >= startY && mouseY <= startY + nodeSize;
    }

    private void renderTooltip(GuiGraphics guiGraphics, String text, int mouseX, int mouseY) {
//        List<Component> tooltip = new ArrayList<>();
//        tooltip.add(Component.literal(text));
        guiGraphics.renderTooltip(font, Component.nullToEmpty(text), mouseX, mouseY);
    }

    private static class Node {
        String label;
        double x, y;
        Node parent;

        public Node(String label, double x, double y) {
            this(label, x, y, null);
        }

        public Node(String label, double x, double y, Node parent) {
            this.label = label;
            this.x = x;
            this.y = y;
            this.parent = parent;
        }
    }
}