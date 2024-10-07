package enderdragon.magicandtaboo.inventory;

import enderdragon.magicandtaboo.block.entity.WorkHubBlockEntity;
import enderdragon.magicandtaboo.init.MATMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ShulkerBoxSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class WorkHubMenu extends AbstractContainerMenu {

    @Nullable
    public static WorkHubMenu formPacket(final int id, final Inventory inventory, final @Nullable FriendlyByteBuf buffer) {
        if (buffer == null) return null;// vanilla
        if (inventory.player.level().getBlockEntity(buffer.readBlockPos()) instanceof WorkHubBlockEntity workstation) {
            try {
                workstation.setTime(buffer.readVarInt());
                workstation.setTotalTime(buffer.readVarInt());
            } catch (Exception ignored) {
                workstation.setTime(0);
                workstation.setTotalTime(0);
            }
            return new WorkHubMenu(id, inventory, workstation);
        }
        return null;
    }

    protected final static void slot(WorkHubMenu menu, WorkHubBlockEntity workstation){
        menu.addSlot(new ShulkerBoxSlot(workstation,  0, 12, 23));
        menu.addSlot(new ShulkerBoxSlot(workstation,  1, 12, 46));
        int slot = 1;
        for (int i = 0 ; i < 2; i++){
            for (int j = 0 ; j < 3; j++){
                slot++;
                menu.addSlot(new ShulkerBoxSlot(workstation,  slot, 38 + i * 18, 17 + 18 * j));
            }
        }
        menu.addSlot(new ShulkerBoxSlot(workstation,  8, 117, 19));
        menu.addSlot(new ShulkerBoxSlot(workstation,  9, 86, 53));
        menu.addSlot(new ShulkerBoxSlot(workstation,  10, 136, 53));

    }

    public final WorkHubBlockEntity workstation;
    //    public final ItemStackHandler inventory;

    protected final Level level;

    public WorkHubMenu(int id, Inventory playerInventory, WorkHubBlockEntity workstation) {
        super(MATMenuTypes.WORK_HUB.get(), id);
        this.workstation = workstation;
        this.level = playerInventory.player.level();
        this.workstation.startOpen(playerInventory.player);

        WorkHubMenu.slot(this, workstation);

        for(int i1 = 0; i1 < 3; ++i1) {
            for(int k1 = 0; k1 < 9; ++k1) {
                this.addSlot(new Slot(playerInventory, k1 + i1 * 9 + 9, 8 + k1 * 18, 84 + i1 * 18));
            }
        }

        for(int j1 = 0; j1 < 9; ++j1) {
            this.addSlot(new Slot(playerInventory, j1, 8 + j1 * 18, 142));
        }
    }

    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (pIndex < this.workstation.getContainerSize()) {
                if (!this.moveItemStackTo(itemstack1, this.workstation.getContainerSize(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, this.workstation.getContainerSize(), false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    public void removed(Player pPlayer) {
        super.removed(pPlayer);
        this.workstation.stopOpen(pPlayer);
    }

    public boolean stillValid(Player pPlayer) {
        return this.workstation.stillValid(pPlayer);
    }
}
