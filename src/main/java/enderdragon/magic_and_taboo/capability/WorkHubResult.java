package enderdragon.magic_and_taboo.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @see net.minecraftforge.items.ItemStackHandler
 */
public class WorkHubResult implements IItemHandlerModifiable, ICapabilityProvider, INBTSerializable<CompoundTag>, TooltipComponent {
    public final LazyOptional<IItemHandlerModifiable> holder = LazyOptional.of(() -> this);
    private ItemStack stack;

    public WorkHubResult(ItemStack stack) {
        this.stack = stack;
    }

    public ItemStack getStack() {
        return this.stack;
    }

    @Override
    public int getSlots() {
        return 1;
    }

    @Override
    public @NotNull ItemStack getStackInSlot(int slot) {
        return slot == 0 ? this.stack : ItemStack.EMPTY;
    }

    @Override
    public void setStackInSlot(int slot, @NotNull ItemStack stack) {
        if (slot == 0) this.stack = stack;
    }

    @Override
    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        if (stack.isEmpty()) return ItemStack.EMPTY;
        if (!this.isItemValid(slot, stack)) return stack;
        if (slot != 0) throw new IndexOutOfBoundsException();
        ItemStack existing = this.stack;
        int limit = Math.min(this.getSlotLimit(slot), stack.getMaxStackSize());
        if (!existing.isEmpty()) {
            if (!ItemHandlerHelper.canItemStacksStack(stack, existing)) return stack;
            limit -= existing.getCount();
        }
        if (limit <= 0) return stack;
        boolean reachedLimit = stack.getCount() > limit;
        if (!simulate) {
            if (existing.isEmpty()) {
                this.stack = reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, limit) : stack;
            } else {
                existing.grow(reachedLimit ? limit : stack.getCount());
            }
        }
        return reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, stack.getCount() - limit) : ItemStack.EMPTY;
    }

    @Override
    public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (amount == 0) return ItemStack.EMPTY;
        if (this.stack.isEmpty()) return ItemStack.EMPTY;
        if (slot != 0) throw new IndexOutOfBoundsException();
        ItemStack existing = this.stack;
        int toExtract = Math.min(amount, this.getSlotLimit(slot));
        if (existing.getCount() > toExtract) {
            if (!simulate) {
                this.stack = ItemHandlerHelper.copyStackWithSize(existing, existing.getCount() - toExtract);
            }
            return ItemHandlerHelper.copyStackWithSize(existing, toExtract);
        } else if (!simulate) {
            this.stack = ItemStack.EMPTY;
            return existing;
        } else {
            return existing.copy();
        }
    }

    @Override
    public int getSlotLimit(int slot) {
        return 64;
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return true;
    }

    @Override
    public CompoundTag serializeNBT() {
        return this.stack.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.stack = ItemStack.of(nbt);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return ForgeCapabilities.ITEM_HANDLER == cap ? this.holder.cast() : LazyOptional.empty();
    }
}
