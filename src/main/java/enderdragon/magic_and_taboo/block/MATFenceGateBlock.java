package enderdragon.magic_and_taboo.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class MATFenceGateBlock extends FenceGateBlock {
    public final int burnOdds;
    public final int igniteOdds;

    public MATFenceGateBlock(int burnOdds, int igniteOdds, BlockBehaviour.Properties props, WoodType type) {
        super(props, type);
        this.burnOdds = burnOdds;
        this.igniteOdds = igniteOdds;
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return this.burnOdds;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return this.igniteOdds;
    }
}
