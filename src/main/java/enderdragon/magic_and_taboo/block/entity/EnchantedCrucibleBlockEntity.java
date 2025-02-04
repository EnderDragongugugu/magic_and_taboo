package enderdragon.magic_and_taboo.block.entity;

import enderdragon.magic_and_taboo.init.MATBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class EnchantedCrucibleBlockEntity extends BlockEntity {
    private final NonNullList<ItemStack> stacks = NonNullList.withSize(8, ItemStack.EMPTY);

    public EnchantedCrucibleBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(MATBlockEntityTypes.ENCHANTED_CRUCIBLE.get(), pPos, pBlockState);
    }

    public NonNullList<ItemStack> getStacks() {
        return stacks;
    }

}
