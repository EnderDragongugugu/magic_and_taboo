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
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class BloodBottle extends Item {
    public static final String TAG_DATA = "blood_bottle_data";
    public static final String TAG_PURENESS = "pureness";

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
        return data.isEmpty() ? 13 : Math.round(data.getInt(TAG_PURENESS) * 0.0054166666F);// 13F / 2400F -> 0.0054166666F
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return (this.getBarWidth(stack) + 15) << 20;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltips, TooltipFlag flag) {
        var tag = stack.getTag();
        if (tag == null) return;
        var data = tag.getCompound(TAG_DATA);
        if (data.isEmpty()) return;
        tooltips.add(Component.literal(Integer.toString(data.getInt(TAG_PURENESS))));
    }

    public static void tryLootBlood(Player player, LivingEntity target) {
        var inventory = player.getInventory();
        var bottle = ContainerUtil.findStack(Items.GLASS_BOTTLE, inventory.offhand, inventory.items);
        if (bottle.isEmpty()) return;
        float health = target.getHealth();
        float maxHealth = target.getMaxHealth();
        if (health <= 0.2 * maxHealth) {// -> health / maxHealth <= 0.2
            var tag = new CompoundTag();
            var entityData = new CompoundTag();
            var blood = new ItemStack(MATItems.BLOOD_BOTTLE.get());
            entityData.putString("entity_name", Component.Serializer.toJson(target.getName()));
            entityData.putInt(TAG_PURENESS, 2400);
            tag.put(TAG_DATA, entityData);
            blood.setTag(tag);
            target.spawnAtLocation(blood, 0.25F);
            bottle.shrink(1);
        }
    }

    public static boolean tryConsume(Player player) {
        var blood = MATItems.BLOOD_BOTTLE.get();
        var inventory = player.getInventory();
        var list = inventory.offhand;
        do {
            for (int i = 0, n = list.size(); i < n; ++i) {
                var stack = list.get(i);
                if (stack.getItem() != blood) continue;
                var tag = stack.getTag();
                if (tag == null) continue;
                var data = tag.getCompound(TAG_DATA);
                if (data.isEmpty()) continue;
                int pureness = data.getInt(TAG_PURENESS) - 10;
                if (pureness > 0) {
                    data.putInt(TAG_PURENESS, pureness);
                    return true;
                }
                stack.shrink(1);
                var bottle = new ItemStack(Items.GLASS_BOTTLE);
                if (stack.isEmpty()) {
                    list.set(i, bottle);
                    return true;
                }
                if (!inventory.add(bottle)) {
                    player.drop(bottle, false);
                }
                return true;
            }
            if (list == inventory.items) return false;
            list = inventory.items;
        } while (true);
    }
}
