package net.magic_and_taboo.client.gui.screen.handler;

import net.magic_and_taboo.MagicAndTabooMod;
import net.magic_and_taboo.client.MagicAndTabooClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class SpyglassSextantUIScreenHandler extends ScreenHandler {
    public Inventory inventory;
    public SpyglassSextantUIScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId,playerInventory, new SimpleInventory(3));
    }
    public SpyglassSextantUIScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory){
//        super( MagicAndTabooClient.SpyglassSextantUIScreenHandler,syncId);
        super( MagicAndTabooMod.SpyglassSextantUIScreenHandler,syncId);
        this.inventory = inventory;
        checkSize(this.inventory,3);
        addSlot(new Slot(this.inventory,0,10,10){
            @Override
            public boolean canInsert(ItemStack stack) {
                return true;
            }
        });
        addSlot(new Slot(this.inventory,1,20,10){
            @Override
            public boolean canInsert(ItemStack stack) {
                return true;
            }
        });
        addSlot(new Slot(this.inventory,2,30,10){
            @Override
            public boolean canInsert(ItemStack stack) {
                return true;
            }
        });

        for(int i = 0;i< 3;++i){
            for (int j = 0;j < 9;++j){
                this.addSlot(new Slot(playerInventory,j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int i = 0;i < 9;++i){
            this.addSlot(new Slot(playerInventory,i, 8 + i * 18, 142));
        }
    }
    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = (Slot)this.slots.get(index);
        if (slot != null && slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            int i = this.inventory.size();
            if (index < i) {
                if (!this.insertItem(itemStack2, i, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.getSlot(1).canInsert(itemStack2) && !this.getSlot(1).hasStack()) {
                if (!this.insertItem(itemStack2, 1, 2, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.getSlot(0).canInsert(itemStack2)) {
                if (!this.insertItem(itemStack2, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (i <= 2 || !this.insertItem(itemStack2, 2, i, false)) {
                int k;
                int j = i;
                int l = k = j + 27;
                int m = l + 9;
                if (index >= l && index < m ? !this.insertItem(itemStack2, j, k, false) : (index >= j && index < k ? !this.insertItem(itemStack2, l, m, false) : !this.insertItem(itemStack2, l, k, false))) {
                    return ItemStack.EMPTY;
                }
                return ItemStack.EMPTY;
            }
            if (itemStack2.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }
        return itemStack;
    }
}
