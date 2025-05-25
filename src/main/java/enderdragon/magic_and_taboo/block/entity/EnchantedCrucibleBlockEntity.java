package enderdragon.magic_and_taboo.block.entity;

import enderdragon.magic_and_taboo.block.EnchantedCrucibleBlock;
import enderdragon.magic_and_taboo.capability.MagicPotion;
import enderdragon.magic_and_taboo.client.renderer.EnchantedCrucibleInfo;
import enderdragon.magic_and_taboo.init.MATBlockEntities;
import enderdragon.magic_and_taboo.init.MATCapabilities;
import enderdragon.magic_and_taboo.item.GlassMagicPotionBottleItem;
import enderdragon.magic_and_taboo.registry.AlchemyElement;
import enderdragon.magic_and_taboo.registry.Element;
import enderdragon.magic_and_taboo.util.ContainerUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @see net.minecraftforge.fluids.capability.templates.FluidTank
 * @see net.minecraftforge.fluids.capability.FluidHandlerBlockEntity
 */
public class EnchantedCrucibleBlockEntity extends BlockEntity implements IFluidHandler {

    public static void tickCommon(Level level, BlockPos pos, BlockState state, EnchantedCrucibleBlockEntity crucible) {
        ++crucible.tick;
        // cooling start
        if (!state.getValue(EnchantedCrucibleBlock.WORKING)) {
            if (crucible.temperature > 0 && !crucible.fluid.isEmpty() && crucible.tick % 50 == 0) {
                --crucible.temperature;
            }
            return;
        }
        // cooling end
        var registry = level.registryAccess();
        // cooking start
        for (int i = crucible.stacks.size() - 1; i >= 0; --i) {
            var stack = crucible.stacks.get(i);
            if (stack.isEmpty()) continue;
            var alchemyElement = AlchemyElement.fromItem(registry, stack.getItem());
            var time = alchemyElement == null ? 300 : alchemyElement.time();
            if (crucible.cookingTime[i] < time) {
                crucible.cookingTime[i] = Math.max(crucible.cookingTime[i] + 1, 0);
            }
        }
        // cooking end
        // heating start
        if (crucible.temperature < 100 && !crucible.fluid.isEmpty() && crucible.tick % 10 == 0) {
            crucible.temperature = Math.max(crucible.temperature + 1, 0);
        }
        // heating end
    }

    public static void tickClient(Level level, BlockPos pos, BlockState state, EnchantedCrucibleBlockEntity crucible) {
        tickCommon(level, pos, state, crucible);
        var info = crucible.getRenderingInfo();
        info.fluid = crucible.getFluidStack();
        if (info.changed || info.temperature != crucible.temperature) {
            info.tip = null;
            crucible.fillPotion(level.registryAccess(), info);
            info.fluidColor = info.fluid.getFluid().isSame(Fluids.WATER)
                    ? info.elements.isEmpty()
                    ? level.getBiome(pos).value().getWaterColor()
                    : PotionUtils.getColor(info.getEffects())
                    : 0xFFFFFF;
        } else if (!info.fluid.getFluid().isSame(Fluids.WATER)) {
            info.fluidColor = 0xFFFFFF;
        }
        info.temperature = crucible.temperature;
    }

    public static final int MAX_SIZE = 8;
    public static final int CAPACITY = 1000;
    private final NonNullList<ItemStack> stacks = NonNullList.withSize(MAX_SIZE, ItemStack.EMPTY);
    private final LazyOptional<IFluidHandler> holder = LazyOptional.of(() -> this);
    private final int[] cookingTime = new int[MAX_SIZE];
    private int temperature = 0;
    protected @NotNull FluidStack fluid = FluidStack.EMPTY;
    public int tick;
    /// Should only be used for rendering
    private EnchantedCrucibleInfo info;

    public EnchantedCrucibleBlockEntity(BlockPos pos, BlockState state) {
        super(MATBlockEntities.ENCHANTED_CRUCIBLE.get(), pos, state);
    }

    public EnchantedCrucibleInfo getRenderingInfo() {
        return this.info == null ? this.info = new EnchantedCrucibleInfo() : this.info;
    }

    public NonNullList<ItemStack> getStacks() {
        return this.stacks;
    }

    public int[] getCookingTime() {
        return cookingTime;
    }

    public int getTemperature() {
        return temperature;
    }

    public boolean cooling() {
        if (this.temperature < 5) return false;
        this.temperature -= 5;
        this.setChanged();
        return true;
    }

    public void test(Level level, GlassMagicPotionBottleItem stack, Player player) {
        int amount = this.fluid.getAmount();
        if (amount >= 250) {
            var bottle = new ItemStack(stack.getFilled());
            this.fillPotion(level.registryAccess(), bottle.getCapability(MATCapabilities.MAGIC_POTION).orElse(MagicPotion.EMPTY));
            ContainerUtil.addItem(player, bottle);
            this.fluid.setAmount(amount - 250);
            this.setChanged();
        }
        if (amount <= 250) {
            stacks.clear();
        }
    }

