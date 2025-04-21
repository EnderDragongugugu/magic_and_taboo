package enderdragon.magic_and_taboo.client.book.linear;

import com.google.common.collect.ImmutableList;

public record Page(
        ImmutableList<IChunk> left,
        ImmutableList<IChunk> right
) {
    public static final int PAGE_WIDTH = 118;
    public static final int PAGE_HEIGHT = 150;
}
