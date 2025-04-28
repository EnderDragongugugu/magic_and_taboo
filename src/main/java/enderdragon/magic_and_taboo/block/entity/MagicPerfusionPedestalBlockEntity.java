package enderdragon.magic_and_taboo.block.entity;

import enderdragon.magic_and_taboo.init.MATBlockEntities;
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
import net.minecraft.world.level.block.state.BlockState;

public class MagicPerfusionPedestalBlockEntity extends BlockEntity {
    public int tick;
    private NonNullList<ItemStack> stack = NonNullList.withSize(1, ItemStack.EMPTY);

    public MagicPerfusionPedestalBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(MATBlockEntities.MAGIC_PERFUSION_PEDESTAL.get(), pPos, pBlockState);
    }

    public static void tickServer(Level level, BlockPos pos, BlockState state, MagicPerfusionPedestalBlockEntity pedestal) {
        ++pedestal.tick;
    }

    public static void tickClient(Level level, BlockPos pos, BlockState state, MagicPerfusionPedestalBlockEntity pedestal) {
        tickServer(level, pos, state, pedestal);
    }

    public ItemStack getItem() {
        return stack.get(0);
    }

    public boolean pushItem(Player player) {
        var itemStack = player.getMainHandItem();
        var stack = itemStack.copyWithCount(1);
        if (this.getItem().isEmpty()) {
            this.stack.set(0, stack);
            itemStack.shrink(1);
            this.setChanged();
            return true;
        }
        return false;
    }

    public boolean removeItem(Player player) {
        if (!this.getItem().isEmpty()) {
            ContainerUtil.addItem(player, this.getItem());
            this.stack.set(0, ItemStack.EMPTY);
            this.setChanged();
            return true;
        }
        return false;
    }

    @Override
    public CompoundTag getUpdateTag() {
        var tag = new CompoundTag();
        this.saveAdditional(tag);
        return tag;
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
        stack = NonNullList.withSize(1, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, this.stack);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, stack);
    }

}
