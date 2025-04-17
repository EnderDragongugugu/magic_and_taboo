package enderdragon.magic_and_taboo.util;

import com.google.common.collect.ImmutableList;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class CollectionUtil {
    public static <T, E> T collect(
            Function<ImmutableList.Builder<E>, T> factory,
            UnaryOperator<ImmutableList.Builder<E>> action
    ) {
        return factory.apply(action.apply(ImmutableList.builder()));
    }
}
