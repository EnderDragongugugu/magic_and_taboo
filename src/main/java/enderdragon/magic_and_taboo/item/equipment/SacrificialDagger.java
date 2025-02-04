package enderdragon.magic_and_taboo.item.equipment;

import enderdragon.magic_and_taboo.capability.IPurenessStorage;
import enderdragon.magic_and_taboo.capability.PurenessStorage;
import enderdragon.magic_and_taboo.item.BloodBottle;
import enderdragon.magic_and_taboo.util.ContainerUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

import static enderdragon.magic_and_taboo.init.MATCapabilities.PURENESS;

@ParametersAreNonnullByDefault
public class SacrificialDagger extends SwordItem {
    public SacrificialDagger(Tier tier, int damageModifier, float speedModifier, Properties props) {
        super(tier, damageModifier, speedModifier, props);
    }

    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            var stack = player.getMainHandItem();
            if (!(stack.getItem() instanceof SacrificialDagger)) return;
            var target = event.getEntity();
            var storage = stack.getCapability(PURENESS).orElse(IPurenessStorage.EMPTY);
            if (!storage.isValid()) {
                BloodBottle.tryLootBlood(player, target);
            }
        }
    }


    @Override
    public boolean isFoil(ItemStack stack) {
        return stack.isEnchanted() || stack.getCapability(PURENESS).orElse(IPurenessStorage.EMPTY).isValid();
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
        var storage = stack.getCapability(PURENESS).orElse(IPurenessStorage.EMPTY);
        if (storage.isValid()) {
            var blood = storage.takeFilledContainer();
            if (!blood.isEmpty()) {
                ContainerUtil.addItem(player, blood);
                ContainerUtil.forcedSync((ServerPlayer) player, hand, stack);
            }
            return InteractionResultHolder.consume(stack);
        }
        if (BloodBottle.tryTransferTo(player, storage)) {
            ContainerUtil.forcedSync((ServerPlayer) player, hand, stack);
            return InteractionResultHolder.consume(stack);
        }
        var container = storage.tryReplaceContainer(Items.AIR);
        if (!container.isEmpty()) {
            ContainerUtil.addItem(player, container);
            return InteractionResultHolder.consume(stack);
        }
        return InteractionResultHolder.fail(stack);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean isSelected) {
        stack.getCapability(PURENESS).orElse(IPurenessStorage.EMPTY).extractPureness(1);
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
        if (storage.getPureness() > 0) {
            tooltips.add(Component.translatable("tooltip.magic_and_taboo.blood_bottle_entity_name", storage.getFormattedPureness()));
        } else {
            tooltips.add(Component.translatable("tooltip.magic_and_taboo.sacrificial_dagger_warn"));
        }
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        var storage = stack.getCapability(PURENESS).orElse(IPurenessStorage.EMPTY);
        if (storage.isValid()) {
            if (target.getType() == storage.getSource() && storage.getPureness() > 0) {
                target.invulnerableTime = 0;//解除受击后伤害免疫
                target.hurt(attacker.damageSources().magic(), 4);
            }
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new PurenessStorage(2400, Items.AIR, true);
    }
    
}
