package enderdragon.magicandtaboo.block.entity;

import enderdragon.magicandtaboo.init.MATBlockEntityTypes;
import enderdragon.magicandtaboo.inventory.menu.FederationWorkstationMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FederationWorkstationBlockEntity extends BaseContainerBlockEntity {
    private static final Logger LOGGER = LogManager.getLogger();
    protected final ContainerData data;
    private int time;
    private int timeTotal;

    public FederationWorkstationBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(MATBlockEntityTypes.FederationWorkstationBlockEntity.get(), pPos, pBlockState);
        this.data = getData();
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.magicandtaboo.federation_workstation");
    }

    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return new FederationWorkstationMenu(pContainerId, pInventory, this, data);
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
    public ItemStack getItem(int pSlot) {
        return null;
    }

    @Override
    public ItemStack removeItem(int pSlot, int pAmount) {
        return null;
    }

    @Override
    public ItemStack removeItemNoUpdate(int pSlot) {
        return null;
    }

    @Override
    public void setItem(int pSlot, ItemStack pStack) {

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
}
