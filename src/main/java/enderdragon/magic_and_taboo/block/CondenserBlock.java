package enderdragon.magic_and_taboo.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CondenserBlock extends Block {
    protected static final VoxelShape AABB1 = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 15.0D, 11.0D);
    protected static final VoxelShape AABB = Shapes.join(
            AABB1,
            box(6.5D, 15.0D, 6.5D, 9.5D, 16.0D, 9.5D),
            BooleanOp.OR
    );

    public CondenserBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return AABB;
    }
}
