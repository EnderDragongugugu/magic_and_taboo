package enderdragon.magicandtaboo.util;

import enderdragon.magicandtaboo.MagicAndTabooMod;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.apache.logging.log4j.LogManager;

import java.util.Map;

public class ItemUtil {
    public static ItemStack getUsingItemStack(LivingEntity entity){
        ItemStack mainHand = entity.getMainHandItem();
        if(!mainHand.isEmpty())  return mainHand;
        ItemStack offHand = entity.getOffhandItem();
        if(!mainHand.isEmpty())  return offHand;
        return ItemStack.EMPTY;
    }
    public static int findFirstItemStack(Inventory inventory, Item item){
        ItemStack find = new ItemStack(item);
        for(int slot = 0;slot < inventory.getContainerSize();slot++){
            ItemStack itemStack = inventory.getItem(slot);
            if(!itemStack.isEmpty() && itemStack.matches(itemStack,find)){
                return slot;
            }
        }
        return -1;
    }
}
