package enderdragon.magic_and_taboo.capability;

import enderdragon.magic_and_taboo.init.MATCapabilities;
import enderdragon.magic_and_taboo.network.NetworkHandler;
import enderdragon.magic_and_taboo.network.s2c.InitMagicPointPayload;
import enderdragon.magic_and_taboo.network.s2c.SyncMagicPointPayload;
import enderdragon.magic_and_taboo.util.CapabilityUtil;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerMagicPointImpl implements ICapabilityProvider, PlayerMagicPoint, INBTSerializable<CompoundTag> {
    public final LazyOptional<PlayerMagicPoint> holder = LazyOptional.of(() -> this);
    public final Player player;
    private int maxMagic = 100;
    private int magic = 0;
    private int nextMagic;

    public PlayerMagicPointImpl(Player player) {
        this.player = player;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return MATCapabilities.PLAYER_MAGIC_POINT.orEmpty(cap, this.holder);
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
        return this.magic;
    }

    @Override
    public int getMaxMagic() {
        return this.maxMagic;
    }

    protected void setMagicImpl(int magic) {
        this.magic = Mth.clamp(magic, 0, this.maxMagic);
    }

    @Override
    public void setMagic(int amount) {
        this.setMagicImpl(amount);
        if (this.player instanceof ServerPlayer) {
            NetworkHandler.CHANNEL.send(
                    PacketDistributor.PLAYER.with(() -> (ServerPlayer) this.player),
                    new SyncMagicPointPayload(this.magic)
            );
        }
    }

    @Override
    public void setMagic(int max, int magic) {
        this.maxMagic = max;
        this.setMagicImpl(magic);
        if (this.player instanceof ServerPlayer) {
            NetworkHandler.CHANNEL.send(
                    PacketDistributor.PLAYER.with(() -> (ServerPlayer) this.player),
                    new InitMagicPointPayload(this.maxMagic, this.magic)
            );
        }
    }

    @Override
    public void tick() {
        if (this.nextMagic > 0) {
            --this.nextMagic;
            return;
        }
        this.nextMagic = 20;
        this.setMagicImpl(this.magic + 1);
    }

    public static void onTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            event.player.getCapability(MATCapabilities.PLAYER_MAGIC_POINT).ifPresent(PlayerMagicPoint::tick);
        }
    }

    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            var capability = CapabilityUtil.getCapability(player, MATCapabilities.PLAYER_MAGIC_POINT);
            if (capability == null) return;
            NetworkHandler.CHANNEL.send(
                    PacketDistributor.PLAYER.with(() -> player),
                    new InitMagicPointPayload(capability.getMaxMagic(), capability.getMagic())
            );
        }
    }
}
