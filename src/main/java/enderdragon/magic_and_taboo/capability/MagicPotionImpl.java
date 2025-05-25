package enderdragon.magic_and_taboo.capability;

import enderdragon.magic_and_taboo.init.MATCapabilities;
import enderdragon.magic_and_taboo.init.MagicPotionSolvents;
import enderdragon.magic_and_taboo.registry.Element;
import enderdragon.magic_and_taboo.util.RegistryAccessor;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatMaps;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class MagicPotionImpl implements ICapabilityProvider, MagicPotion, INBTSerializable<CompoundTag> {
    public final LazyOptional<MagicPotion> holder = LazyOptional.of(() -> this);
    private @Nullable Object2FloatMap<Element> access;
    private @Nullable FluidType solvent;
    private @Nullable List<MobEffectInstance> effects;
    protected @Nullable Object2FloatMap<Element> elements;
    protected final float timeFactor;
    protected final int baseLevel;

    public MagicPotionImpl(float timeFactor, int baseLevel) {
        this.timeFactor = timeFactor;
        this.baseLevel = baseLevel;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return MATCapabilities.MAGIC_POTION.orEmpty(cap, this.holder);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public CompoundTag serializeNBT() {
        var root = new CompoundTag();
        if (this.solvent != null) {
            root.putString("Solvent", ForgeRegistries.FLUID_TYPES.get().getKey(this.solvent).toString());
        }
        var elements = new CompoundTag();
        if (this.elements == null) return root;
        var registries = RegistryAccessor.access();
        if (registries == null) return root;
        var lookup = registries.registryOrThrow(Element.RESOURCE_KEY);
        for (var entry : this.elements.object2FloatEntrySet()) {
            elements.putFloat(lookup.getKey(entry.getKey()).toString(), entry.getFloatValue());
        }
        root.put("Elements", elements);
        return root;
    }

    @Override
    public void deserializeNBT(CompoundTag root) {
        var map = new Object2FloatOpenHashMap<Element>();
        var registries = RegistryAccessor.access();
        if (registries == null) {
            this.setContent(null, map);
            return;
        }
        var elements = root.getCompound("Elements");
        var lookup = registries.registryOrThrow(Element.RESOURCE_KEY);
        for (var entry : lookup.entrySet()) {
            float value = elements.getFloat(entry.getKey().location().toString());
            if (value <= 0.0F) continue;
            map.put(entry.getValue(), value);
        }
        var solvent = ResourceLocation.tryParse(root.getString("Solvent"));
        this.setContent(solvent == null ? null : ForgeRegistries.FLUID_TYPES.get().getValue(solvent), map);
    }

    @Override
    public Object2FloatMap<Element> getElements() {
        if (this.access == null) {
            if (this.elements == null) {
                this.elements = new Object2FloatOpenHashMap<>();
            }
            this.access = Object2FloatMaps.unmodifiable(this.elements);
        }
        return this.access;
    }

    @Override
    public void setContent(@Nullable FluidType solvent, Object2FloatMap<Element> elements) {
        this.access = null;
        this.effects = null;
        this.elements = elements;
        this.solvent = solvent == ForgeMod.EMPTY_TYPE.get() ? null : solvent;
    }

    @Override
    public @Nullable FluidType getSolvent() {
        return this.solvent;
    }

    @Override
    public boolean isFatal() {
        if (this.elements == null) return false;
        for (var entry : this.elements.object2FloatEntrySet()) {
            if (entry.getFloatValue() >= entry.getKey().concentration().max()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<MobEffectInstance> getEffects() {
        if (this.effects == null) {
            if (this.elements == null) return Collections.emptyList();
            this.effects = MagicPotionSolvents.getSolvent(this.solvent)
                    .getEffects(this.elements, this.timeFactor, this.baseLevel);
        }
        return this.effects;
    }
}
