package enderdragon.magic_and_taboo.client.book.graph;

import net.minecraft.client.gui.GuiGraphics;

import javax.annotation.Nonnull;

public class Line {
    public final int leftX;
    public final int leftY;
    public final int rightX;
    public final int rightY;
    public final int corner;

    public Line(@Nonnull Node parent, @Nonnull Node child) {
        if (parent.x < child.x) {
            this.leftX = parent.x;
            this.leftY = parent.y;
            this.rightX = child.x;
            this.rightY = child.y;
        } else {
            this.leftX = child.x;
            this.leftY = child.y;
            this.rightX = parent.x;
            this.rightY = parent.y;
        }
        this.corner = parent.y > child.y
                ? (child.y + child.halfHeight + parent.y - parent.halfHeight) / 2
                : (parent.y + parent.halfHeight + child.y - child.halfHeight) / 2;
    }

    public void render(GuiGraphics graphics, int offsetX, int offsetY) {
        int leftX = offsetX + this.leftX;
        int leftY = offsetY + this.leftY;
        int rightX = offsetX + this.rightX;
        int rightY = offsetY + this.rightY;
        int corner = offsetY + this.corner;
        graphics.vLine(leftX, leftY, corner, -1);
        graphics.hLine(leftX, rightX, corner, -1);
        graphics.vLine(rightX, corner, rightY, -1);
    }
}
