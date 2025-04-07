package enderdragon.magic_and_taboo.client.book;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

import javax.annotation.Nonnull;
import java.util.function.UnaryOperator;

public class Node {
    public final Node parent;
    public final IListener onClick;
    public final String label;
    public final int halfWidth;
    public final int halfHeight;
    public final int x;
    public final int y;

    public Node(
            Node parent,
            String label,
            int x,
            int y,
            int width,
            int height,
            IListener onClick
    ) {
        this.parent = parent;
        this.label = label;
        this.x = x;
        this.y = y;
        this.halfWidth = width / 2;
        this.halfHeight = height / 2;
        this.onClick = onClick;
    }

    public boolean isHovered(int offsetX, int offsetY, double mouseX, double mouseY) {
        int x = offsetX + this.x;
        int y = offsetY + this.y;
        return mouseX >= x - this.halfWidth && mouseX <= x + this.halfWidth &&
                mouseY >= y - this.halfHeight && mouseY <= y + this.halfHeight;
    }

    public void render(GuiGraphics graphics, Font font, int offsetX, int offsetY) {
        int x = offsetX + this.x;
        int y = offsetY + this.y;
        graphics.fill(x - this.halfWidth, y - this.halfHeight, x + this.halfWidth, y + this.halfHeight, 0xFF00FF00);
        graphics.drawString(
                font,
                this.label,
                x - font.width(this.label) / 2,
                y - font.lineHeight / 2,
                0xFFFFFF,
                false
        );
    }

    public static class Builder<T extends IBuilder> implements IBuilder {
        public final @Nonnull Page.Builder builder;
        public final @Nonnull T parent;
        private Node node;
        private IListener onClick = IListener.NONE;
        private String label;
        private int x;
        private int y;
        private int width = 20;
        private int height = 20;

        public Builder(@Nonnull Page.Builder builder, @Nonnull T parent) {
            this.builder = builder;
            this.parent = parent;
        }

        public Builder<T> pressed(IListener callback) {
            if (this.node == null) {
                this.onClick = callback;
            }
            return this;
        }

        public Builder<T> labeled(String label) {
            if (this.node == null) {
                this.label = label;
            }
            return this;
        }

        public Builder<T> sized(int width, int height) {
            if (this.node == null) {
                this.width = width;
                this.height = height;
            }
            return this;
        }

        public Builder<T> posited(int x, int y) {
            if (this.node == null) {
                this.x = x;
                this.y = y;
            }
            return this;
        }

        public Builder<T> child(UnaryOperator<Builder<Builder<T>>> action) {
            this.builder.child(this, action);
            return this;
        }

        @Nonnull
        @Override
        public Node asNode() {
            return this.node == null ? this.node = new Node(
                    this.parent.asNode(),
                    this.label,
                    this.x,
                    this.y,
                    this.width,
                    this.height,
                    this.onClick
            ) : this.node;
        }
    }
}
