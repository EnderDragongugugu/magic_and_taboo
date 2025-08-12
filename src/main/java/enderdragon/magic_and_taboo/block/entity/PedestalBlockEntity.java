package enderdragon.magic_and_taboo.block.entity;

import enderdragon.magic_and_taboo.init.MATBlockEntities;
import enderdragon.magic_and_taboo.util.BlockEntityUtil;
import enderdragon.magic_and_taboo.util.ContainerUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

public class PedestalBlockEntity extends BlockEntity {
    public static void tick(Level level, BlockPos pos, BlockState state, PedestalBlockEntity pedestal) {
        ++pedestal.ticks;
    }

    protected @Nonnull ItemStack stack = ItemStack.EMPTY;
    public int ticks;

    public PedestalBlockEntity(BlockPos pos, BlockState state) {
        this(MATBlockEntities.PEDESTAL.get(), pos, state);
    }

    public PedestalBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public ItemStack getStack() {
        return this.stack;
    }

    public final void removeStack() {
        this.setStack(ItemStack.EMPTY);
    }

    public void setStack(ItemStack stack) {
        this.stack = stack;
        this.setChanged();
    }

    public boolean tryPlaceItem(ItemStack stack) {
        if (this.stack.isEmpty()) {
            this.setStack(stack.split(1));
            return true;
        }
        return false;
    }

    public boolean tryTakeItem(Player player) {
        if (this.stack.isEmpty()) return false;
        ContainerUtil.addItem(player, this.stack);
        this.removeStack();
        return true;
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void setChanged() {
        super.setChanged();
        BlockEntityUtil.notifyChanged(this);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.stack = ItemStack.of(tag.getCompound("item"));
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("item", this.stack.save(new CompoundTag()));
    }
}
