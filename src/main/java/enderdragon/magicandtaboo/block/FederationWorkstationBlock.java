package enderdragon.magicandtaboo.block;

import enderdragon.magicandtaboo.block.entity.FederationWorkstationBlockEntity;
import enderdragon.magicandtaboo.init.MATBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.FurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.world.level.block.HorizontalDirectionalBlock.FACING;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED;

public class FederationWorkstationBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    protected static final VoxelShape AABB = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 14.0D, 15.0D);

    public FederationWorkstationBlock(Properties props) {
        super(props);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.SOUTH)
                .setValue(WATERLOGGED, false)
        );
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return AABB;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, WATERLOGGED);
    }

    @Override
    public BlockState rotate(BlockState state, LevelAccessor level, BlockPos pos, Rotation direction) {
        return state.setValue(FACING, direction.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        BlockState blockstate = this.defaultBlockState().setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
        return blockstate;
    }

    @Override
    public FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (pState.getValue(WATERLOGGED)) {
            pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }
        return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }


    @Override
    @SuppressWarnings("deprecation")
    public @NotNull VoxelShape getOcclusionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return Shapes.empty();
    }

    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return MATBlockEntityTypes.FederationWorkstationBlockEntity.get().create(pPos, pState);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        BlockEntity blockentity = pLevel.getBlockEntity(pPos);
        if (blockentity instanceof FederationWorkstationBlockEntity) {
            pPlayer.openMenu((MenuProvider) blockentity);
            pPlayer.awardStat(Stats.INTERACT_WITH_FURNACE);
        }
        return InteractionResult.SUCCESS;
    }
}
