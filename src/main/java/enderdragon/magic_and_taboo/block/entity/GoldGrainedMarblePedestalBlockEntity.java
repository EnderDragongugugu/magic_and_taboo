package enderdragon.magic_and_taboo.block.entity;

import enderdragon.magic_and_taboo.init.MATBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class GoldGrainedMarblePedestalBlockEntity extends AbstractPedestalBlockEntity {
    public GoldGrainedMarblePedestalBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(MATBlockEntities.GOLD_GRAINED_MARBLE_PEDESTAL.get(), pPos, pBlockState);
    }

    public static void tickServer(Level level, BlockPos pos, BlockState state, GoldGrainedMarblePedestalBlockEntity pedestal) {
        ++pedestal.tick;
    }

    public static void tickClient(Level level, BlockPos pos, BlockState state, GoldGrainedMarblePedestalBlockEntity pedestal) {
        tickServer(level, pos, state, pedestal);
    }
}
