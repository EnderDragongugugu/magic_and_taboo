package enderdragon.magic_and_taboo.block;

import enderdragon.magic_and_taboo.block.entity.PedestalBlockEntity;
import enderdragon.magic_and_taboo.init.MATBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class GoldGrainedMarblePedestalBlock extends BaseEntityBlock {
    public GoldGrainedMarblePedestalBlock(Properties props) {
        super(props);
    }

    @SuppressWarnings("deprecation")
    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PedestalBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide ? createTickerHelper(type, MATBlockEntities.PEDESTAL.get(), PedestalBlockEntity::tick) : null;
    }

    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.getBlockEntity(pos) instanceof PedestalBlockEntity pedestal) {
            if (level.isClientSide) return InteractionResult.CONSUME;
            if (pedestal.tryPlaceItem(player.getItemInHand(hand)) || player.isShiftKeyDown() && pedestal.tryTakeItem(player))
                return InteractionResult.SUCCESS;
            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }
}
