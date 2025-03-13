package enderdragon.magic_and_taboo.item;

import enderdragon.magic_and_taboo.capability.MagicPotion;
import enderdragon.magic_and_taboo.init.MATCapabilities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;

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
            for (var effectInstance : list) {
                pLivingEntity.addEffect(effectInstance);
            }
        });
        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new MagicPotion();
    }
}
