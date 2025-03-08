package enderdragon.magic_and_taboo.block.entity;

import enderdragon.magic_and_taboo.init.MATBlockEntities;
import enderdragon.magic_and_taboo.registry.AlchemyElement;
import enderdragon.magic_and_taboo.registry.Element;
import enderdragon.magic_and_taboo.util.ContainerUtil;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import net.minecraft.core.BlockPos;
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
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class EnchantedCrucibleBlockEntity extends BlockEntity implements IFluidHandler {
    public static final int MAX_SIZE = 8;
    private static final Logger LOGGER = LogManager.getLogger();

    private final NonNullList<ItemStack> stacks = NonNullList.withSize(MAX_SIZE, ItemStack.EMPTY);
    private final int[] cookingTime = new int[MAX_SIZE];
    private int temperature = 0;
    private FluidTank fluids = new FluidTank(1000);

    public EnchantedCrucibleBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(MATBlockEntities.ENCHANTED_CRUCIBLE.get(), pPos, pBlockState);
    }

    public NonNullList<ItemStack> getStacks() {

        return stacks;
    }

    public int[] getCookingTime() {
        return cookingTime;
    }

    public FluidTank getFluids() {
        return fluids;
    }

    public int getTemperature() {
        return temperature;
    }

    public static void heating(Level level, BlockPos pos, BlockState state, EnchantedCrucibleBlockEntity crucible) {
        boolean flag = false;
        if (crucible.temperature < 100 && !crucible.getFluids().isEmpty() && level.getGameTime() % 10 == 0) {
            flag = true;
            crucible.temperature = Mth.clamp(crucible.temperature + 1, 0, 100);

        }
        if (flag) {
            crucible.setChanged();
        }
    }

    public void test(ItemStack hand, Player player) {
        var tank = this.fluids.getFluid();
        var amount = tank.getAmount();
        if (amount % 250 == 0) {
            spawnMagicP(hand, player);
            tank.setAmount(amount - 250);
        }
        if (amount - 250 <= 0) {
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
            if (t >= range.getMin() && t <= range.getMax()) {
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

    public void load(CompoundTag pTag) {
        super.load(pTag);
        if (pTag.contains("Fluids")) {
            fluids.readFromNBT(pTag.getCompound("Fluids"));
        }
        ContainerHelper.loadAllItems(pTag, stacks);
        if (pTag.contains("cooking_total_times", 11)) {
            int[] cookingTotalTimes = pTag.getIntArray("cooking_total_times");
            System.arraycopy(cookingTotalTimes, 0, this.cookingTime, 0, Math.min(this.cookingTime.length, cookingTotalTimes.length));
        }
        if (pTag.contains("temperature", 11)) {
            temperature = pTag.getInt("temperature");
        }
    }

    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        ContainerHelper.saveAllItems(pTag, stacks, true);
        pTag.putIntArray("cooking_total_times", this.cookingTime);
        pTag.putInt("temperature", this.temperature);
        CompoundTag fluidNBT = new CompoundTag();
        fluids.writeToNBT(fluidNBT);
        pTag.put("Fluids", fluidNBT);
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
        boolean result = FluidUtil.interactWithFluidHandler(player, hand, this.getFluids());
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

    public static void tick(Level level, BlockPos pos, BlockState state, EnchantedCrucibleBlockEntity crucible) {
        cookTick(level, pos, state, crucible);
        heating(level, pos, state, crucible);
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

    @Override
    public @NotNull FluidStack getFluidInTank(int tank) {
        return fluids.getFluid();
    }

    @Override
    public int getTankCapacity(int tank) {
        return fluids.getCapacity();
    }

    @Override
    public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
        return true;
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        int amountFilled = fluids.fill(resource, action);
        if (amountFilled > 0 && action == FluidAction.EXECUTE) {
            setChanged();
        }
        return amountFilled;
    }

    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        FluidStack drained = fluids.drain(maxDrain, action);
        if (!drained.isEmpty() && action == FluidAction.EXECUTE) {
            setChanged();
        }
        return drained;
    }

    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
        FluidStack drained = fluids.drain(resource, action);
        if (!drained.isEmpty() && action == FluidAction.EXECUTE) {
            setChanged();
        }
        return drained;
    }
}
