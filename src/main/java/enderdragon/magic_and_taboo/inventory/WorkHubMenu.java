package enderdragon.magic_and_taboo.inventory;

import enderdragon.magic_and_taboo.block.entity.WorkHubBlockEntity;
import enderdragon.magic_and_taboo.init.MATMenuTypes;
import enderdragon.magic_and_taboo.tag.MATItemTags;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.TransientCraftingContainer;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class WorkHubMenu extends AbstractContainerMenu {

    @Nullable
    public static WorkHubMenu formPacket(final int id, final Inventory inventory, final @Nullable FriendlyByteBuf buffer) {
        if (buffer == null) return null;// vanilla
        if (inventory.player.level().getBlockEntity(buffer.readBlockPos()) instanceof WorkHubBlockEntity workHub) {
            return new WorkHubMenu(id, inventory, workHub);
        }
        return null;
    }

    public final WorkHubBlockEntity workHub;
    //    public final ItemStackHandler inventory;

    public WorkHubMenu(int id, Inventory playerInventory, WorkHubBlockEntity workHub) {
        super(MATMenuTypes.WORK_HUB.get(), id);
        this.workHub = workHub;
        workHub.startOpen(playerInventory.player);
        this.addSlot(new TagFilteredSlot(workHub, MATItemTags.MORTARS, 0, 12, 23));
        this.addSlot(new TagFilteredSlot(workHub, MATItemTags.BLAZE_BURNERS, 1, 12, 45));
        for (int i = 0, slot = 1; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                this.addSlot(new Slot(workHub, ++slot, 38 + i * 18, 17 + 18 * j));
            }
        }
        this.addSlot(new CacheItemSlot(workHub, 8, 117, 19));
        this.addSlot(new Slot(workHub, 9, 86, 53));
        this.addSlot(new ResultSlot(playerInventory.player, new TransientCraftingContainer(this, 1, 1), workHub, 10, 136, 53));
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
        this.addDataSlot(workHub.time);
        this.addDataSlot(workHub.timeTotal);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack result = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        //noinspection ConstantValue
        if (slot != null && slot.hasItem()) {
            ItemStack stack = slot.getItem();
            result = stack.copy();
            if (index < this.workHub.getContainerSize()) {
                if (!this.moveItemStackTo(stack, this.workHub.getContainerSize(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(stack, 0, this.workHub.getContainerSize(), false)) {
                return ItemStack.EMPTY;
            }
            if (stack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return result;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.workHub.stopOpen(player);
    }

    @Override
    public boolean stillValid(Player player) {
        return this.workHub.stillValid(player);
    }
}
