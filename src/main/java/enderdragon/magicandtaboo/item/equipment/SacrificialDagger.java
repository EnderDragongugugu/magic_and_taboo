package enderdragon.magicandtaboo.item.equipment;

import enderdragon.magicandtaboo.item.BloodBottle;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class SacrificialDagger extends SwordItem {
    public static final String TAG_SACRIFICING = "sacrificing";
    private static final Logger LOGGER = LogManager.getLogger();

    public SacrificialDagger(Tier tier, int damageModifier, float speedModifier, Properties props) {
        super(tier, damageModifier, speedModifier, props);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        if (stack.isEnchanted()) return true;
        var data = stack.getTag();
        return data != null && data.getBoolean(TAG_SACRIFICING);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        var stack = player.getItemInHand(hand);
        if (level.isClientSide) {
            return player.getInventory().hasAnyMatching(BloodBottle.IS_VALID)
                    ? InteractionResultHolder.consume(stack)
                    : InteractionResultHolder.fail(stack);
        }
        var data = stack.getOrCreateTag();
        boolean flag = data.getBoolean(TAG_SACRIFICING);
        if (!flag && BloodBottle.tryConsume(player)) {
            data.putBoolean(TAG_SACRIFICING, true);
            return InteractionResultHolder.consume(stack);
        }
        if (flag) {
            data.putBoolean(TAG_SACRIFICING, false);
            return InteractionResultHolder.consume(stack);
        }
        return InteractionResultHolder.fail(stack);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof Player player && target.getMobType() != MobType.UNDEAD) {
            BloodBottle.tryLootBlood(player, target);
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    //    public void getSoul(Player player , ItemStack itemStack) {
//        Inventory inventory = player.getInventory();
//        int slot = getBloodBottle(inventory);
//        if(slot > -1){
//            ItemStack bloodBottle = inventory.getItem(slot);
//            CompoundTag data = bloodBottle.getTag();
//            CompoundTag bloodBottleData = data.getCompound("blood_bottle_data");
//            int pureness =bloodBottleData.getInt("pureness");
//        }
//    }
}
