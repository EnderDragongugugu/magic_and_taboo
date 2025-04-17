package enderdragon.magic_and_taboo.client.book.graph;

import com.google.common.collect.ImmutableList;
import enderdragon.magic_and_taboo.client.book.IListener;
import net.minecraft.advancements.FrameType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class NodeBuilder<T extends IBuilder> implements IBuilder {
    public static final Supplier<ItemStack> NO_ICON = () -> ItemStack.EMPTY;
    public final ImmutableList.Builder<Component> tooltip = ImmutableList.builder();
    public final @Nonnull GraphBuilder builder;
    public final @Nonnull T parent;
    public final @Nonnull FrameType type;
    private Node node;
    private IListener onClick = IListener.NONE;
    private Supplier<ItemStack> icon = NO_ICON;
    private int x;
    private int y;

    public NodeBuilder(@Nonnull GraphBuilder builder, @Nonnull T parent, @Nonnull FrameType type) {
        this.builder = builder;
        this.parent = parent;
        this.type = type;
    }

    public NodeBuilder<T> withIcon(Supplier<ItemStack> supplier) {
        if (this.node == null) {
            this.icon = supplier;
        }
        return this;
    }

    public NodeBuilder<T> described(String description) {
        return this.described(Component.translatable(description));
    }

    public NodeBuilder<T> described(Component description) {
        if (this.node == null) {
            this.tooltip.add(description);
        }
        return this;
    }

    public NodeBuilder<T> posited(int x, int y) {
        if (this.node == null) {
            this.x = x;
            this.y = y;
        }
        return this;
    }

    public NodeBuilder<T> clicked(IListener callback) {
        if (this.node == null) {
            this.onClick = callback;
        }
        return this;
    }

    public NodeBuilder<T> child(FrameType type, UnaryOperator<NodeBuilder<NodeBuilder<T>>> action) {
        this.builder.child(this, type, action);
        return this;
    }

    @Nonnull
    @Override
    public Node asNode() {
        return this.node == null ? this.node = new Node(
                this.parent.asNode(),
                this.type,
                this.tooltip,
                this.x,
                this.y,
                this.onClick,
                this.icon
        ) : this.node;
    }
}
