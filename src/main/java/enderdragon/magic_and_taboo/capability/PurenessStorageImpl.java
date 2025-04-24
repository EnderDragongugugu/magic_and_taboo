package enderdragon.magic_and_taboo.capability;

import enderdragon.magic_and_taboo.init.MATCapabilities;
import enderdragon.magic_and_taboo.init.MATItems;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

import static enderdragon.magic_and_taboo.init.MATCapabilities.PURENESS;

public class PurenessStorageImpl implements PurenessStorage, ICapabilityProvider, INBTSerializable<CompoundTag>, Function<PurenessStorage, ItemStack> {
    private static final Reference2ObjectOpenHashMap<Item, Function<PurenessStorage, ItemStack>> CONTAINERS = new Reference2ObjectOpenHashMap<>();

    public static boolean registerContainer(Item container, Function<PurenessStorage, ItemStack> filler) {
        if (CONTAINERS.containsKey(container)) return false;
        CONTAINERS.put(container, filler);
        return true;
    }

    public final LazyOptional<PurenessStorage> holder = LazyOptional.of(() -> this);
    public final int max;
    protected int pureness;
    protected float percent;
    protected EntityType<?> source = null;
    public final boolean virtual;
    protected @NotNull Item container;

    public PurenessStorageImpl(int max, Item container, boolean virtual) {
        this.max = max;
        this.container = container;
        this.virtual = virtual;
    }

    @Override
    public int getMaxPureness() {
        return this.max;
    }

    @Override
    public int getPureness() {
        return this.pureness;
    }

    @Override
    public void setPureness(int amount) {
        this.pureness = Mth.clamp(amount, 0, this.getMaxPureness());
        if (this.pureness == 0) {
            this.percent = 0;
            this.setSource(null);
        } else {
            this.percent = (float) this.pureness / this.max;
        }
    }

    @Override
    public void setSource(@Nullable EntityType<?> source) {
        this.source = source;
    }

    @Nullable
    @Override
    public EntityType<?> getSource() {
        return this.source;
    }

    @Override
    public ItemStack tryReplaceContainer(Item item) {
        if (this.virtual) {
            var stack = this.container == Items.AIR ? ItemStack.EMPTY : new ItemStack(this.container);
            this.container = item;
            return stack;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack apply(PurenessStorage storage) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack takeFilledContainer() {
        var stack = CONTAINERS.getOrDefault(this.container, this).apply(this);
        if (this.virtual) {
            this.container = Items.AIR;
        }
        return stack;
    }

    @Override
    public String getFormattedPureness() {
        return String.format("%.1f%%", this.getPercent() * 100F);
    }

    @Override
    public float getPercent() {
        return this.percent;
    }

    @Override
    public boolean isValid() {
        return this.source != null;
    }

    @Override
    public boolean transferForm(PurenessStorage other) {
        var pureness = other.getPureness();
        var source = other.getSource();
        if (pureness < 0 || source == null) return false;
        this.setPureness(pureness);
        this.setSource(source);
        other.setPureness(0);//other.setSource(null);
        return true;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return MATCapabilities.PURENESS.orEmpty(cap, this.holder);
    }

    @Override
    public CompoundTag serializeNBT() {
        var nbt = new CompoundTag();
        var source = this.getSource();
        if (source != null) {
            nbt.putString("Source", EntityType.getKey(source).toString());
        }
        nbt.putInt("Pureness", this.getPureness());
        if (this.virtual) {
            ForgeRegistries.ITEMS.getResourceKey(this.container).ifPresent(item ->
                    nbt.putString("Container", item.location().toString())
            );
        }
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        var source = nbt.getString("Source");
        if (source.isEmpty()) return;
        EntityType.byString(source).ifPresent(this::setSource);
        this.setPureness(nbt.getInt("Pureness"));
        if (this.virtual) {
            var container = nbt.getString("Container");
            if (container.isEmpty()) return;
            var item = ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse(container));
            this.container = item == null ? Items.AIR : item;
        }
    }

    static {
        registerContainer(Items.GLASS_BOTTLE, storage -> {
            if (storage.isValid()) {
                var blood = new ItemStack(MATItems.BLOOD_BOTTLE.get());
                if (blood.getCapability(PURENESS).orElse(EMPTY).transferForm(storage)) {
                    return blood;
                }
            }
            return new ItemStack(Items.GLASS_BOTTLE);
        });
    }
}
