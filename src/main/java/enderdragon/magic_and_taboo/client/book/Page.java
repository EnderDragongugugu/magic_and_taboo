package enderdragon.magic_and_taboo.client.book;

import com.google.common.collect.ImmutableList;
import net.minecraft.advancements.FrameType;
import org.jetbrains.annotations.Nullable;

import java.util.function.UnaryOperator;

public class Page {
    public final ImmutableList<Node> nodes;
    public final ImmutableList<Line> lines;
    public final IListener onClose;

    @SuppressWarnings("UnstableApiUsage")
    public Page(IListener onClose, ImmutableList.Builder<Node> nodes) {
        this.onClose = onClose;
        this.nodes = nodes.build();
        var builder = ImmutableList.<Line>builderWithExpectedSize(this.nodes.size());
        for (Node node : this.nodes) {
            if (node.parent == null) continue;
            builder.add(new Line(node.parent, node));
        }
        this.lines = builder.build();
    }

    public static class NodeBuilder implements INodeBuilder {
        public final IListener onClose;
        private final ImmutableList.Builder<Node> nodes = ImmutableList.builder();

        public NodeBuilder(IListener onClose) {
            this.onClose = onClose;
        }

        public NodeBuilder child(FrameType type, UnaryOperator<Node.Builder<NodeBuilder>> action) {
            return this.child(this, type, action);
        }

        public <T extends INodeBuilder> NodeBuilder child(T parent, FrameType type, UnaryOperator<Node.Builder<T>> action) {
            this.nodes.add(action.apply(new Node.Builder<>(this, parent, type)).asNode());
            return this;
        }

        public Page build() {
            return new Page(this.onClose, this.nodes);
        }

        @Override
        public @Nullable Node asNode() {
            return null;
        }
    }
}
