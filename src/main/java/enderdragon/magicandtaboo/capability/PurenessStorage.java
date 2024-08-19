package enderdragon.magicandtaboo.capability;

import enderdragon.magicandtaboo.init.MATCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PurenessStorage implements IPurenessStorage, ICapabilityProvider, INBTSerializable<CompoundTag> {
    public final LazyOptional<IPurenessStorage> holder = LazyOptional.of(() -> this);
    public final int max;
    protected int pureness;
    protected float percent;
    protected EntityType<?> source = null;

    public PurenessStorage(int max) {
        this.max = max;
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
    public void setSource(EntityType<?> source) {
        this.source = source;
    }

    @Override
    public EntityType<?> getSource() {
        return this.source;
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
    public boolean transferForm(IPurenessStorage other) {
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
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        var source = nbt.getString("Source");
        if (source.isEmpty()) return;
        EntityType.byString(source).ifPresent(this::setSource);
        this.setPureness(nbt.getInt("Pureness"));
    }
}