    public void fillPotion(RegistryAccess registry, MagicPotion potion) {
        var elements = Element.fromStacks(registry, this.stacks, cookingTime, this.temperature);
        for (var entry : elements.object2FloatEntrySet()) {
            var element = entry.getKey();
            float conflict = 0.0F, bonus = 1.0F;
            for (var inner : element.resistanceElementMap().object2FloatEntrySet()) {
                if (elements.containsKey(inner.getKey().value())) {
                    conflict += inner.getFloatValue();
                }
            }
            for (var inner : element.resistanceElementMap().object2FloatEntrySet()) {
                if (elements.containsKey(inner.getKey().value())) {
                    bonus += inner.getFloatValue();
                }
            }
            entry.setValue((entry.getFloatValue() - conflict) * bonus);
        }
        potion.setContent(this.fluid.getFluid().getFluidType(), elements);
    }

    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("Fluid")) {
            this.fluid = FluidStack.loadFluidStackFromNBT(tag.getCompound("Fluid"));
        }
        if (tag.contains("Items")) {
            this.stacks.clear();
            ContainerHelper.loadAllItems(tag, this.stacks);
            if (this.info != null) {
                this.info.changed = true;
            }
        }
        if (tag.contains("CookingProgress", 11)) {
            int[] progresses = tag.getIntArray("CookingProgress");
            System.arraycopy(progresses, 0, this.cookingTime, 0, Math.min(this.cookingTime.length, progresses.length));
        }
        if (tag.contains("Temperature", Tag.TAG_ANY_NUMERIC)) {
            temperature = tag.getInt("Temperature");
        }
    }

    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, stacks, true);
        tag.putIntArray("CookingProgress", this.cookingTime);
        tag.putInt("Temperature", this.temperature);
        tag.put("Fluid", this.fluid.writeToNBT(new CompoundTag()));
    }

    public boolean place(RegistryAccess registry, ItemStack stack, Player player) {
        if (!stack.isEmpty()) {
            var alchemyElement = AlchemyElement.fromItem(registry, stack.getItem());
            if (alchemyElement != null) {
                var stacks = this.stacks;
                for (int i = 0; i < stacks.size(); ++i) {
                    var content = stacks.get(i);
                    if (content.isEmpty()) {
                        cookingTime[i] = 0;
                        this.stacks.set(i, stack.split(1));
                        this.setChanged();
                        return true;
                    }
                }
            }
            player.displayClientMessage(Component.translatable("text.warn.place", stack.getHoverName()), true);
        }
        return false;
    }

    public boolean remove(RegistryAccess registry, Player player) {
        if (this.level == null || temperature >= 100) return false;
        var stacks = this.stacks;
        for (int i = stacks.size() - 1; i >= 0; --i) {
            var stack = stacks.get(i);
            if (stack.isEmpty()) continue;
            var alchemyElement = AlchemyElement.fromItem(registry, stack.getItem());
            var time = alchemyElement == null ? 300 : alchemyElement.time();
            if (cookingTime[i] < time) {
                stacks.set(i, ItemStack.EMPTY);
                cookingTime[i] = 0;
                ContainerUtil.addItem(player, stack);
                this.setChanged();
                return true;
            }
        }
        return false;
    }

    public boolean putFluid(Player player, InteractionHand hand) {
        if (!FluidUtil.interactWithFluidHandler(player, hand, this)) return false;
        setChanged();
        return true;
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    @Override
    public int getTanks() {
        return 1;
    }

    public @NotNull FluidStack getFluidStack() {
        return this.fluid;
    }

    /// @deprecated Use {@link #getFluidStack()} instead.
    @Override
    @Deprecated
    public @NotNull FluidStack getFluidInTank(int tank) {
        return this.fluid;
    }

    @Override
    public int getTankCapacity(int tank) {
        return CAPACITY;
    }

    @Override
    public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
        return !stack.getFluid().isSame(Fluids.LAVA);
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        if (resource.isEmpty() || !this.isFluidValid(0, resource)) return 0;
        final var fluid = this.fluid;
        if (action.simulate()) {
            return fluid.isEmpty()
                    ? Math.min(CAPACITY, resource.getAmount())
                    : fluid.isFluidEqual(resource)
                    ? Math.min(CAPACITY - fluid.getAmount(), resource.getAmount())
                    : 0;
        }
        if (fluid.isEmpty()) {
            this.fluid = new FluidStack(resource, Math.min(CAPACITY, resource.getAmount()));
            this.setChanged();
            return this.fluid.getAmount();
        }
        if (!fluid.isFluidEqual(resource)) return 0;
        int filled = CAPACITY - fluid.getAmount();
        if (resource.getAmount() < filled) {
            fluid.grow(resource.getAmount());
            filled = resource.getAmount();
        } else {
            fluid.setAmount(CAPACITY);
        }
        if (filled > 0) {
            this.setChanged();
        }
        return filled;
    }

    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        int drained = maxDrain;
        if (this.fluid.getAmount() < drained) {
            drained = this.fluid.getAmount();
        }
        FluidStack stack = new FluidStack(this.fluid, drained);
        if (action.execute() && drained > 0) {
            this.fluid.shrink(drained);
            this.setChanged();
        }
        return stack;
    }

    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
        return resource.isEmpty() || !resource.isFluidEqual(this.fluid)
                ? FluidStack.EMPTY
                : drain(resource.getAmount(), action);
    }

    @Override
    @NotNull
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction facing) {
        return capability == ForgeCapabilities.FLUID_HANDLER
                ? this.holder.cast()
                : super.getCapability(capability, facing);
    }
}
