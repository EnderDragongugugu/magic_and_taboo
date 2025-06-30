package enderdragon.magic_and_taboo.capability;

import enderdragon.magic_and_taboo.init.MATCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerMagicPointImpl implements ICapabilityProvider, IPlayerMagicPoint, INBTSerializable<CompoundTag> {
    public final LazyOptional<IPlayerMagicPoint> holder = LazyOptional.of(() -> this);

    protected int magic = 0;
    private int maxMagic = 100;

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return MATCapabilities.PLAYER_MAGIC_POINT.orEmpty(cap, holder);
    }

    @Override
    public CompoundTag serializeNBT() {
        var root = new CompoundTag();
        root.putInt("max_magic_point", maxMagic);
        root.putInt("magic_point", magic);
        return root;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.maxMagic = nbt.getInt("max_magic_point");
        int amount = nbt.getInt("magic_point");
        this.addMagic(amount);
    }

    @Override
    public int getMagic() {
        return magic;
    }

    @Override
    public int getMaxMagic() {
        return maxMagic;
    }

    @Override
    public void setMagic(int amount) {
        magic = amount;
    }

    @Override
    public void setMaxMagic(int amount) {
        maxMagic = amount;
    }

    @Override
    public void addMagic(int amount) {
        magic = magic + amount < 0 ? 0 : Math.max(magic + amount, maxMagic);
    }

}
