package enderdragon.magic_and_taboo.capability;

import enderdragon.magic_and_taboo.init.MATCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerMagicPointImpl implements ICapabilityProvider, IPlayerMagicPoint, INBTSerializable<CompoundTag> {
    public final LazyOptional<IPlayerMagicPoint> holder = LazyOptional.of(() -> this);
    private static final Map<UUID, Integer> TICK = new HashMap<>();

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
        this.magic = nbt.getInt("magic_point");
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

    public static void tick(TickEvent.PlayerTickEvent event) {
        if (event.player.level().isClientSide) return;
        var player = event.player;
        UUID uuid = player.getUUID();
        int i = TICK.getOrDefault(uuid, 0);
        TICK.putIfAbsent(uuid, i + 1);
        if (i >= 20) {
            player.getCapability(MATCapabilities.PLAYER_MAGIC_POINT).ifPresent(magic -> {
                int MP = magic.getMagic();
                if (MP < magic.getMaxMagic()) {
                    magic.addMagic(1);
                }
            });
            TICK.put(uuid, 0);
        }
    }
}
