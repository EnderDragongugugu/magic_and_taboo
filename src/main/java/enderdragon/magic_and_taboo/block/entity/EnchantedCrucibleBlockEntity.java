package enderdragon.magic_and_taboo.block.entity;

import enderdragon.magic_and_taboo.client.render.EnchantedCrucibleInfo;
import enderdragon.magic_and_taboo.init.MATBlockEntities;
import enderdragon.magic_and_taboo.registry.AlchemyElement;
import enderdragon.magic_and_taboo.registry.Element;
import enderdragon.magic_and_taboo.util.ContainerUtil;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * @see net.minecraftforge.fluids.capability.templates.FluidTank
 * @see net.minecraftforge.fluids.capability.FluidHandlerBlockEntity
 */
public class EnchantedCrucibleBlockEntity extends BlockEntity implements IFluidHandler {
    public static void tickServer(Level level, BlockPos pos, BlockState state, EnchantedCrucibleBlockEntity crucible) {
        cookTick(level, pos, state, crucible);
        heating(level, pos, state, crucible);
    }

    public static void tickClient(Level level, BlockPos pos, BlockState state, EnchantedCrucibleBlockEntity crucible) {
        var info = crucible.getRenderingInfo();
        info.fluid = crucible.getFluidStack();
        info.fluidColor = info.fluid.getFluid().isSame(Fluids.WATER)
                ? level.getBiome(pos).get().getWaterColor()
                : 0xFFFFFF;
        ++info.tick;
    }

    public static final int MAX_SIZE = 8;
    public static final int CAPACITY = 1000;
    private final NonNullList<ItemStack> stacks = NonNullList.withSize(MAX_SIZE, ItemStack.EMPTY);
    private final LazyOptional<IFluidHandler> holder = LazyOptional.of(() -> this);
    private final int[] cookingTime = new int[MAX_SIZE];
    private int temperature = 0;
    protected @NotNull FluidStack fluid = FluidStack.EMPTY;
    /**
     * Should only be used for rendering
     */
    private EnchantedCrucibleInfo info;

    public EnchantedCrucibleBlockEntity(BlockPos pos, BlockState state) {
        super(MATBlockEntities.ENCHANTED_CRUCIBLE.get(), pos, state);
    }

    public EnchantedCrucibleInfo getRenderingInfo() {
        return this.info == null ? this.info = new EnchantedCrucibleInfo() : this.info;
    }

    public NonNullList<ItemStack> getStacks() {
        return stacks;
    }

    public int[] getCookingTime() {
        return cookingTime;
    }

    public int getTemperature() {
        return temperature;
    }

    public static void heating(Level level, BlockPos pos, BlockState state, EnchantedCrucibleBlockEntity crucible) {
        boolean flag = false;
        if (crucible.temperature < 100 && !crucible.fluid.isEmpty() && level.getGameTime() % 10 == 0) {
            flag = true;
            crucible.temperature = Mth.clamp(crucible.temperature + 1, 0, 100);

        }
        if (flag) {
            crucible.setChanged();
        }
    }

    public void test(ItemStack hand, Player player) {
        int amount = this.fluid.getAmount();
        if (amount % 250 == 0) {
            spawnMagicP(hand, player);
            this.fluid.setAmount(amount - 250);
        }
        if (amount <= 250) {
            stacks.clear();
        }
    }

    public void spawnMagicP(ItemStack hand, Player player) {
        if (!hand.is(Items.GLASS_BOTTLE)) return;
        var bottle = new ItemStack(Items.HONEY_BOTTLE);
        Map<Holder<Element>, Float> map = getAllElements(level);
        var tag = new CompoundTag();
        var elements = new CompoundTag();
        map.forEach((elementHolder, aFloat) -> {
            elements.putFloat(elementHolder.get().getName(), aFloat);
        });
        tag.put("elements", elements);
        bottle.setTag(tag);
        ContainerUtil.addItem(player, bottle);
    }

    public Map<Holder<Element>, Float> getItemStackElements(@Nullable AlchemyElement alchemyElement, int index) {
        Object2FloatMap<Holder<Element>> map = new Object2FloatOpenHashMap<>();
        if (alchemyElement == null) return map;
        var t = temperature;
        alchemyElement.elementMap().forEach((elementHolder, aFloat) -> {
            var element = elementHolder.get();
            var range = element.getTemperatureRange();
            if (t >= range.min() && t <= range.max()) {
                map.put(elementHolder, aFloat);
            }
        });
        return map;
    }

