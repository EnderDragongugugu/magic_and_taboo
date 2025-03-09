package enderdragon.magic_and_taboo.block;

import enderdragon.magic_and_taboo.block.entity.EnchantedCrucibleBlockEntity;
import enderdragon.magic_and_taboo.init.MATBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.world.level.block.HorizontalDirectionalBlock.FACING;

/**
 * @see net.minecraft.world.level.block.AbstractCauldronBlock
 */
public class EnchantedCrucibleBlock extends BaseEntityBlock {
    private static final VoxelShape INSIDE = box(2.0D, 4.0D, 2.0D, 14.0D, 15.0D, 14.0D);
    private static final VoxelShape AABB = Shapes.join(box(0.0D, 0.0D, 0.0D, 16.0D, 15.0D, 16.0D), INSIDE, BooleanOp.ONLY_FIRST);

    public EnchantedCrucibleBlock(Properties props) {
        super(props);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide
                ? createTickerHelper(type, MATBlockEntities.ENCHANTED_CRUCIBLE.get(), EnchantedCrucibleBlockEntity::tickClient)
                : createTickerHelper(type, MATBlockEntities.ENCHANTED_CRUCIBLE.get(), EnchantedCrucibleBlockEntity::tickCommon);
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return AABB;
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getInteractionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return INSIDE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(FACING));
    }

    @Override
    public BlockState rotate(BlockState state, LevelAccessor level, BlockPos pos, Rotation direction) {
        return state.setValue(FACING, direction.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return Shapes.empty();
    }

    @Override
    @SuppressWarnings("deprecation")
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return MATBlockEntities.ENCHANTED_CRUCIBLE.get().create(pos, state);
    }

    @Override
    @SuppressWarnings("deprecation")
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.getBlockEntity(pos) instanceof EnchantedCrucibleBlockEntity crucible) {
            if (level.isClientSide) return InteractionResult.CONSUME;
            ItemStack stack = player.getItemInHand(hand);
            if (crucible.getFluidStack().isEmpty()) {
                crucible.putFluid(stack, player, hand);
                return InteractionResult.SUCCESS;
            } else if (stack.is(Items.GLASS_BOTTLE)) {
                crucible.test(level, stack, player);
                return InteractionResult.SUCCESS;
            } else if (player.isShiftKeyDown() && crucible.remove(level.registryAccess(), player)) {
                return InteractionResult.SUCCESS;
            } else if (crucible.place(level.registryAccess(), player.getAbilities().instabuild ? stack.copy() : stack, player)) {
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        return new ItemStack(Items.GLASS_BOTTLE);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock())) {
            if (level.getBlockEntity(pos) instanceof EnchantedCrucibleBlockEntity crucible) {
                Containers.dropContents(level, pos, crucible.getStacks());
            }
            super.onRemove(state, level, pos, newState, movedByPiston);
        }
    }
}
