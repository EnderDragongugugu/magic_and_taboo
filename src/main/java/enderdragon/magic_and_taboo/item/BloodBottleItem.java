package enderdragon.magic_and_taboo.item;

import enderdragon.magic_and_taboo.capability.IPurenessStorage;
import enderdragon.magic_and_taboo.capability.PurenessStorage;
import enderdragon.magic_and_taboo.init.MATItems;
import enderdragon.magic_and_taboo.util.ContainerUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

import static enderdragon.magic_and_taboo.init.MATCapabilities.PURENESS;

public class BloodBottleItem extends Item {
    public static final Predicate<ItemStack> IS_VALID = stack -> {
        if (stack.getItem() != MATItems.BLOOD_BOTTLE.get()) return false;
        var storage = stack.getCapability(PURENESS).orElse(IPurenessStorage.EMPTY);
        return storage.isValid() && storage.getPureness() > 0;
    };

    public BloodBottleItem(Properties props) {
        super(props);
    }

    public static void tryLootBlood(Player player, LivingEntity target) {
        var inventory = player.getInventory();
        var bottle = ContainerUtil.findStack(Items.GLASS_BOTTLE, inventory.offhand, inventory.items);
        if (bottle.isEmpty()) return;
        float health = target.getHealth();
        float maxHealth = target.getMaxHealth();
        if (health <= 0.2 * maxHealth) {
            var blood = new ItemStack(MATItems.BLOOD_BOTTLE.get());
            var storage = blood.getCapability(PURENESS).orElse(IPurenessStorage.EMPTY);
            storage.setSource(target.getType());
            storage.setPureness(2400);
            target.spawnAtLocation(blood, 0.25F);
            bottle.shrink(1);
        }
    }

    public static boolean tryTransferTo(Player player, IPurenessStorage storage) {
        var blood = MATItems.BLOOD_BOTTLE.get();
        var inventory = player.getInventory();
        var list = inventory.offhand;
        do {
            for (ItemStack stack : list) {
                if (stack.getItem() != blood) continue;
                var source = stack.getCapability(PURENESS).orElse(IPurenessStorage.EMPTY);
                if (source.isValid() && source.getPureness() > 0 && storage.transferForm(source)) {
                    stack.shrink(1);
                    var container = storage.tryReplaceContainer(Items.GLASS_BOTTLE);
                    if (!container.isEmpty() && !inventory.add(container)) {
                        player.drop(container, false);
                    }
                    inventory.setChanged();
                    return true;
                }
            }
            if (list == inventory.items) return false;
            list = inventory.items;
        } while (true);
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return stack.getCapability(PURENESS).orElse(IPurenessStorage.EMPTY).isValid();
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return Math.round(stack.getCapability(PURENESS).orElse(IPurenessStorage.EMPTY).getPercent() * 13);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return ((this.getBarWidth(stack) + 2) << 20) | 0x0F0000;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltips, TooltipFlag flag) {
        var storage = stack.getCapability(PURENESS).orElse(IPurenessStorage.EMPTY);
        var source = storage.getSource();
        if (source != null) {
            tooltips.add(Component.translatable(
                    "tooltip.magic_and_taboo.blood_bottle_entity_name",
                    Component.translatable(source.getDescriptionId()).withStyle(ChatFormatting.DARK_RED)
            ));
        }
        tooltips.add(Component.translatable("tooltip.magic_and_taboo.blood_bottle_pureness", storage.getFormattedPureness()));
    }

    @Override
    public Component getName(ItemStack stack) {
        var source = stack.getCapability(PURENESS).orElse(IPurenessStorage.EMPTY).getSource();
        return source == null ? super.getName(stack) : Component.translatable("item.magic_and_taboo.blood_bottle.has_entity", source.getDescription());
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new PurenessStorage(2400, Items.GLASS_BOTTLE, false);
    }
}
