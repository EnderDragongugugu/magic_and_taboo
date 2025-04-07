package enderdragon.magic_and_taboo.client.book;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class Node {
    private static final ResourceLocation WIDGETS_LOCATION = new ResourceLocation("textures/gui/advancements/widgets.png");
    public final Node parent;
    public final IListener onClick;
    public final String label;
    public final String content;
    public final int halfWidth;
    public final int halfHeight;
    public final int x;
    public final int y;
    private final ItemStack icon;
    public final int nodeType;

    public Node(
            Node parent,
            String label,
            ItemStack icon,
            int nodeType,
            String content,
            int x,
            int y,
            int width,
            int height,
            IListener onClick
    ) {
        this.parent = parent;
        this.label = label;
        this.icon = icon;
        this.nodeType = nodeType;
        this.content = content;
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

    public List<Component> text() {
        List<Component> list = new ArrayList<>();
        list.add(Component.translatable(label));
        if (!content.isEmpty()) {
            list.add(Component.translatable(content));
        }
        return list;
    }

    public void render(GuiGraphics graphics, Font font, int offsetX, int offsetY) {
        int x = offsetX + this.x;
        int y = offsetY + this.y;
        graphics.blit(WIDGETS_LOCATION, x - 13, y - 13, nodeType * 26, 128, 26, 26);
        if (!icon.isEmpty()) {
            graphics.renderItem(icon, x - 8, y - 8);
        }
    }

    public static class Builder<T extends IBuilder> implements IBuilder {
        public final @Nonnull Page.Builder builder;
        public final @Nonnull T parent;
        private Node node;
        private IListener onClick = IListener.NONE;
        private String label;
        private ItemStack icon = new ItemStack(Items.AIR);
        private String content = "";
        private int nodeType = 0;
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

        public Builder<T> content(String content) {
            if (this.node == null) {
                this.content = content;
            }
            return this;
        }

        public Builder<T> icon(Item item) {
            if (this.node == null) {
                var itemStack = new ItemStack(item);
                this.icon = itemStack;
            }
            return this;
        }

        public Builder<T> nodeType(int type) {
            if (this.node == null) {
                this.nodeType = type;
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
                    this.icon,
                    this.nodeType,
                    this.content,
                    this.x,
                    this.y,
                    this.width,
                    this.height,
                    this.onClick
            ) : this.node;
        }
    }

    public interface NodeType {
        int CHAPTER = 0;
        int SECTION = 1;
        int PART = 2;
    }
}
