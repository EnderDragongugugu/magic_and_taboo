package enderdragon.magic_and_taboo.block;

import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

public class StrippableLogBlock extends MATLogBlock {
    public final RegistryObject<? extends Block> stripped;

    public StrippableLogBlock(RegistryObject<? extends Block> stripped, int burnOdds, int igniteOdds, Properties props) {
        super(burnOdds, igniteOdds, props);
        this.stripped = stripped;
    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction action, boolean simulate) {
        var stack = context.getItemInHand();
        if (action == ToolActions.AXE_STRIP && this.stripped.isPresent() && stack.canPerformAction(action)) {
            return this.stripped.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
        }
        return null;
    }
}
