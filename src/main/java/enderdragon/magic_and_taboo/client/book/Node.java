package enderdragon.magic_and_taboo.client.book;

import net.minecraft.advancements.FrameType;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class Node {
    private static final ResourceLocation WIDGETS_LOCATION = new ResourceLocation("textures/gui/advancements/widgets.png");
    public final Node parent;
    public final FrameType type;
    public final String label;
    public final String description;
    public final int halfWidth;
    public final int halfHeight;
    public final int x;
    public final int y;
    public final IListener onClick;
    private final Supplier<ItemStack> iconSupplier;
    private @Nonnull ItemStack icon = ItemStack.EMPTY;
    private @Nonnull List<Component> tooltip = List.of();

    public Node(
            Node parent,
            FrameType type,
            String label,
            String description,
            int x,
            int y,
            int width,
            int height,
            IListener onClick,
            Supplier<ItemStack> icon
    ) {
        this.parent = parent;
        this.label = label;
        this.iconSupplier = icon;
        this.type = type;
        this.description = description;
        this.x = x;
        this.y = y;
        this.halfWidth = width / 2;
        this.halfHeight = height / 2;
        this.onClick = onClick;
    }

    public void reload() {
        this.icon = this.iconSupplier.get();
        this.tooltip = this.description.isEmpty() ? List.of(
                Component.translatable(this.label)
        ) : List.of(
                Component.translatable(this.label),
                Component.translatable(this.description)
        );
    }

    public boolean isHovered(int offsetX, int offsetY, double mouseX, double mouseY) {
        int x = offsetX + this.x;
        int y = offsetY + this.y;
        return mouseX >= x - this.halfWidth && mouseX <= x + this.halfWidth &&
                mouseY >= y - this.halfHeight && mouseY <= y + this.halfHeight;
    }

    public @NotNull List<Component> getTooltip() {
        return this.tooltip;
    }

    public void render(GuiGraphics graphics, Font font, int offsetX, int offsetY) {
        int x = offsetX + this.x;
        int y = offsetY + this.y;
        graphics.blit(WIDGETS_LOCATION, x - 13, y - 13, this.type.getTexture(), 128, 26, 26);
        if (!icon.isEmpty()) {
            graphics.renderItem(icon, x - 8, y - 8);
        }
    }

    public static class Builder<T extends IBuilder> implements IBuilder {
        public static final Supplier<ItemStack> NO_ICON = () -> ItemStack.EMPTY;
        public final @Nonnull Page.Builder builder;
        public final @Nonnull T parent;
        public final @Nonnull FrameType type;
        private Node node;
        private IListener onClick = IListener.NONE;
        private String label;
        private Supplier<ItemStack> icon = NO_ICON;
        private String description = "";
        private int x;
        private int y;
        private int width = 20;
        private int height = 20;

        public Builder(@Nonnull Page.Builder builder, @Nonnull T parent, @Nonnull FrameType type) {
            this.builder = builder;
            this.parent = parent;
            this.type = type;
        }

        public Builder<T> withIcon(Supplier<ItemStack> supplier) {
            if (this.node == null) {
                this.icon = supplier;
            }
            return this;
        }

        public Builder<T> labeled(String label) {
            if (this.node == null) {
                this.label = label;
            }
            return this;
        }

        public Builder<T> described(String description) {
            if (this.node == null) {
                this.description = description;
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

        public Builder<T> clicked(IListener callback) {
            if (this.node == null) {
                this.onClick = callback;
            }
            return this;
        }

        public Builder<T> child(FrameType type, UnaryOperator<Builder<Builder<T>>> action) {
            this.builder.child(this, type, action);
            return this;
        }

        @Nonnull
        @Override
        public Node asNode() {
            return this.node == null ? this.node = new Node(
                    this.parent.asNode(),
                    this.type,
                    this.label,
                    this.description,
                    this.x,
                    this.y,
                    this.width,
                    this.height,
                    this.onClick,
                    this.icon
            ) : this.node;
        }
    }
}