    public Map<Holder<Element>, Float> getAllElements(Level level) {
        Object2FloatMap<Holder<Element>> map = new Object2FloatOpenHashMap<>();
        for (int i = 0; i < stacks.size(); ++i) {
            var itemStack = stacks.get(i);
            if (!itemStack.isEmpty()) {
                var alchemyElement = getAlchemyElement(level, itemStack);
                var elementsMap = getItemStackElements(alchemyElement, i);
                elementsMap.forEach((elementHolder, aFloat) ->
                        map.merge(elementHolder, aFloat, Float::sum)
                );
            }
        }
        return map;
    }


    public static void cookTick(Level level, BlockPos pos, BlockState state, EnchantedCrucibleBlockEntity crucible) {
        boolean flag = false;
        for (int i = 0; i < crucible.stacks.size(); ++i) {
            var itemStack = crucible.stacks.get(i);
            var alchemyElement = getAlchemyElement(level, itemStack);
            var time = alchemyElement == null ? 300 : alchemyElement.time();
            if (crucible.cookingTime[i] < time && !itemStack.isEmpty()) {
                flag = true;
                crucible.cookingTime[i] = Mth.clamp(crucible.cookingTime[i] + 1, 0, time);
            }
        }
        if (flag) {
            crucible.setChanged();
        }
    }

    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("Fluid")) {
            this.fluid = FluidStack.loadFluidStackFromNBT(tag.getCompound("Fluid"));
        }
        ContainerHelper.loadAllItems(tag, stacks);
        if (tag.contains("cooking_total_times", 11)) {
            int[] cookingTotalTimes = tag.getIntArray("cooking_total_times");
            System.arraycopy(cookingTotalTimes, 0, this.cookingTime, 0, Math.min(this.cookingTime.length, cookingTotalTimes.length));
        }
        if (tag.contains("temperature", 11)) {
            temperature = tag.getInt("temperature");
        }
    }

    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, stacks, true);
        tag.putIntArray("cooking_total_times", this.cookingTime);
        tag.putInt("temperature", this.temperature);
        tag.put("Fluid", this.fluid.writeToNBT(new CompoundTag()));
    }

    public boolean place(ItemStack stack, Player player) {
        var alchemyElement = getAlchemyElement(level, stack);
        for (int i = 0; i < stacks.size(); ++i) {
            var itemStack = stacks.get(i);
            if (itemStack.isEmpty() && alchemyElement != null) {
                cookingTime[i] = 0;
                this.stacks.set(i, stack.split(1));
                setChanged();
                return true;
            }
        }
        if (player instanceof ServerPlayer serverPlayer && !stack.isEmpty()) {
            serverPlayer.connection.send(new ClientboundSetActionBarTextPacket(Component.translatable("text.warn.place", stack.getHoverName())));
        }
        return false;
    }

    @Nullable
    public static AlchemyElement getAlchemyElement(Level level, ItemStack itemStack) {
        var key = ForgeRegistries.ITEMS.getKey(itemStack.getItem());
        return level.registryAccess().registryOrThrow(AlchemyElement.RESOURCE_KEY).getOptional(key).orElse(null);
    }

    public boolean remove(Player player) {
        if (temperature >= 100) return false;
        for (int i = stacks.size() - 1; i >= 0; i--) {
            var itemStack = stacks.get(i);
            var alchemyElement = getAlchemyElement(level, itemStack);
            var time = alchemyElement == null ? 300 : alchemyElement.time();
            if (!itemStack.isEmpty() && cookingTime[i] < time) {
                ItemStack removed = stacks.get(i);
                stacks.set(i, ItemStack.EMPTY);
                cookingTime[i] = 0;
                setChanged();
                ContainerUtil.addItem(player, removed);
                return true;
            }
        }
        return false;
    }

    public void putFluid(ItemStack itemStack, Player player, InteractionHand hand) {
        boolean result = FluidUtil.interactWithFluidHandler(player, hand, this);
        if (result) {
//            if (!player.isCreative()) {
//                player.setItemInHand(hand, result.getResult());
//            }
        }
        setChanged();
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag);
        return tag;
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

    /**
     * @deprecated Use {@link #getFluidStack()} instead.
     */
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
        return true;
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        if (resource.isEmpty()) return 0;
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
            return fluid.getAmount();
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
