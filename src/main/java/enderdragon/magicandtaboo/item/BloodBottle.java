package enderdragon.magicandtaboo.item;

import enderdragon.magicandtaboo.init.MATItems;
import enderdragon.magicandtaboo.util.ContainerUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class BloodBottle extends Item {
    public static final String TAG_DATA = "blood_bottle_data";
    public static final String TAG_PURENESS = "pureness";
    public static final String TAG_ENTITY_NAME = "entity_name";

    public static final Predicate<ItemStack> IS_VALID = stack -> {
        if (stack.getItem() != MATItems.BLOOD_BOTTLE.get()) return false;
        var tag = stack.getTag();
        if (tag == null) return false;
        var data = tag.getCompound(TAG_DATA);
        return !data.isEmpty() && data.getInt(TAG_PURENESS) > 0;
    };

    public BloodBottle(Properties props) {
        super(props);
    }

    public static void tryLootBlood(Player player, LivingEntity target) {
        var inventory = player.getInventory();
        var bottle = ContainerUtil.findStack(Items.GLASS_BOTTLE, inventory.offhand, inventory.items);
        if (bottle.isEmpty()) return;
        float health = target.getHealth();
        float maxHealth = target.getMaxHealth();
        if (health <= 0.2 * maxHealth) {
            var tag = new CompoundTag();
            var entityData = new CompoundTag();
            var blood = new ItemStack(MATItems.BLOOD_BOTTLE.get());
            entityData.putString(BloodBottle.TAG_ENTITY_NAME, target.getName().getString());
            entityData.putInt(TAG_PURENESS, 2400);
            tag.put(TAG_DATA, entityData);
            blood.setTag(tag);
            target.spawnAtLocation(blood, 0.25F);
            bottle.shrink(1);
        }
    }

    public static int getPureness(ItemStack stack) {
        var tag = stack.getTag();
        return tag == null ? -1 : getPureness(tag);
    }

    public static int getPureness(@NotNull CompoundTag tag) {
        var data = tag.getCompound(TAG_DATA);
        return data.isEmpty() ? -1 : data.getInt(TAG_PURENESS);
    }
    public static String getEntityName(ItemStack stack) {
        var tag = stack.getTag();
        return tag == null ? "" : getEntityName(tag);
    }

    public static String getEntityName(@NotNull CompoundTag tag) {
        var data = tag.getCompound(TAG_DATA);
        return data.isEmpty() ? "" : data.getString(BloodBottle.TAG_ENTITY_NAME);
    }

//    public static boolean tryConsume(Player player) {
//        var blood = MATItems.BLOOD_BOTTLE.get();
//        var inventory = player.getInventory();
//        var list = inventory.offhand;
//        do {
//            for (int i = 0, n = list.size(); i < n; ++i) {
//                var stack = list.get(i);
//                if (stack.getItem() != blood) continue;
//                var tag = stack.getTag();
//                if (tag == null) continue;
//                var data = tag.getCompound(TAG_DATA);
//                if (data.isEmpty()) continue;
//                int pureness = data.getInt(TAG_PURENESS) - 10;
//                if (pureness > 0) {
//                    data.putInt(TAG_PURENESS, pureness);
//                    return true;
//                }
//                stack.shrink(1);
//                var bottle = new ItemStack(Items.GLASS_BOTTLE);
//                if (stack.isEmpty()) {
//                    list.set(i, bottle);
//                    return true;
//                }
//                if (!inventory.add(bottle)) {
//                    player.drop(bottle, false);
//                }
//                return true;
//            }
//            if (list == inventory.items) return false;
//            list = inventory.items;
//        } while (true);
//    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        var data = stack.getTag();
        return data != null && data.contains(TAG_DATA, 10);
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        var tag = stack.getTag();
        if (tag == null) return 13;
        var data = tag.getCompound(TAG_DATA);
        return data.isEmpty() ? 13 : Math.round(data.getInt(TAG_PURENESS) * 0.0054166666F);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return ((this.getBarWidth(stack) + 2) << 20) | 0x0F0000;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltips, TooltipFlag flag) {
        int pureness = BloodBottle.getPureness(stack);
        String entityName = BloodBottle.getEntityName(stack);
        if(entityName.length() > 0){
            tooltips.add(Component.translatable("tooltip.magicandtaboo.blood_bottle_entity_name", "§4"+ entityName + "§f"));
        }
        if (pureness > -1) {
            tooltips.add(Component.translatable("tooltip.magicandtaboo.blood_bottle_pureness", String.format("%.1f%%", pureness / 24.0F)));
        }

    }

    @Override
    public @NotNull Component getName(ItemStack pStack) {
        var tag = pStack.getTag();
        if (tag == null) return super.getName(pStack);
        var data = tag.getCompound(TAG_DATA);
        if (data.isEmpty()) return super.getName(pStack);
        return Component.translatable("item.magicandtaboo.blood_bottle.has_entity", data.getString("entity_name"));
    }

}
