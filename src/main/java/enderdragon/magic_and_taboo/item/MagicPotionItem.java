package enderdragon.magic_and_taboo.item;

import enderdragon.magic_and_taboo.capability.IMagicPotion;
import enderdragon.magic_and_taboo.capability.MagicPotion;
import enderdragon.magic_and_taboo.init.MATCapabilities;
import enderdragon.magic_and_taboo.init.MATItems;
import enderdragon.magic_and_taboo.util.ContainerUtil;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class MagicPotionItem extends Item {
    public static ItemStack consumeStack(ItemStack stack, LivingEntity entity, Supplier<? extends Item> container) {
        Player player = null;
        if (entity instanceof ServerPlayer $player) {
            player = $player;
            CriteriaTriggers.CONSUME_ITEM.trigger($player, stack);
        } else if (entity instanceof Player) {
            player = (Player) entity;
        }
        IMagicPotion potion = stack.getCapability(MATCapabilities.MAGIC_POTION, null).orElse(IMagicPotion.EMPTY);
        if (!entity.level().isClientSide) {
            if (potion.isFatal()) {
                entity.kill();
            } else {
                for (var effect : potion.getEffectInstances()) {
                    if (effect.getEffect().isInstantenous()) {
                        effect.getEffect().applyInstantenousEffect(player, player, entity, effect.getAmplifier(), 1.0D);
                    } else {
                        entity.addEffect(new MobEffectInstance(effect));
                    }
                }
            }
        }
        if (player != null) {
            player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
                if (stack.isEmpty()) return new ItemStack(container.get());
                ContainerUtil.addItem(player, new ItemStack(container.get()));
            }
        }
        entity.gameEvent(GameEvent.DRINK);
        return stack;
    }

    public MagicPotionItem(Properties props) {
        super(props);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 32;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        return consumeStack(stack, entity, MATItems.GLASS_POTION_BOTTLE);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new MagicPotion(1.0F, 0);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level pLevel, List<Component> tooltips, TooltipFlag flag) {
        PotionUtils.addPotionTooltip(
                stack.getCapability(MATCapabilities.MAGIC_POTION).orElse(IMagicPotion.EMPTY).getEffectInstances(),
                tooltips,
                1.0F
        );
    }
}
