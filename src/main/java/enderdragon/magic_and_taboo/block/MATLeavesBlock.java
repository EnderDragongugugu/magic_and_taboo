package enderdragon.magic_and_taboo.block;

import enderdragon.magic_and_taboo.tag.MATEntityTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;

public class MATLeavesBlock extends LeavesBlock {
    public final int burnOdds;
    public final int igniteOdds;

    public MATLeavesBlock(int burnOdds, int igniteOdds, Properties props) {
        super(props);
        this.burnOdds = burnOdds;
        this.igniteOdds = igniteOdds;
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return state.hasProperty(WATERLOGGED) && !state.getValue(WATERLOGGED);
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return state.hasProperty(WATERLOGGED) && state.getValue(WATERLOGGED) ? 0 : this.burnOdds;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return state.hasProperty(WATERLOGGED) && state.getValue(WATERLOGGED) ? 0 : this.igniteOdds;
    }

    @Override
    public boolean isValidSpawn(BlockState state, BlockGetter level, BlockPos pos, SpawnPlacements.Type place, EntityType<?> type) {
        return type.is(MATEntityTags.ARBOREAL);
    }
}
