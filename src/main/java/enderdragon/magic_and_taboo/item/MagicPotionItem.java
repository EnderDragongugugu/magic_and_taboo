package enderdragon.magic_and_taboo.item;

import enderdragon.magic_and_taboo.capability.IMagicPotion;
import enderdragon.magic_and_taboo.capability.MagicPotion;
import enderdragon.magic_and_taboo.init.MATCapabilities;
import enderdragon.magic_and_taboo.init.MATItems;
import enderdragon.magic_and_taboo.registry.Element;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class MagicPotionItem extends Item {
    public MagicPotionItem(Properties props) {
        super(props);
    }

    public int getUseDuration(ItemStack stack) {
        return 32;
    }

    public UseAnim getUseAnimation(ItemStack props) {
        return UseAnim.DRINK;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        pStack.getCapability(MATCapabilities.MAGIC_POTION).ifPresent(data -> {
            var list = data.getEffectInstances();
            addEffect(pLevel, list, pLivingEntity);
        });
        test(pLivingEntity, pStack, this, MATItems.GLASS_POTION_BOTTLE.get());
        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new MagicPotion();
    }

    @Override
    public void appendHoverText(ItemStack pStack, @javax.annotation.Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        pStack.getCapability(MATCapabilities.MAGIC_POTION).ifPresent(data -> {
            PotionUtils.addPotionTooltip(data.getEffectInstances().stream().toList(), pTooltip, 1.0F);
        });
    }

    protected static void test(LivingEntity livingEntity, ItemStack itemStack, Item useItem, Item bottle) {
        if (livingEntity instanceof Player player && player instanceof ServerPlayer) {
            player.awardStat(Stats.ITEM_USED.get(useItem));
            if (!player.getAbilities().instabuild) {
                itemStack.shrink(1);
            }

            if (!player.getAbilities().instabuild) {
                player.getInventory().add(new ItemStack(bottle));
            }
        }
    }

    protected static void addEffect(Level level, Collection<MobEffectInstance> list, LivingEntity livingEntity) {
        if (!level.isClientSide) {
            for (var effectInstance : list) {
                livingEntity.addEffect(effectInstance);
            }
        }
    }

    protected static Collection<MobEffectInstance> getEffectInstances(IMagicPotion data, float timeMultiple, int extraLevel) {
        Collection<MobEffectInstance> set = new HashSet<>();
        var elements = data.getElements();
        for (var entry : elements.object2FloatEntrySet()) {
            var element = entry.getKey();
            var effectInstance = getEffectInstance(element, entry.getFloatValue(), timeMultiple, extraLevel);
            set.add(effectInstance);
        }
        return set;
    }

    protected static MobEffectInstance getEffectInstance(Element element, float value, float timeMultiple, int extraLevel) {
        var maxLevel = element.maxLevel();
        var maxTime = element.maxTime();
        var maxConcentration = element.concentration().max();
        var minConcentration = element.concentration().min();
        var f = Mth.clamp(value, minConcentration, maxConcentration);
        int level = (int) Math.ceil(
                (f - minConcentration) / ((maxConcentration - minConcentration) / maxLevel)
        );
        level = Math.max(0, Math.min(level, maxLevel) + extraLevel);
        var normalized = (f - minConcentration) / (maxConcentration - minConcentration);
        var time = 0.0F;
        if (maxConcentration / 2 > value) {
            time = value / maxConcentration * maxTime;
        } else {
            time = Mth.clamp(maxTime * (1.0F - 0.6F * normalized), 30 * 20, maxTime);
        }
        return new MobEffectInstance(element.effect(), (int) (time * timeMultiple), level);
    }
}
