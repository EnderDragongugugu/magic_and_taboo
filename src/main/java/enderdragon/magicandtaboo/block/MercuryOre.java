package enderdragon.magicandtaboo.block;


import enderdragon.magicandtaboo.effect.MATEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class MercuryOre extends DropExperienceBlock {
    public MercuryOre(Properties pProperties, IntProvider pXpRange) {
        super(pProperties, pXpRange);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(Component.translatable("tooltip.magicandtaboo.mercury_ore"));
    }

    @Override
    public void playerDestroy(Level pLevel, Player pPlayer, BlockPos pPos, BlockState pState, @Nullable BlockEntity pBlockEntity, ItemStack pTool) {
        super.playerDestroy(pLevel, pPlayer, pPos, pState, pBlockEntity, pTool);
        float random = pLevel.random.nextFloat();
        if (random <= 0.2) {
            MobEffectInstance effect = new MobEffectInstance(MATEffect.MERCURY_TOXINS.get(), 30 * 20, 1);
            pPlayer.addEffect(effect);
        }
    }
}
