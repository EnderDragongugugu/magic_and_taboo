package enderdragon.magicandtaboo.util;

import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Predicate;

public class ContainerUtil {
    public static int find(Container container, Item item) {
        for (int i = 0, n = container.getContainerSize(); i < n; ++i) {
            if (container.getItem(i).getItem() == item) return i;
        }
        return -1;
    }

    public static int find(Container container, TagKey<Item> tag) {
        for (int i = 0, n = container.getContainerSize(); i < n; ++i) {
            if (container.getItem(i).is(tag)) return i;
        }
        return -1;
    }

    public static int find(Container container, Predicate<ItemStack> predicate) {
        for (int i = 0, n = container.getContainerSize(); i < n; ++i) {
            if (predicate.test(container.getItem(i))) return i;
        }
        return -1;
    }

    @SafeVarargs
    public static @NotNull ItemStack findStack(Item item, List<ItemStack>... areas) {
        for (int i = 0, n = areas.length; i < n; ++i) {
            var stacks = areas[i];
            for (int j = 0, m = stacks.size(); j < m; ++j) {
                var stack = stacks.get(i);
                if (stack.getItem() == item) return stack;
            }
        }
        return ItemStack.EMPTY;
    }
}
