package enderdragon.magic_and_taboo.block;

import enderdragon.magic_and_taboo.block.entity.WorkHubBlockEntity;
import enderdragon.magic_and_taboo.init.MATBlockEntityTypes;
import enderdragon.magic_and_taboo.init.MATBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.minecraft.world.level.block.HorizontalDirectionalBlock.FACING;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED;

public class WorkHubBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    protected static final VoxelShape AABB = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 14.0D, 15.0D);

    public WorkHubBlock(Properties props) {
        super(props);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false)
        );
    }

    protected void saveToItem(ItemStack pStack, BlockEntityType<?> pBlockEntityType, CompoundTag pBlockEntityData) {
        if (pBlockEntityData.isEmpty()) {
            pStack.removeTagKey("BlockEntityTag");
        } else {
            ListTag list = new ListTag();
            if (pBlockEntityData.contains("Items", 9)) {
                ListTag itemList = pBlockEntityData.getList("Items", 10);
                for (int i = 0; i < itemList.size(); i++) {
                    CompoundTag itemStack = itemList.getCompound(i);
                    if (itemStack.getByte("Slot") == 8) list.add(itemStack);
                }
            }
            if (!list.isEmpty()) {
                pBlockEntityData.put("Items", list);
                BlockEntity.addEntityType(pBlockEntityData, pBlockEntityType);
                pStack.addTagElement("BlockEntityTag", pBlockEntityData);
            }
        }
    }

    @Override
    public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (blockEntity instanceof WorkHubBlockEntity hub) {
            if (pPlayer.isCreative() && !pLevel.isClientSide && !hub.isEmpty()) {
                ItemStack itemStack = new ItemStack(MATBlocks.WORK_HUB.get());
                saveToItem(itemStack, hub.getType(), hub.saveWithoutMetadata());
                if (hub.hasCustomName()) {
                    itemStack.setHoverName(hub.getCustomName());
                }
                ItemEntity itemEntity = new ItemEntity(pLevel, (double) pPos.getX() + 0.5D, (double) pPos.getY() + 0.5D, (double) pPos.getZ() + 0.5D, itemStack);
                itemEntity.setDefaultPickUpDelay();
                pLevel.addFreshEntity(itemEntity);
            }
        }
        super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return AABB;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(FACING, WATERLOGGED));
    }

    @Override
    public BlockState rotate(BlockState state, LevelAccessor level, BlockPos pos, Rotation direction) {
        return state.setValue(FACING, direction.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER)
                .setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @SuppressWarnings("deprecation")
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos pos, BlockPos facingPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, facing, facingState, level, pos, facingPos);
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
        return MATBlockEntityTypes.WORK_HUB.get().create(pos, state);
    }

    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (player instanceof ServerPlayer && level.getBlockEntity(pos) instanceof WorkHubBlockEntity hub) {
            NetworkHooks.openScreen((ServerPlayer) player, hub, pos);
        }
        return InteractionResult.SUCCESS;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock())) {
            if (level.getBlockEntity(pos) instanceof Container container) {
                for (int i = 0; i < container.getContainerSize(); i++) {
                    if (i != 8) {
                        ItemStack itemStack = container.getItem(i);
                        Containers.dropItemStack(level, (double) pos.getX(), (double) pos.getY(), (double) pos.getZ(), itemStack);
                    }
                }
                level.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, level, pos, newState, movedByPiston);
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, MATBlockEntityTypes.WORK_HUB.get(), WorkHubBlockEntity::tick);
    }
}
