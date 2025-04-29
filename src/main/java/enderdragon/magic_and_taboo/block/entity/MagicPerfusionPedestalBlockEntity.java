package enderdragon.magic_and_taboo.block.entity;

import enderdragon.magic_and_taboo.init.MATBlockEntities;
import enderdragon.magic_and_taboo.util.ContainerUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

public class MagicPerfusionPedestalBlockEntity extends BlockEntity {
    public int tick;
    private @Nonnull ItemStack stack = ItemStack.EMPTY;

    public MagicPerfusionPedestalBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(MATBlockEntities.MAGIC_PERFUSION_PEDESTAL.get(), pPos, pBlockState);
    }

    public static void tickServer(Level level, BlockPos pos, BlockState state, MagicPerfusionPedestalBlockEntity pedestal) {
        ++pedestal.tick;
    }

    public static void tickClient(Level level, BlockPos pos, BlockState state, MagicPerfusionPedestalBlockEntity pedestal) {
        tickServer(level, pos, state, pedestal);
    }

    public ItemStack getStack() {
        return this.stack;
    }

    public boolean tryPlaceItem(ItemStack stack) {
        if (this.stack.isEmpty()) {
            this.stack = stack.split(1);
            this.setChanged();
            return true;
        }
        return false;
    }

    public boolean tryTakeItem(Player player) {
        if (this.stack.isEmpty()) return false;
        ContainerUtil.addItem(player, this.stack);
        this.stack = ItemStack.EMPTY;
        this.setChanged();
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
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
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
