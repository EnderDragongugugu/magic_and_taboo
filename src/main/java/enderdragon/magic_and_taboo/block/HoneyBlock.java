package enderdragon.magic_and_taboo.block;

import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.FlowingFluid;

import java.util.function.Supplier;

//TODO: make it sticky
public class HoneyBlock extends LiquidBlock {
    public HoneyBlock(Supplier<? extends FlowingFluid> fluid, Properties props) {
        super(fluid, props);
    }
}
