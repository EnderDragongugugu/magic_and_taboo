package enderdragon.magicandtaboo.util;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemUtil {
    public static ItemStack getUsingItemStack(LivingEntity entity) {
        ItemStack mainHand = entity.getMainHandItem();
        if (!mainHand.isEmpty()) return mainHand;
        ItemStack offHand = entity.getOffhandItem();
        if (!mainHand.isEmpty()) return offHand;
        return ItemStack.EMPTY;
    }

    /**
     * @deprecated USE {@link ContainerUtil#find}
     **/
    @Deprecated
    public static int findFirstItemStack(Inventory inventory, Item item) {
        ItemStack find = new ItemStack(item);
        for (int slot = 0; slot < inventory.getContainerSize(); slot++) {
            ItemStack itemStack = inventory.getItem(slot);
            if (!itemStack.isEmpty() && ItemStack.matches(itemStack, find)) {
                return slot;
            }
        }
        return -1;
    }
}
