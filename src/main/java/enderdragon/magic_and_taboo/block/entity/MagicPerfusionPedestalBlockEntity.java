package enderdragon.magic_and_taboo.block.entity;

import enderdragon.magic_and_taboo.block.MagicPerfusionPedestalBlock;
import enderdragon.magic_and_taboo.init.MATBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class MagicPerfusionPedestalBlockEntity extends PedestalBlockEntity {

    public MagicPerfusionPedestalBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(MATBlockEntities.MAGIC_PERFUSION_PEDESTAL.get(), pPos, pBlockState);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, MagicPerfusionPedestalBlockEntity pedestal) {
        ++pedestal.ticks;
        if (pedestal.ticks % 80 == 0) {
            if (MagicPerfusionPedestalBlock.isStructureValid(level, pos)) {
                System.out.println("结构正常");
            }
        }
    }
}
