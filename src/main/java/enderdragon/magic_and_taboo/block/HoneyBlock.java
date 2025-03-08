package enderdragon.magic_and_taboo.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.phys.Vec3;

import java.util.function.Supplier;

//TODO: make it sticky
public class HoneyBlock extends LiquidBlock {
    public HoneyBlock(Supplier<? extends FlowingFluid> fluid, Properties props) {
        super(fluid, props);
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if (!(pEntity instanceof LivingEntity) || pEntity.getFeetBlockState().is(this)) {
            pEntity.makeStuckInBlock(pState, new Vec3(0.9F, 1.5D, 0.9F));
        }
    }
}
