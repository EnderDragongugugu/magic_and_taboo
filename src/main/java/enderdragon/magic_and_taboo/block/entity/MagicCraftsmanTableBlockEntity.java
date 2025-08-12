package enderdragon.magic_and_taboo.block.entity;

import enderdragon.magic_and_taboo.init.MATBlockEntities;
import enderdragon.magic_and_taboo.util.BlockEntityUtil;
import enderdragon.magic_and_taboo.util.ContainerUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class MagicCraftsmanTableBlockEntity extends BlockEntity {
    public static void tick(Level level, BlockPos pos, BlockState state, MagicCraftsmanTableBlockEntity table) {
        ++table.ticks;
    }

    private final NonNullList<ItemStack> stacks = NonNullList.withSize(3, ItemStack.EMPTY);
    public int ticks;

    public MagicCraftsmanTableBlockEntity(BlockPos pos, BlockState state) {
        this(MATBlockEntities.CRAFTSMAN_TABLE.get(), pos, state);
    }

    public MagicCraftsmanTableBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }


    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public NonNullList<ItemStack> getStacks() {
        return this.stacks;
    }

    public ItemStack getStack(int slot) {
        return this.stacks.get(slot);
    }

    public void setChanged() {
        super.setChanged();
        BlockEntityUtil.notifyChanged(this);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.stacks.clear();
        ContainerHelper.loadAllItems(tag, this.stacks);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, this.stacks, true);
    }

    public boolean putItem(ItemStack stack) {
        var iterator = this.stacks.listIterator();
        while (iterator.hasNext()) {
            var slot = iterator.next();
            if (slot.isEmpty()) {
                iterator.set(stack.split(1));
                this.setChanged();
                return true;
            }
        }
        return false;
    }

    public boolean takeItem(Player player) {
        var iterator = this.stacks.listIterator(this.stacks.size());
        while (iterator.hasPrevious()) {
            var stack = iterator.previous();
            if (stack.isEmpty()) continue;
            iterator.set(ItemStack.EMPTY);
            ContainerUtil.addItem(player, stack);
            this.setChanged();
            return true;
        }
        return false;
    }
}
