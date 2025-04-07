package enderdragon.magic_and_taboo.item;

import enderdragon.magic_and_taboo.block.entity.EnchantedCrucibleBlockEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class EnchantedCrucibleBlockItem extends BlockItem {
    public EnchantedCrucibleBlockItem(Block block, Properties props) {
        super(block, props);
    }

    @Override
    protected boolean placeBlock(BlockPlaceContext context, BlockState state) {
        var level = context.getLevel();
        var pos = context.getClickedPos();
        var fluid = level.getFluidState(pos);
        if (!super.placeBlock(context, state)) return false;
        if (fluid.isSource() && level.getBlockEntity(pos) instanceof EnchantedCrucibleBlockEntity crucible) {
            crucible.fill(new FluidStack(fluid.getType(), FluidType.BUCKET_VOLUME), IFluidHandler.FluidAction.EXECUTE);
        }
        return true;
    }
}
