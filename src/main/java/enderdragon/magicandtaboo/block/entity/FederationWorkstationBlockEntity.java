package enderdragon.magicandtaboo.block.entity;

import enderdragon.magicandtaboo.init.MATBlockEntityTypes;
import enderdragon.magicandtaboo.inventory.FederationWorkstationMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Consumer;

public class FederationWorkstationBlockEntity extends BaseContainerBlockEntity implements Consumer<FriendlyByteBuf> {
    private int time;
    private int timeTotal;

    public FederationWorkstationBlockEntity(BlockPos pos, BlockState state) {
        super(MATBlockEntityTypes.FEDERATION_WORKSTATION.get(), pos, state);
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
    protected Component getDefaultName() {
        return Component.translatable("container.magicandtaboo.federation_workstation");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inventory) {
        return new FederationWorkstationMenu(id, inventory, this);
    }

    @Override
    public int getContainerSize() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getItem(int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {

    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public void clearContent() {

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
                    case 0 -> FederationWorkstationBlockEntity.this.time;
                    case 1 -> FederationWorkstationBlockEntity.this.timeTotal;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> FederationWorkstationBlockEntity.this.time = value;
                    case 1 -> FederationWorkstationBlockEntity.this.timeTotal = value;
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
