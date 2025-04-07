package enderdragon.magic_and_taboo.client.book;

import com.google.common.collect.ImmutableList;
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

    public static class Builder implements IBuilder {
        public final IListener onClose;
        private final ImmutableList.Builder<Node> nodes = ImmutableList.builder();

        public Builder(IListener onClose) {
            this.onClose = onClose;
        }

        public Builder child(UnaryOperator<Node.Builder<Builder>> action) {
            return this.child(this, action);
        }

        public <T extends IBuilder> Builder child(T parent, UnaryOperator<Node.Builder<T>> action) {
            this.nodes.add(action.apply(new Node.Builder<>(this, parent)).asNode());
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
