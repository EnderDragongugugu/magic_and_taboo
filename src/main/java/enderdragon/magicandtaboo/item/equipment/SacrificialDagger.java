package enderdragon.magicandtaboo.item.equipment;

import enderdragon.magicandtaboo.MagicAndTabooMod;
import enderdragon.magicandtaboo.util.ItemUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class SacrificialDagger extends SwordItem {
    public SacrificialDagger(Properties pProperties) {
        super(Tiers.IRON, 1, 1, pProperties);
    }

//    @Override
//    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
//        LogManager.getLogger().debug("test");
//        return super.use(pLevel, pPlayer, pUsedHand);
//    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        if(pAttacker instanceof Player player){
            Inventory inventory = new Inventory(player);
//            LogManager.getLogger().debug(Items.GLASS_BOTTLE.TAG);
            int slot = ItemUtil.findFirstItemStack(inventory, Items.GLASS_BOTTLE);
            player.displayClientMessage(Component.translatable(slot + ""),false);
        }
        return true;
    }
}
