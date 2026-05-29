package enderdragon.magic_and_taboo.block.entity;

import com.google.common.collect.Iterables;
import enderdragon.magic_and_taboo.crafting.PedestalRecipe;
import enderdragon.magic_and_taboo.init.MATBlockEntities;
import enderdragon.magic_and_taboo.init.MATRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import static enderdragon.magic_and_taboo.block.MagicPerfusionPedestalBlock.*;

public class MagicPerfusionPedestalBlockEntity extends PedestalBlockEntity implements Container {
    protected boolean isValid = false;
    private PedestalRecipe currentRecipe;
    private int craftingTime;
    private int craftingTimeTotal;
    private ResourceLocation currentRecipeId;
    private static final int CHECK_INTERVAL = 80;

    // 视觉消耗进度：0.0 = 未消耗，1.0 = 完全消耗（不渲染）
    private float[] visualConsumptionProgress = new float[POS_LIST.size()];
    // 上次同步视觉消耗状态的时间
    private int lastConsumptionSyncTick = 0;
    private static final int CONSUMPTION_SYNC_INTERVAL = 10;

    public MagicPerfusionPedestalBlockEntity(BlockPos pos, BlockState state) {
        super(MATBlockEntities.MAGIC_PERFUSION_PEDESTAL.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, MagicPerfusionPedestalBlockEntity pedestal) {
        ++pedestal.ticks;

        if (pedestal.ticks % CHECK_INTERVAL == 0) {
            boolean wasValid = pedestal.isValid;
            pedestal.isValid = isStructureValid(level, pos);
            if (wasValid != pedestal.isValid) {
                updateStructureState(level, pos);
            }
            if (!pedestal.isValid) {
                pedestal.resetCrafting();
                return;
            }
        }

        if (!pedestal.isValid) return;

        if (pedestal.currentRecipe == null) {
            var recipe = level.getRecipeManager()
                    .getRecipeFor(MATRecipeTypes.PEDESTAL_RECIPE_TYPE.get(), pedestal, level)
                    .orElse(null);
            if (recipe != null) {
                pedestal.currentRecipe = recipe;
                pedestal.craftingTimeTotal = recipe.time();
                pedestal.craftingTime = 0;
                pedestal.setChanged();
                // 重置视觉消耗进度
                for (int i = 0; i < pedestal.visualConsumptionProgress.length; i++) {
                    pedestal.visualConsumptionProgress[i] = 0;
                }
            }
        } else {
            if (pedestal.ticks % 10 == 0 && !pedestal.currentRecipe.matches(pedestal, level)) {
                pedestal.resetCrafting();
                return;
            }
            // 只在服务器端增加制作时间
            if (!level.isClientSide) {
                pedestal.craftingTime++;
                // 更新视觉消耗进度
                pedestal.updateVisualConsumption(level);

                if (pedestal.craftingTime >= pedestal.craftingTimeTotal) {
                    pedestal.completeRecipe(level);
                } else if (pedestal.ticks % CONSUMPTION_SYNC_INTERVAL == 0) {
                    pedestal.syncVisualConsumptionToClient(level);
                }
            } else {
                // 客户端：生成粒子效果
                pedestal.spawnConsumptionParticles(level);
            }
        }
    }

    private void resetCrafting() {
        // 配方被打断时，销毁已被视觉消耗的物品
        if (level != null && !level.isClientSide) {
            destroyVisuallyConsumedItems();
        }

        currentRecipe = null;
        currentRecipeId = null;
        craftingTime = 0;
        craftingTimeTotal = 0;

        // 重置视觉消耗进度
        for (int i = 0; i < visualConsumptionProgress.length; i++) {
            visualConsumptionProgress[i] = 0;
        }

        // 恢复所有物品的可见性
        if (level != null && !level.isClientSide) {
            for (var pos : POS_LIST) {
                BlockPos pedestalPos = worldPosition.offset(pos);
                if (level.getBlockEntity(pedestalPos) instanceof PedestalBlockEntity pedestal) {
                    if (pedestal.isVisuallyConsumed()) {
                        pedestal.setVisuallyConsumed(false);
                        level.sendBlockUpdated(pedestalPos, level.getBlockState(pedestalPos), level.getBlockState(pedestalPos), 3);
                    }
                }
            }
        }

        setChanged();
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    /**
     * 销毁已被视觉消耗的物品
     */
    private void destroyVisuallyConsumedItems() {
        for (int i = 0; i < POS_LIST.size(); i++) {
            if (visualConsumptionProgress[i] >= 0.8f) {
                BlockPos pedestalPos = worldPosition.offset(POS_LIST.get(i));
                if (level.getBlockEntity(pedestalPos) instanceof PedestalBlockEntity pedestal) {
                    if (!pedestal.getStack().isEmpty()) {
                        pedestal.removeStack();
                        level.sendBlockUpdated(pedestalPos, level.getBlockState(pedestalPos), level.getBlockState(pedestalPos), 3);
                    }
                }
            }
        }
    }

    private void completeRecipe(Level level) {
        if (currentRecipe == null) return;

        // 首先消耗材料（注意：这些物品可能已经被视觉消耗）
        consumeIngredients(level);

        // 然后设置结果物品
        ItemStack result = currentRecipe.output().copy();
        if (!result.isEmpty()) {
            this.stack = result;
        }

        currentRecipe = null;
        currentRecipeId = null;
        craftingTime = 0;
        craftingTimeTotal = 0;

        // 重置视觉消耗进度
        for (int i = 0; i < visualConsumptionProgress.length; i++) {
            visualConsumptionProgress[i] = 0;
        }

        // 重置所有周围基座的视觉消耗状态
        resetAllPedestalsVisualState(level);

        setChanged();

        // 确保客户端能够正确接收到更新
        if (!level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    /**
     * 重置所有周围基座的视觉消耗状态
     */
    private void resetAllPedestalsVisualState(Level level) {
        for (var pos : POS_LIST) {
            BlockPos pedestalPos = worldPosition.offset(pos);
            if (level.getBlockEntity(pedestalPos) instanceof PedestalBlockEntity pedestal) {
                if (pedestal.isVisuallyConsumed()) {
                    pedestal.setVisuallyConsumed(false);
                    if (!level.isClientSide) {
                        level.sendBlockUpdated(pedestalPos, level.getBlockState(pedestalPos), level.getBlockState(pedestalPos), 3);
                    }
                }
            }
        }
    }

    private void consumeIngredients(Level level) {
        // 消耗中心核心物品
        if (!this.stack.isEmpty()) {
            this.stack.shrink(1);
            if (this.stack.getCount() <= 0) {
                this.stack = ItemStack.EMPTY;
            }
        }

        // 消耗周围基座的物品 - 需要直接访问 BlockEntity 来修改并同步
        for (var pos : POS_LIST) {
            BlockPos pedestalPos = worldPosition.offset(pos);
            if (level.getBlockEntity(pedestalPos) instanceof PedestalBlockEntity pedestal) {
                ItemStack pedestalStack = pedestal.getStack();
                if (!pedestalStack.isEmpty()) {
                    pedestalStack.shrink(1);
                    // 检查物品是否已被消耗完
                    if (pedestalStack.getCount() <= 0) {
                        pedestal.removeStack();
                    } else {
                        pedestal.setChanged();
                        // 确保客户端能够正确接收到更新
                        if (!level.isClientSide) {
                            level.sendBlockUpdated(pedestalPos, level.getBlockState(pedestalPos), level.getBlockState(pedestalPos), 3);
                        }
                    }
                }
            }
        }
    }

    public int getCraftingTime() {
        return craftingTime;
    }

    public int getCraftingTimeTotal() {
        return craftingTimeTotal;
    }

    @Nullable
    public PedestalRecipe getCurrentRecipe() {
        return currentRecipe;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.isValid = tag.getBoolean("is_valid");
        this.craftingTime = tag.getInt("crafting_time");
        this.craftingTimeTotal = tag.getInt("crafting_time_total");
        if (tag.contains("recipe_id")) {
            this.currentRecipeId = ResourceLocation.tryParse(tag.getString("recipe_id"));
        } else {
            this.currentRecipeId = null;
        }
        // 配方会在服务器端重新匹配，客户端会在 tick 时从 recipe manager 获取
        // 客户端需要立即清除配方引用，避免使用过期的配方
        if (this.level != null && this.level.isClientSide) {
            this.currentRecipe = null;
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean("is_valid", this.isValid);
        tag.putInt("crafting_time", this.craftingTime);
        tag.putInt("crafting_time_total", this.craftingTimeTotal);
        if (this.currentRecipe != null) {
            tag.putString("recipe_id", this.currentRecipe.getId().toString());
        }
    }

    @Override
    public CompoundTag getUpdateTag() {
        var tag = super.getUpdateTag();
        tag.putBoolean("is_valid", this.isValid);
        tag.putInt("crafting_time", this.craftingTime);
        tag.putInt("crafting_time_total", this.craftingTimeTotal);
        if (this.currentRecipe != null) {
            tag.putString("recipe_id", this.currentRecipe.getId().toString());
        }
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
        this.isValid = tag.getBoolean("is_valid");
        this.craftingTime = tag.getInt("crafting_time");
        this.craftingTimeTotal = tag.getInt("crafting_time_total");
        if (tag.contains("recipe_id")) {
            this.currentRecipeId = ResourceLocation.tryParse(tag.getString("recipe_id"));
        } else {
            this.currentRecipeId = null;
        }
        // 客户端：根据配方ID更新配方引用
        updateRecipeFromId();
        // 确保客户端状态同步：如果 craftingTime 为 0，强制清除配方
        if (this.craftingTime == 0 && this.craftingTimeTotal == 0) {
            this.currentRecipe = null;
            this.currentRecipeId = null;
        }
    }

    /**
     * 更新视觉消耗进度 - 根据配方进度逐渐"消耗"周围物品
     */
    private void updateVisualConsumption(Level level) {
        if (currentRecipe == null || craftingTimeTotal <= 0) return;

        float recipeProgress = (float) craftingTime / craftingTimeTotal;
        var surroundingStacks = getSurroundingStacks(level, worldPosition);

        // 根据配方进度，计算每个物品应该被消耗的程度
        // 假设有 n 个物品，每个物品在进度达到 (i+1)/(n+1) 时完全消耗
        int nonEmptyCount = 0;
        for (var stack : surroundingStacks) {
            if (!stack.isEmpty()) nonEmptyCount++;
        }

        if (nonEmptyCount == 0) return;

        int currentIndex = 0;
        for (int i = 0; i < POS_LIST.size(); i++) {
            var stack = surroundingStacks.get(i);
            if (stack.isEmpty()) {
                visualConsumptionProgress[i] = 0;
                continue;
            }

            // 计算这个物品应该被消耗的进度
            float itemThreshold = (float) (currentIndex + 1) / (nonEmptyCount + 1);
            // 根据配方进度和物品阈值计算目标进度
            float targetProgress = Math.min(1.0f, recipeProgress / itemThreshold);
            // 平滑过渡到目标进度
            visualConsumptionProgress[i] += (targetProgress - visualConsumptionProgress[i]) * 0.1f;

            // 同步到周围的基座
            BlockPos pedestalPos = worldPosition.offset(POS_LIST.get(i));
            if (level.getBlockEntity(pedestalPos) instanceof PedestalBlockEntity pedestal) {
                boolean shouldBeConsumed = visualConsumptionProgress[i] >= 0.8f;
                if (pedestal.isVisuallyConsumed() != shouldBeConsumed) {
                    pedestal.setVisuallyConsumed(shouldBeConsumed);
                }
            }

            currentIndex++;
        }
    }

    /**
     * 生成消耗粒子效果 - 从周围基座向中心发射withCore物品纹理粒子
     */
    private void spawnConsumptionParticles(Level level) {
        if (currentRecipe == null) return;

        // 只在客户端生成粒子
        if (!level.isClientSide) return;

        var surroundingStacks = getSurroundingStacks(level, worldPosition);
        Vec3 center = Vec3.atCenterOf(worldPosition);

        // 使用 withCore 物品（中心物品）的纹理发射所有粒子
        ItemStack particleStack = !this.stack.isEmpty() ? this.stack :
                surroundingStacks.stream().filter(s -> !s.isEmpty()).findFirst().orElse(ItemStack.EMPTY);

        for (int i = 0; i < POS_LIST.size(); i++) {
            var stack = surroundingStacks.get(i);
            if (stack.isEmpty() || visualConsumptionProgress[i] >= 1.0f) continue;

            BlockPos pedestalPos = worldPosition.offset(POS_LIST.get(i));
            if (level.getBlockEntity(pedestalPos) instanceof PedestalBlockEntity pedestal) {
                // 如果这个物品已经被视觉消耗，不发射粒子
                if (pedestal.isVisuallyConsumed()) continue;

                // 根据消耗进度决定粒子数量和速度
                float consumptionProgress = visualConsumptionProgress[i];

                // 始终发射一些粒子，即使进度很小
                int particleCount = 1 + (int) (consumptionProgress * 2);

                Vec3 pedestalCenter = Vec3.atCenterOf(pedestalPos);

                // 计算从基座指向中心的向量（类似末影水晶链接效果）
                Vec3 direction = center.subtract(pedestalCenter).normalize();
                double speed = 0.05 + consumptionProgress * 0.15;

                // 发射粒子
                for (int j = 0; j < particleCount; j++) {
                    // 从基座物品位置附近生成粒子
                    double offsetX = (level.getRandom().nextDouble() - 0.5) * 0.4;
                    double offsetY = 0.3 + level.getRandom().nextDouble() * 0.6;
                    double offsetZ = (level.getRandom().nextDouble() - 0.5) * 0.4;

                    double x = pedestalCenter.x + offsetX;
                    double y = pedestalCenter.y + offsetY;
                    double z = pedestalCenter.z + offsetZ;

                    // 速度指向中心，稍微向上的分量
                    double wobble = (level.getRandom().nextDouble() - 0.5) * 0.02;
                    double velX = direction.x * speed + wobble;
                    double velY = direction.y * speed * 0.3 + 0.01;
                    double velZ = direction.z * speed + wobble;

                    // 使用 withCore 物品纹理
                    ((net.minecraft.client.multiplayer.ClientLevel) level).addParticle(
                            new net.minecraft.core.particles.ItemParticleOption(net.minecraft.core.particles.ParticleTypes.ITEM, particleStack),
                            x, y, z, velX, velY, velZ
                    );
                }
            }
        }

        // 添加从方块中间上升的粒子效果（使用 withCore 物品纹理）
        if (!particleStack.isEmpty()) {
            float recipeProgress = craftingTimeTotal > 0 ? (float) craftingTime / craftingTimeTotal : 0;
            int riseParticleCount = (int) (1 + recipeProgress * 4);
            float riseHeight = 1.0f + recipeProgress * 2.0f;

            for (int j = 0; j < riseParticleCount; j++) {
                // 从方块中间位置生成上升粒子
                double offsetX = (level.getRandom().nextDouble() - 0.5) * 0.3;
                double offsetZ = (level.getRandom().nextDouble() - 0.5) * 0.3;

                double x = center.x + offsetX;
                double y = center.y + 0.5 + level.getRandom().nextDouble() * riseHeight;
                double z = center.z + offsetZ;

                double velX = (level.getRandom().nextDouble() - 0.5) * 0.01;
                double velY = 0.03 + recipeProgress * 0.08;
                double velZ = (level.getRandom().nextDouble() - 0.5) * 0.01;

                ((net.minecraft.client.multiplayer.ClientLevel) level).addParticle(
                        new net.minecraft.core.particles.ItemParticleOption(net.minecraft.core.particles.ParticleTypes.ITEM, particleStack),
                        x, y, z, velX, velY, velZ
                );
            }
        }
    }

    /**
     * 同步视觉消耗状态到客户端
     */
    private void syncVisualConsumptionToClient(Level level) {
        if (level.isClientSide) return;

        for (int i = 0; i < POS_LIST.size(); i++) {
            BlockPos pedestalPos = worldPosition.offset(POS_LIST.get(i));
            if (level.getBlockEntity(pedestalPos) instanceof PedestalBlockEntity pedestal) {
                boolean shouldBeConsumed = visualConsumptionProgress[i] >= 0.8f;
                if (pedestal.isVisuallyConsumed() != shouldBeConsumed) {
                    pedestal.setVisuallyConsumed(shouldBeConsumed);
                    level.sendBlockUpdated(pedestalPos, level.getBlockState(pedestalPos), level.getBlockState(pedestalPos), 3);
                }
            }
        }
    }

    private void updateRecipeFromId() {
        if (this.currentRecipeId == null) {
            this.currentRecipe = null;
        } else if (this.level != null) {
            this.level.getRecipeManager()
                    .byKey(this.currentRecipeId)
                    .ifPresent(recipe -> {
                        if (recipe instanceof PedestalRecipe pedestalRecipe) {
                            this.currentRecipe = pedestalRecipe;
                        }
                    });
        }
    }

    public @Nullable PedestalBlockEntity getPedestal(int slot) {
        if (this.level == null) return null;
        return slot == 0 ? this : getSurroundingPedestal(this.level, this.worldPosition, slot - 1);
    }

    @Override
    public int getContainerSize() {
        return this.isValid ? 1 + POS_LIST.size() : 1;
    }

    @Override
    public boolean isEmpty() {
        return this.getStack().isEmpty() && (!this.isValid || this.level == null || Iterables.all(getSurroundingStacks(this.level, this.worldPosition), ItemStack::isEmpty));
    }

    @Override
    public ItemStack getItem(int slot) {
        var pedestal = this.getPedestal(slot);
        return pedestal == null ? ItemStack.EMPTY : pedestal.getStack();
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        if (amount <= 0) return ItemStack.EMPTY;
        var pedestal = this.getPedestal(slot);
        if (pedestal == null) return ItemStack.EMPTY;
        var stack = pedestal.getStack();
        return stack.isEmpty() ? ItemStack.EMPTY : stack.split(amount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        var pedestal = this.getPedestal(slot);
        if (pedestal == null) return ItemStack.EMPTY;
        var stack = pedestal.getStack();
        pedestal.removeStack();
        return stack;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        var pedestal = this.getPedestal(slot);
        if (pedestal == null) return;
        pedestal.tryPlaceItem(stack);
        this.setChanged();
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public void clearContent() {
        this.removeStack();
        var level = this.level;
        if (level == null) return;
        getSurroundingPedestals(level, this.worldPosition).forEach(PedestalBlockEntity::removeStack);
    }
}
