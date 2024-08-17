package enderdragon.magicandtaboo.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED;

public class FlammableBlock extends Block {
    public final int burnOdds;
    public final int igniteOdds;

    public FlammableBlock(int burnOdds, int igniteOdds, Properties props) {
        super(props);
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
