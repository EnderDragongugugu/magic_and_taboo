package enderdragon.magicandtaboo.item.equipment;

import enderdragon.magicandtaboo.item.BloodBottle;
import enderdragon.magicandtaboo.util.ContainerUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public class SacrificialDagger extends SwordItem {
    public static final String TAG_SACRIFICING = "sacrificing";
    public static final String TAG_BLOOD_BOTTLE = "blood_bottle";
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
        Inventory inventory = player.getInventory();
        var stack = player.getItemInHand(hand);
        if (level.isClientSide) {
            return inventory.hasAnyMatching(BloodBottle.IS_VALID)
                    ? InteractionResultHolder.consume(stack)
                    : InteractionResultHolder.fail(stack);
        }
        var data = stack.getOrCreateTag();
        if (data.getBoolean(TAG_SACRIFICING)) {
            data.putBoolean(TAG_SACRIFICING, false);
            releaseBloodBottle(player, stack);
            return InteractionResultHolder.consume(stack);
        }
        var blood = ContainerUtil.findStack(BloodBottle.IS_VALID, inventory.offhand, inventory.items);
        if (blood != null) {
            data.putBoolean(TAG_SACRIFICING, true);
            bindBloodBottle(stack, blood);
            return InteractionResultHolder.consume(stack);
        }
        return InteractionResultHolder.fail(stack);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        CompoundTag nbt = pStack.getTag();
        if (nbt != null && nbt.contains(TAG_BLOOD_BOTTLE, 10)) {
            int pureness = BloodBottle.getPureness(nbt.getCompound(TAG_BLOOD_BOTTLE).getCompound("tag"));
            if (pureness > -1) {
                pTooltipComponents.add(Component.translatable("tooltip.magicandtaboo.blood_bottle_pureness", String.format("%.1f%%", pureness / 24.0F)));
            } else {
                pTooltipComponents.add(Component.translatable("tooltip.magicandtaboo.sacrificial_dagger_warn"));
            }
        }
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof Player player && target.getMobType() != MobType.UNDEAD) {
            BloodBottle.tryLootBlood(player, target);
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    public static void bindBloodBottle(ItemStack sword, ItemStack bloodBottle) {
        CompoundTag addItemNBT = new CompoundTag();
        CompoundTag swordNBT = sword.getOrCreateTag();
        if (swordNBT.isEmpty() || !swordNBT.contains(TAG_BLOOD_BOTTLE, 10)) {
            swordNBT.put(TAG_BLOOD_BOTTLE, bloodBottle.save(addItemNBT));
            bloodBottle.shrink(1);
        }
    }

    public static void releaseBloodBottle(Player player, ItemStack dagger) {
        var tag = dagger.getTag();
        if (tag == null) return;
        var blood = ItemStack.of(tag.getCompound(TAG_BLOOD_BOTTLE));
        tag.remove(TAG_BLOOD_BOTTLE);
        if (blood.isEmpty()) return;
        ContainerUtil.addItem(player,
                BloodBottle.getPureness(blood) > 0 ? blood : new ItemStack(Items.GLASS_BOTTLE)
        );
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
