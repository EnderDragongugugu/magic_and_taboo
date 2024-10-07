package enderdragon.magicandtaboo.block.entity;

import enderdragon.magicandtaboo.init.MATBlockEntityTypes;
import enderdragon.magicandtaboo.inventory.WorkHubMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Consumer;

public class WorkHubBlockEntity extends BaseContainerBlockEntity implements Consumer<FriendlyByteBuf> {
    private int time;
    private int timeTotal;
    private NonNullList<ItemStack> items = NonNullList.withSize(11, ItemStack.EMPTY);

    public WorkHubBlockEntity(BlockPos pos, BlockState state) {
        super(MATBlockEntityTypes.WORK_HUB.get(), pos, state);
    }

    public int getTime() {
        return this.time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTotalTime() {
        return this.timeTotal;
    }

    public void setTotalTime(int time) {
        this.timeTotal = time;
    }



    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(pTag, this.items);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return super.getUpdateTag();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        ContainerHelper.saveAllItems(pTag, this.items);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.magicandtaboo.work_hub");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inventory) {
        return new WorkHubMenu(id, inventory, this);
    }

    @Override
    public int getContainerSize() {
        return this.items.size();
    }

    @Override
    public boolean isEmpty() {
        return this.getItems().stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public ItemStack getItem(int slot) {
        return this.items.get(slot);
    }

    public NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        return ContainerHelper.removeItem(this.items, slot, amount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(this.items, slot);
    }


    @Override
    public void setItem(int pSlot, ItemStack pStack) {
//        this.unpackLootTable((Player)null);
        this.getItems().set(pSlot, pStack);
        if (pStack.getCount() > this.getMaxStackSize()) {
            pStack.setCount(this.getMaxStackSize());
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public void clearContent() {
        items.clear();
    }

//    public ItemStackHandler getInventory() {
//        return inventory;
//    }

    @Deprecated
    private ContainerData getData() {
        return new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> WorkHubBlockEntity.this.time;
                    case 1 -> WorkHubBlockEntity.this.timeTotal;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> WorkHubBlockEntity.this.time = value;
                    case 1 -> WorkHubBlockEntity.this.timeTotal = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    /**
     * Write data into network packet buffer
     */
    @Override
    public void accept(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(this.worldPosition).writeVarInt(this.time).writeVarInt(this.timeTotal);
    }
}
