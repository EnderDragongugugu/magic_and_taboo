package enderdragon.magicandtaboo.item.equipment;

import enderdragon.magicandtaboo.init.MATItems;
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
        boolean flag = data.getBoolean(TAG_SACRIFICING);
        int slot = ContainerUtil.find(inventory,MATItems.BLOOD_BOTTLE.get());
        if (!flag &&  slot > -1) {
            ItemStack bloodBottle =inventory.getItem(slot);
            if(BloodBottle.IS_VALID.test(bloodBottle)){
                data.putBoolean(TAG_SACRIFICING, true);
                this.addBloodBottle(stack,bloodBottle);
                return InteractionResultHolder.consume(stack);
            }
        }
        if (flag) {
            data.putBoolean(TAG_SACRIFICING, false);
            this.removeBloodBottle(player,stack);
            return InteractionResultHolder.consume(stack);
        }
        return InteractionResultHolder.fail(stack);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        CompoundTag nbt = pStack.getTag();
        if(nbt != null && nbt.contains("blood_bottle_nbt",10)){
            CompoundTag bloodBottleNBT = nbt.getCompound("blood_bottle_nbt").getCompound("tag");
            int pureness = BloodBottle.getPureness(bloodBottleNBT);
            if(pureness > -1){
                String num = pureness / 24.0F + "%";
                pTooltipComponents.add(Component.translatable("tooltip.magicandtaboo.blood_bottle_pureness",num ));
            }else {
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
    public void addBloodBottle(ItemStack sword, ItemStack bloodBottle){
        CompoundTag addItemNBT = new CompoundTag();
        CompoundTag swordNBT = sword.getOrCreateTag();
        if(swordNBT.isEmpty() || !swordNBT.contains("blood_bottle_nbt",10)){
            swordNBT.put("blood_bottle_nbt",bloodBottle.save(addItemNBT));
            sword.setTag(swordNBT);
            bloodBottle.shrink(1);
        }
    }
    public void removeBloodBottle(Player player,ItemStack itemStack){
        CompoundTag nbt = itemStack.getTag();
        if(nbt != null && nbt.contains("blood_bottle_nbt",10)){
            CompoundTag itemStackNBT = nbt.getCompound("blood_bottle_nbt").getCompound("tag");
            ItemStack bloodBottle = new ItemStack(MATItems.BLOOD_BOTTLE.get(),1);
            bloodBottle.setTag(itemStackNBT);
            CompoundTag bloodBottleNBT = bloodBottle.getTag();
            if(bloodBottleNBT != null && bloodBottleNBT.contains(BloodBottle.TAG_DATA,10)){
                CompoundTag data = bloodBottleNBT.getCompound(BloodBottle.TAG_DATA);
                int pureness = data.getInt(BloodBottle.TAG_PURENESS);
                if(pureness > 0){
                    ContainerUtil.addItem(player,bloodBottle);
                    nbt.remove("blood_bottle_nbt");
                    itemStack.setTag(nbt);
                    return;
                }
            }
            ItemStack glassBottle = new ItemStack(Items.GLASS_BOTTLE);
            nbt.remove("blood_bottle_nbt");
            itemStack.setTag(nbt);
            ContainerUtil.addItem(player, glassBottle);
        }
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
