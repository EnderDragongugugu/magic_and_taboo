package enderdragon.magicandtaboo.util;

import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.apache.logging.log4j.LogManager;

import java.util.function.Predicate;
import java.util.logging.Logger;

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
    public static void consumeStack(ItemStack itemStack,int count){
        if(itemStack.getCount() > 1){
            itemStack.setCount(count);
        }else {
            itemStack.setCount(0);
        }
    }
}
