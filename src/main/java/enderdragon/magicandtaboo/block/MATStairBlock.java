package enderdragon.magicandtaboo.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class MATStairBlock extends StairBlock {
    public final int burnOdds;
    public final int igniteOdds;

    public MATStairBlock(int burnOdds, int igniteOdds, Supplier<BlockState> supplier, Properties props) {
        super(supplier, props);
        this.burnOdds = burnOdds;
        this.igniteOdds = igniteOdds;
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return !state.hasProperty(WATERLOGGED) || !state.getValue(WATERLOGGED);
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return state.hasProperty(WATERLOGGED) && state.getValue(WATERLOGGED) ? 0 : this.burnOdds;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return state.hasProperty(WATERLOGGED) && state.getValue(WATERLOGGED) ? 0 : this.igniteOdds;
    }
}
