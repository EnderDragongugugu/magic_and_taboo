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
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;

public class PedestalBlockEntity extends BlockEntity {
    public static void tick(Level level, BlockPos pos, BlockState state, PedestalBlockEntity pedestal) {
        ++pedestal.ticks;

        // 客户端：发射粒子向中心的 MagicPerfusionPedestalBlockEntity
        if (level.isClientSide) {
            pedestal.spawnTransferParticles(level);
        }
    }

    protected @Nonnull ItemStack stack = ItemStack.EMPTY;
    public int ticks;
    // 视觉消耗状态：true表示物品已被视觉消耗（不渲染），但仍然存在
    protected boolean visuallyConsumed = false;

    public PedestalBlockEntity(BlockPos pos, BlockState state) {
        this(MATBlockEntities.PEDESTAL.get(), pos, state);
    }

    public PedestalBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public ItemStack getStack() {
        return this.stack;
    }

    public boolean isVisuallyConsumed() {
        return this.visuallyConsumed;
    }

    public void setVisuallyConsumed(boolean consumed) {
        if (this.visuallyConsumed != consumed) {
            this.visuallyConsumed = consumed;
            this.setChanged();
        }
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
            // 重置视觉消耗状态
            this.visuallyConsumed = false;
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

    /**
     * 发射传输粒子向中心的 MagicPerfusionPedestalBlockEntity
     */
    protected void spawnTransferParticles(Level level) {
        // 如果物品为空或已被视觉消耗，不发射粒子
        if (this.stack.isEmpty() || this.visuallyConsumed) return;

        // 每 5 tick 发射一次粒子，避免过于密集
        if (this.ticks % 5 != 0) return;

        // 查找周围的 MagicPerfusionPedestalBlockEntity
        MagicPerfusionPedestalBlockEntity centerPedestal = findNearbyMagicPerfusionPedestal(level);
        if (centerPedestal == null) return;

        // 检查中心是否有活跃的配方
        if (centerPedestal.getCurrentRecipe() == null) return;

        // 检查制作进度
        int craftingTime = centerPedestal.getCraftingTime();
        int craftingTimeTotal = centerPedestal.getCraftingTimeTotal();
        if (craftingTimeTotal <= 0 || craftingTime >= craftingTimeTotal) return;

        // 发射粒子
        Vec3 from = Vec3.atCenterOf(this.worldPosition);
        Vec3 to = Vec3.atCenterOf(centerPedestal.getBlockPos());
        Vec3 direction = to.subtract(from).normalize();

        double speed = 0.08 + (craftingTime / (double) craftingTimeTotal) * 0.12;

        double offsetX = (level.getRandom().nextDouble() - 0.5) * 0.3;
        double offsetY = 0.5 + level.getRandom().nextDouble() * 0.5;
        double offsetZ = (level.getRandom().nextDouble() - 0.5) * 0.3;

        double x = from.x + offsetX;
        double y = from.y + offsetY;
        double z = from.z + offsetZ;

        double velX = direction.x * speed + (level.getRandom().nextDouble() - 0.5) * 0.02;
        double velY = 0.02 + level.getRandom().nextDouble() * 0.04;
        double velZ = direction.z * speed + (level.getRandom().nextDouble() - 0.5) * 0.02;

        ((net.minecraft.client.multiplayer.ClientLevel) level).addParticle(
                new net.minecraft.core.particles.ItemParticleOption(net.minecraft.core.particles.ParticleTypes.ITEM, this.stack),
                x, y, z, velX, velY, velZ
        );
    }

    /**
     * 查找附近的 MagicPerfusionPedestalBlockEntity
     * 通过检查周围8个位置来确定当前方块是否是某个 MagicPerfusionPedestal 的一部分
     */
    private MagicPerfusionPedestalBlockEntity findNearbyMagicPerfusionPedestal(Level level) {
        // 检查周围8个可能的位置（使用 MagicPerfusionPedestalBlock.POS_LIST 的反向）
        // 当前方块位置减去 POS_LIST 中的每个偏移量，检查是否是 MagicPerfusionPedestalBlockEntity

        for (var offset : enderdragon.magic_and_taboo.block.MagicPerfusionPedestalBlock.POS_LIST) {
            BlockPos potentialCenter = this.worldPosition.subtract(offset);
            if (level.getBlockEntity(potentialCenter) instanceof MagicPerfusionPedestalBlockEntity pedestal) {
                return pedestal;
            }
        }
        return null;
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
        this.visuallyConsumed = tag.getBoolean("visually_consumed");
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("item", this.stack.save(new CompoundTag()));
        tag.putBoolean("visually_consumed", this.visuallyConsumed);
    }
}
