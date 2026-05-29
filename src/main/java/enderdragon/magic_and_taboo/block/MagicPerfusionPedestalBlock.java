package enderdragon.magic_and_taboo.block;

import com.google.common.collect.ImmutableList;
import enderdragon.magic_and_taboo.block.entity.MagicPerfusionPedestalBlockEntity;
import enderdragon.magic_and_taboo.block.entity.PedestalBlockEntity;
import enderdragon.magic_and_taboo.init.MATBlockEntities;
import enderdragon.magic_and_taboo.init.MATBlocks;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
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
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Stream;

import static net.minecraft.world.level.block.HorizontalDirectionalBlock.FACING;

public class MagicPerfusionPedestalBlock extends BaseEntityBlock {
    public static final ImmutableList<Vec3i> POS_LIST = ImmutableList.of(
            new Vec3i(3, 0, 1),
            new Vec3i(3, 0, -1),
            new Vec3i(-3, 0, 1),
            new Vec3i(-3, 0, -1),
            new Vec3i(1, 0, 3),
            new Vec3i(-1, 0, 3),
            new Vec3i(-1, 0, -3),
            new Vec3i(1, 0, -3)
    );
    public static final BooleanProperty IS_INTACT = BooleanProperty.create("is_intact");

    public static boolean isStructureValid(Level level, BlockPos center) {
        var block = MATBlocks.GOLD_GRAINED_MARBLE_PEDESTAL.get();
        for (var pos : POS_LIST) {
            if (!level.getBlockState(center.offset(pos)).is(block)) {
                return false;
            }
        }
        return true;
    }

    public static @Nullable PedestalBlockEntity getSurroundingPedestal(Level level, BlockPos center, int index) {
        return index >= 0 && index < POS_LIST.size() && level.getBlockEntity(
                center.offset(POS_LIST.get(index))
        ) instanceof PedestalBlockEntity pedestal ? pedestal : null;
    }

    public static List<ItemStack> getSurroundingStacks(Level level, BlockPos center) {
        var stacks = new ObjectArrayList<ItemStack>(POS_LIST.size());
        for (var pos : POS_LIST) {
            stacks.add(level.getBlockEntity(center.offset(pos)) instanceof PedestalBlockEntity pedestal ? pedestal.getStack() : ItemStack.EMPTY);
        }
        return stacks;
    }

    public static Stream<PedestalBlockEntity> getSurroundingPedestals(Level level, BlockPos center) {
        return POS_LIST.stream()
                .map(center::offset)
                .map(level::getBlockEntity)
                .filter(PedestalBlockEntity.class::isInstance)
                .map(PedestalBlockEntity.class::cast);
    }

    public static void updateStructureState(Level level, BlockPos center) {
        boolean intact = isStructureValid(level, center);
        BlockState state = level.getBlockState(center);
        if (state.hasProperty(IS_INTACT) && state.getValue(IS_INTACT) != intact) {
            level.setBlock(center, state.setValue(IS_INTACT, intact), 3);
        }
    }


    public MagicPerfusionPedestalBlock(Properties props) {
        super(props);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(IS_INTACT, false)
        );
    }

    @SuppressWarnings("deprecation")
    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(FACING).add(IS_INTACT));
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

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MagicPerfusionPedestalBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, MATBlockEntities.MAGIC_PERFUSION_PEDESTAL.get(), MagicPerfusionPedestalBlockEntity::tick);
    }

    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.getBlockEntity(pos) instanceof MagicPerfusionPedestalBlockEntity pedestal) {
            if (level.isClientSide) return InteractionResult.CONSUME;
            if (pedestal.tryPlaceItem(player.getItemInHand(hand)) || player.isShiftKeyDown() && pedestal.tryTakeItem(player))
                return InteractionResult.SUCCESS;
            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }
}
