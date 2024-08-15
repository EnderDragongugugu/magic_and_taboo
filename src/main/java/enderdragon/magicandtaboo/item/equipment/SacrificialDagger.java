package enderdragon.magicandtaboo.item.equipment;

import enderdragon.magicandtaboo.client.ClientUtil;
import enderdragon.magicandtaboo.util.ContainerUtil;
import init.MATItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class SacrificialDagger extends SwordItem {
    private static final Logger LOGGER = LogManager.getLogger();

    public SacrificialDagger(Tier tier, int damageModifier, float speedModifier, Properties props) {
        super(tier, damageModifier, speedModifier, props);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        CompoundTag data = stack.getTag();
        if (data != null && data.getBoolean("sacrificial_dagger_using")) {
            return true;
        }
        return false;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        CompoundTag data = itemStack.getOrCreateTag();
        Inventory inventory = pPlayer.getInventory();
        if (this.getBloodBottle(inventory) > -1 && !data.getBoolean("sacrificial_dagger_using")) {
            data.putBoolean("sacrificial_dagger_using",true);
            itemStack.setTag(data);
            pPlayer.setItemInHand(pUsedHand,itemStack);
            return InteractionResultHolder.consume(itemStack);
        }
        if(data.getBoolean("sacrificial_dagger_using")){
            data.putBoolean("sacrificial_dagger_using",false);
            itemStack.setTag(data);
            pPlayer.setItemInHand(pUsedHand,itemStack);
            return InteractionResultHolder.consume(itemStack);
        }
        return InteractionResultHolder.fail(itemStack);

    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof Player player) {
            if (target.getMobType() != MobType.UNDEAD) {
                getBlood(player, target);
            }
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    public void getBlood(Player player, LivingEntity hurtEntity) {
        ItemStack bloodBottle = new ItemStack(MATItems.BLOOD_BOTTLE.get());

        CompoundTag entityData = new CompoundTag();
        CompoundTag nbt = bloodBottle.getOrCreateTag();

        Inventory inventory = player.getInventory();

        int slot = ContainerUtil.find(inventory, Items.GLASS_BOTTLE);
        float health = hurtEntity.getHealth();
        float maxHealth = hurtEntity.getMaxHealth();
        if (slot > -1 && health / maxHealth <= 0.2) {

            ItemStack itemStack = inventory.getItem(slot);

            ContainerUtil.consumeStack(itemStack, itemStack.getCount() - 1);

            entityData.putString("entity_name", "test");
            entityData.putInt("pureness", 2400);
            nbt.put("blood_bottle_data", entityData);
            bloodBottle.setTag(nbt);
            hurtEntity.spawnAtLocation(bloodBottle);
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
    public int getBloodBottle(Inventory inventory) {
        int slot = ContainerUtil.find(inventory, MATItems.BLOOD_BOTTLE.get());
        if (slot > -1) {
            ItemStack bloodBottle = inventory.getItem(slot);
            CompoundTag data = bloodBottle.getTag();
            if (data == null || !data.contains("blood_bottle_data", 10)) return -1;
            CompoundTag bloodBottleData = data.getCompound("blood_bottle_data");
            int pureness = bloodBottleData.getInt("pureness");
            if (pureness <= 0) return -1;
            return slot;
        }
        return -1;
    }
}
