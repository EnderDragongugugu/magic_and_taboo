package enderdragon.magic_and_taboo.block;


import enderdragon.magic_and_taboo.effect.MATEffect;
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

public class MercuryOreBlock extends DropExperienceBlock {
    public MercuryOreBlock(Properties props, IntProvider provider) {
        super(props, provider);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter level, List<Component> tooltips, TooltipFlag flag) {
        tooltips.add(Component.translatable("tooltip.magic_and_taboo.mercury_ore"));
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity entity, ItemStack tool) {
        super.playerDestroy(level, player, pos, state, entity, tool);
        if (level.random.nextFloat() < 0.2F) {
            player.addEffect(new MobEffectInstance(MATEffect.MERCURY_TOXINS.get(), 600, 1));
        }
    }
}
