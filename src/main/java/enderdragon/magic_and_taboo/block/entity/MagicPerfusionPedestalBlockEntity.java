package enderdragon.magic_and_taboo.block.entity;

import enderdragon.magic_and_taboo.init.MATBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class MagicPerfusionPedestalBlockEntity extends AbstractPedestalBlockEntity {

    public MagicPerfusionPedestalBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(MATBlockEntities.MAGIC_PERFUSION_PEDESTAL.get(), pPos, pBlockState);
    }

    public static void tickServer(Level level, BlockPos pos, BlockState state, MagicPerfusionPedestalBlockEntity pedestal) {
        ++pedestal.tick;
    }

    public static void tickClient(Level level, BlockPos pos, BlockState state, MagicPerfusionPedestalBlockEntity pedestal) {
        tickServer(level, pos, state, pedestal);
    }

}
