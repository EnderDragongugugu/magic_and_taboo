package enderdragon.magic_and_taboo.block.entity;

import enderdragon.magic_and_taboo.crafting.WorkHubRecipe;
import enderdragon.magic_and_taboo.init.MATBlockEntities;
import enderdragon.magic_and_taboo.init.MATRecipeTypes;
import enderdragon.magic_and_taboo.inventory.WorkHubMenu;
import enderdragon.magic_and_taboo.tag.MATItemTags;
import enderdragon.magic_and_taboo.util.ContainerUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.item.ItemStack;
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
    private WorkHubRecipe lastRecipe;

    public final DataSlot time = DataSlot.standalone();
    public final DataSlot timeTotal = DataSlot.standalone();
    private NonNullList<ItemStack> stacks = NonNullList.withSize(MAX_SIZE, ItemStack.EMPTY);
    private final RecipeManager.CachedCheck<WorkHubBlockEntity, WorkHubRecipe> checker = RecipeManager.createCheck(MATRecipeTypes.WORK_HUB_RECIPE_TYPE.get());

    public WorkHubBlockEntity(BlockPos pos, BlockState state) {
        super(MATBlockEntities.WORK_HUB.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, WorkHubBlockEntity hub) {
        var recipe = hub.checker.getRecipeFor(hub, level).orElse(null);
        if (recipe != null) {
            var result = recipe.getResultItem(level.registryAccess());
            if (ContainerUtil.canMerge(hub.getStackInSlot(8), result)) {
                hub.timeTotal.set(recipe.workTime());
                int time = hub.time.get() + 1;
                if (time > hub.timeTotal.get()) {
                    hub.addItem(8, result, result.getCount());
                    hub.executeRecipe(recipe);
                    hub.time.set(0);
                    hub.lastRecipe = recipe;
                } else {
                    hub.time.set(time);
                }
            }
        } else {
            hub.time.set(0);
        }
        if (hub.lastRecipe != null) {
            hub.output(hub.lastRecipe);
        }
    }

    //    protected void playParticle(WorkHubRecipe recipe) {
//        if (level != null && !recipe.burner().isEmpty()) {
//        var pos = this.getBlockPos();
//        var facing = this.getBlockState().getValue(HorizontalDirectionalBlock.FACING);
//        level.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.08, pos.getY() + 1.3125, pos.getZ() + 0.85, 0.0, 0.1, 0.0);
//            switch (facing) {
//                case NORTH ->
//                        level.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5, pos.getY() + 1.76, pos.getZ() + 0.5, 0.0, 0.1, 0.0);
//                case SOUTH ->
//                        level.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5, pos.getY() + 1.76, pos.getZ() + 0.5, 0.0, 0.1, 0.0);
//                case EAST ->
//                        level.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5, pos.getY() + 1.76, pos.getZ() + 0.5, 0.0, 0.1, 0.0);
//                case WEST ->
//                        level.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5, pos.getY() + 1.76, pos.getZ() + 0.5, 0.0, 0.1, 0.0);
//            }
//
//        }
//    }
    protected void executeRecipe(WorkHubRecipe recipe) {
        ItemStack temp;
        if (recipe.requireMortar()) {
            temp = this.stacks.get(0);
            temp.setDamageValue(temp.getDamageValue() + 1);
        }
        if (!recipe.burner().isEmpty()) {
            temp = this.stacks.get(1);
            temp.setDamageValue(temp.getDamageValue() + 1);
        }
        for (int i = 2; i <= 7; i++) {
            this.stacks.get(i).shrink(1);
        }
    }

    protected void output(WorkHubRecipe recipe) {
        var oldOutput = getStackInSlot(8);
        var resultItem = recipe.getResultItem(level.registryAccess());
        var container = recipe.container();
        var itemStack = getStackInSlot(9);
        var output = getStackInSlot(10);
        var canOutput = output.getCount() + 1 < output.getMaxStackSize();
        if (container.isEmpty() && !oldOutput.isEmpty() && canOutput) {
            oldOutput.shrink(1);
            addItem(10, resultItem, 1);
        } else if (!itemStack.isEmpty() && !oldOutput.isEmpty() && container.test(itemStack) && canOutput) {
            oldOutput.shrink(1);
            itemStack.shrink(1);
            addItem(10, resultItem, 1);
        }
    }

    protected void addItem(int slot, ItemStack resultItem, int count) {
        var itemStack = getStackInSlot(slot);
        if (itemStack.isEmpty()) {
            setItem(slot, resultItem.copyWithCount(count));
        } else {
            itemStack.grow(1);
        }
    }

    protected void onContentsChanged(int slot) {
    }

    public NonNullList<ItemStack> getStacks() {
        return this.stacks;
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return ContainerHelper.saveAllItems(new CompoundTag(), this.stacks, true);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        stacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        time.set(tag.getInt("time"));
        timeTotal.set(tag.getInt("time_total"));
        ContainerHelper.loadAllItems(tag, stacks);
        if (this.level == null) return;
        var id = ResourceLocation.tryParse(tag.getString("last_recipe"));
        if (id == null) return;
        if (this.level.getRecipeManager().byKey(id).orElse(null) instanceof WorkHubRecipe recipe) {
            this.lastRecipe = recipe;
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, stacks);
        tag.putInt("time", time.get());
        tag.putInt("time_total", timeTotal.get());
        if (this.lastRecipe != null) {
            tag.putString("last_recipe", this.lastRecipe.getId().toString());
        }
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
