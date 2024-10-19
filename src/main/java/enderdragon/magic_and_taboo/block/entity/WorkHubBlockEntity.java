package enderdragon.magic_and_taboo.block.entity;

import enderdragon.magic_and_taboo.init.MATBlockEntityTypes;
import enderdragon.magic_and_taboo.init.MATRecipeTypes;
import enderdragon.magic_and_taboo.inventory.WorkHubMenu;
import enderdragon.magic_and_taboo.tag.MATItemTags;
import enderdragon.magic_and_taboo.util.DataSlotImpl;
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
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.minecraftforge.items.ItemHandlerHelper.canItemStacksStack;
import static net.minecraftforge.items.ItemHandlerHelper.copyStackWithSize;

public class WorkHubBlockEntity extends BaseContainerBlockEntity implements IItemHandlerModifiable {
    public final static int MAX_SIZE = 11;
    private static final Logger LOGGER = LogManager.getLogger();

    public final DataSlot time = new DataSlotImpl();
    public final DataSlot timeTotal = new DataSlotImpl();
    private NonNullList<ItemStack> stacks = NonNullList.withSize(MAX_SIZE, ItemStack.EMPTY);
    private final RecipeManager.CachedCheck<WorkHubBlockEntity, ? extends Recipe<?>> checker = RecipeManager.createCheck(MATRecipeTypes.WORK_HUB_RECIPE_TYPE.get());

    public WorkHubBlockEntity(BlockPos pos, BlockState state) {
        super(MATBlockEntityTypes.WORK_HUB.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, WorkHubBlockEntity hub) {
        var recipe = hub.checker.getRecipeFor(hub, level).orElse(null);
        LOGGER.info("tick hub: {}", recipe == null ? "null" : recipe.getId());
    }

    protected void onContentsChanged(int slot) {
        LOGGER.debug("111");
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        stacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, stacks);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, stacks);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.magic_and_taboo.work_hub");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inventory) {
        return new WorkHubMenu(id, inventory, this);
    }

    @Override
    public boolean isEmpty() {
        return this.stacks.stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public void clearContent() {
        this.stacks.clear();
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        this.stacks.set(slot, stack);
        this.onContentsChanged(slot);
    }

    @Override
    public int getSlots() {
        return this.stacks.size();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return this.stacks.get(slot);
    }


    @Override
    public int getSlotLimit(int slot) {
        return switch (slot) {
            case 0, 1 -> 1;
            default -> this.getMaxStackSize();
        };
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        return switch (slot) {
            case 0 -> stack.is(MATItemTags.MORTARS);
            case 1 -> stack.is(MATItemTags.BLAZE_BURNERS);
            default -> true;
        };
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (stack.isEmpty()) return ItemStack.EMPTY;
        if (!this.isItemValid(slot, stack)) return stack;
        var existing = this.stacks.get(slot);
        int limit = Math.min(getSlotLimit(slot), stack.getMaxStackSize());
        if (!existing.isEmpty()) {
            if (!canItemStacksStack(stack, existing)) return stack;
            limit -= existing.getCount();
        }
        if (limit <= 0) return stack;
        boolean reachedLimit = stack.getCount() > limit;
        if (!simulate) {
            if (existing.isEmpty()) {
                this.stacks.set(slot, reachedLimit ? copyStackWithSize(stack, limit) : stack);
            } else {
                existing.grow(reachedLimit ? limit : stack.getCount());
            }
            this.onContentsChanged(slot);
        }
        return reachedLimit ? copyStackWithSize(stack, stack.getCount() - limit) : ItemStack.EMPTY;
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (amount == 0) return ItemStack.EMPTY;
        var existing = this.stacks.get(slot);
        if (existing.isEmpty()) return ItemStack.EMPTY;
        int toExtract = Math.min(amount, existing.getMaxStackSize());
        if (existing.getCount() <= toExtract) {
            if (simulate) return existing.copy();
            this.stacks.set(slot, ItemStack.EMPTY);
            this.onContentsChanged(slot);
            return existing;
        }
        if (!simulate) {
            existing.setCount(existing.getCount() - toExtract);
            this.onContentsChanged(slot);
        }
        return copyStackWithSize(existing, toExtract);
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        var stack = this.stacks.get(slot);
        if (stack.isEmpty()) return ItemStack.EMPTY;
        this.stacks.set(slot, ItemStack.EMPTY);
        this.onContentsChanged(slot);
        return stack;
    }

    @Override
    public final int getContainerSize() {
        return this.getSlots();
    }

    @Override
    public final ItemStack getItem(int slot) {
        return this.getStackInSlot(slot);
    }

    @Override
    public final void setItem(int slot, ItemStack stack) {
        this.setStackInSlot(slot, stack);
    }

    @Override
    public final ItemStack removeItem(int slot, int amount) {
        return this.extractItem(slot, amount, false);
    }

    @Override
    protected final WorkHubBlockEntity createUnSidedHandler() {
        return this;
    }
}
