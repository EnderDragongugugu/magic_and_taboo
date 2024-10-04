package enderdragon.magicandtaboo.inventory;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

public class FederationWorkstationItemHandle implements IItemHandler {

    private final IItemHandler itemHandler;

    public FederationWorkstationItemHandle(IItemHandler itemHandler){
        this.itemHandler = itemHandler;
    }

    @Override
    public int getSlots() {
        return 10;
    }

    @Override
    public @NotNull ItemStack getStackInSlot(int slot) {
        return itemHandler.getStackInSlot(slot);
    }

    @Override
    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        return stack;
    }

    @Override
    public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
        return ItemStack.EMPTY;
    }

    @Override
    public int getSlotLimit(int slot) {
        return itemHandler.getSlotLimit(slot);
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return true;
    }
}
