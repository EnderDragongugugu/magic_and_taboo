package enderdragon.magicandtaboo.util;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class ItemUtil {
    public static ItemStack getUsingItemStack(LivingEntity entity){
        ItemStack mainHand = entity.getMainHandItem();
        if(!mainHand.isEmpty())  return mainHand;
        ItemStack offHand = entity.getOffhandItem();
        if(!mainHand.isEmpty())  return offHand;
        return ItemStack.EMPTY;
    }
}
