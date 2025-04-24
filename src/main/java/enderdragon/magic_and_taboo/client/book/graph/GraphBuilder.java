package enderdragon.magic_and_taboo.client.book.graph;

import com.google.common.collect.ImmutableList;
import net.minecraft.advancements.FrameType;
import org.jetbrains.annotations.Nullable;

import java.util.function.UnaryOperator;

public class GraphBuilder implements Builder {
    private final ImmutableList.Builder<Node> nodes = ImmutableList.builder();

    public GraphBuilder child(FrameType type, UnaryOperator<NodeBuilder<GraphBuilder>> action) {
        return this.child(this, type, action);
    }

    public <T extends Builder> GraphBuilder child(T parent, FrameType type, UnaryOperator<NodeBuilder<T>> action) {
        this.nodes.add(action.apply(new NodeBuilder<>(this, parent, type)).asNode());
        return this;
    }

    public GraphChapter build() {
        return new GraphChapter(this.nodes);
    }

    @Override
    public @Nullable Node asNode() {
        return null;
    }
}
