package enderdragon.magic_and_taboo.block.entity;

import com.google.common.collect.Iterables;
import enderdragon.magic_and_taboo.init.MATBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import static enderdragon.magic_and_taboo.block.MagicPerfusionPedestalBlock.*;

public class MagicPerfusionPedestalBlockEntity extends PedestalBlockEntity implements Container {
    protected boolean isValid = false;

    public MagicPerfusionPedestalBlockEntity(BlockPos pos, BlockState state) {
        super(MATBlockEntities.MAGIC_PERFUSION_PEDESTAL.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, MagicPerfusionPedestalBlockEntity pedestal) {
        ++pedestal.ticks;
        if (pedestal.ticks % 80 == 0) {
            pedestal.isValid = isStructureValid(level, pos);
        }
    }

    public @Nullable PedestalBlockEntity getPedestal(int slot) {
        if (this.level == null) return null;
        return slot == 0 ? this : getSurroundingPedestal(this.level, this.worldPosition, slot - 1);
    }

    @Override
    public int getContainerSize() {
        return this.isValid ? 1 + POS_LIST.size() : 1;
    }

    @Override
    public boolean isEmpty() {
        return this.getStack().isEmpty() && (!this.isValid || this.level == null || Iterables.all(getSurroundingStacks(this.level, this.worldPosition), ItemStack::isEmpty));
    }

    @Override
    public ItemStack getItem(int slot) {
        var pedestal = this.getPedestal(slot);
        return pedestal == null ? ItemStack.EMPTY : pedestal.getStack();
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        if (amount <= 0) return ItemStack.EMPTY;
        var pedestal = this.getPedestal(slot);
        if (pedestal == null) return ItemStack.EMPTY;
        var stack = pedestal.getStack();
        return stack.isEmpty() ? ItemStack.EMPTY : stack.split(amount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        var pedestal = this.getPedestal(slot);
        if (pedestal == null) return ItemStack.EMPTY;
        var stack = pedestal.getStack();
        pedestal.removeStack();
        return stack;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        var pedestal = this.getPedestal(slot);
        if (pedestal == null) return;
        pedestal.tryPlaceItem(stack);
        this.setChanged();
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public void clearContent() {
        this.removeStack();
        var level = this.level;
        if (level == null) return;
        getSurroundingPedestals(level, this.worldPosition).forEach(PedestalBlockEntity::removeStack);
    }
}
