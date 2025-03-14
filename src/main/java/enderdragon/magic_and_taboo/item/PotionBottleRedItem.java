package enderdragon.magic_and_taboo.item;

import enderdragon.magic_and_taboo.init.MATCapabilities;
import enderdragon.magic_and_taboo.init.MATItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;

import java.util.List;

public class PotionBottleRedItem extends MagicPotionItem {
    public PotionBottleRedItem(Properties props) {
        super(props);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        pStack.getCapability(MATCapabilities.MAGIC_POTION).ifPresent(data -> {
            MagicPotionItem.addEffect(pLevel, MagicPotionItem.getEffectInstances(data, 2.0F, -1), pLivingEntity);
        });
        test(pLivingEntity, pStack, this, MATItems.GLASS_POTION_BOTTLE_RED.get());
        return this.isEdible() ? pLivingEntity.eat(pLevel, pStack) : pStack;

    }

    @Override
    public void appendHoverText(ItemStack pStack, @javax.annotation.Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        pStack.getCapability(MATCapabilities.MAGIC_POTION).ifPresent(data -> {
            PotionUtils.addPotionTooltip(getEffectInstances(data, 2.0F, -1).stream().toList(), pTooltip, 1.0F);
        });
    }

}
