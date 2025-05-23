package enderdragon.magic_and_taboo.block.entity;

import enderdragon.magic_and_taboo.block.MagicPerfusionPedestalBlock;
import enderdragon.magic_and_taboo.init.MATBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import static enderdragon.magic_and_taboo.block.MagicPerfusionPedestalBlock.POS_LIST;

public class MagicPerfusionPedestalBlockEntity extends PedestalBlockEntity implements Container {
    protected boolean isStructureValid = false;

    protected BlockPos pos;

    public MagicPerfusionPedestalBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(MATBlockEntities.MAGIC_PERFUSION_PEDESTAL.get(), pPos, pBlockState);
        this.pos = pPos;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, MagicPerfusionPedestalBlockEntity pedestal) {
        ++pedestal.ticks;
        if (pedestal.ticks % 80 == 0) {
            if (MagicPerfusionPedestalBlock.isStructureValid(level, pos)) {
                pedestal.isStructureValid = true;
            }
        }
    }

    public NonNullList<ItemStack> getItems() {
        NonNullList<ItemStack> itemStacks = NonNullList.withSize(POS_LIST.length, ItemStack.EMPTY);
        itemStacks.add(super.getStack());
        if (this.isStructureValid) {
            return MagicPerfusionPedestalBlock.getItemStacks(itemStacks, this.level, pos);
        }
        return itemStacks;
    }

    @Override
    public int getContainerSize() {
        if (this.isStructureValid) {
            return 1 + POS_LIST.length;
        }
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return getItems().isEmpty();
    }

    @Override
    public ItemStack getItem(int pSlot) {
        return getItems().get(pSlot);
    }

    @Override
    public ItemStack removeItem(int pSlot, int pAmount) {
        var itemStack = removeItemNoUpdate(pSlot);
        super.setChanged();
        return itemStack;
    }

    @Override
    public ItemStack removeItemNoUpdate(int pSlot) {
        var itemStack = ItemStack.EMPTY;
        for (int i = 0; i < POS_LIST.length; i++) {
            var blockEntity = level.getBlockEntity(this.pos.offset(POS_LIST[i]));
            if (blockEntity instanceof PedestalBlockEntity pedestal && i == pSlot) {
                itemStack = pedestal.getStack();
                pedestal.remove();
            }
        }
        return itemStack;
    }

    @Override
    public void setItem(int pSlot, ItemStack pStack) {
        for (int i = 0; i < POS_LIST.length; i++) {
            var blockEntity = level.getBlockEntity(this.pos.offset(POS_LIST[i]));
            if (blockEntity instanceof PedestalBlockEntity pedestal && i == pSlot) {
                pedestal.tryPlaceItem(pStack);
                super.setChanged();
            }
        }
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return Container.stillValidBlockEntity(this, pPlayer);
    }

    @Override
    public void clearContent() {
        if (this.isStructureValid) {
            for (var pos : POS_LIST) {
                if (level.getBlockEntity(this.pos.offset(pos)) instanceof PedestalBlockEntity pedestal) {
                    pedestal.remove();
                }
            }
        }
        super.remove();
    }
}
