package enderdragon.magic_and_taboo.item;

import enderdragon.magic_and_taboo.init.MATItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class PotionSyringeItem extends MagicPotionItem {
    public PotionSyringeItem(Properties props) {
        super(props);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        return consumeStack(stack, entity, MATItems.GLASS_POTION_SYRINGE);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.CROSSBOW;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 16;
    }
}
