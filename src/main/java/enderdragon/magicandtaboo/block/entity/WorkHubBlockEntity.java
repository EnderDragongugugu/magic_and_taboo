package enderdragon.magicandtaboo.block.entity;

import enderdragon.magicandtaboo.init.MATBlockEntityTypes;
import enderdragon.magicandtaboo.inventory.WorkHubItemHandler;
import enderdragon.magicandtaboo.inventory.WorkHubMenu;
import enderdragon.magicandtaboo.util.DataSlotImpl;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorkHubBlockEntity extends BaseContainerBlockEntity {
    private static final Logger LOGGER = LogManager.getLogger();
    private static ItemStackHandler inventory;
    private static boolean inventoryChange;

    public final DataSlot time = new DataSlotImpl();
    public final DataSlot timeTotal = new DataSlotImpl();
    private NonNullList<ItemStack> items = NonNullList.withSize(11, ItemStack.EMPTY);


    public WorkHubBlockEntity(BlockPos pos, BlockState state) {
        super(MATBlockEntityTypes.WORK_HUB.get(), pos, state);
        inventory = getInventory();
        inventoryChange = true;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, WorkHubBlockEntity blockEntity) {
//        if (hasItemStack()) {
////            Optional<WorkHubRecipeType> recipe = new ;
//        }
    }

    private static boolean hasItemStack() {
        for (int i = 2; i <= 7; i++) {
            if (!inventory.getStackInSlot(i).isEmpty()) return true;
        }
        return false;
    }

    private ItemStackHandler getInventory() {
        return new ItemStackHandler(WorkHubItemHandler.MAX_SLOT) {
            @Override
            protected void onContentsChanged(int slot) {
                WorkHubBlockEntity.super.setChanged();
                inventoryChange = true;
                LOGGER.debug(111);
            }
        };
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, items);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, items);
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
        return items.size();
    }

    @Override
    public boolean isEmpty() {
        return this.getItems().stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public ItemStack getItem(int slot) {
        return items.get(slot);
    }

    public NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        return ContainerHelper.removeItem(items, slot, amount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(items, slot);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        this.getItems().set(slot, stack);
        if (stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
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
}
