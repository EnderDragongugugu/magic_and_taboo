package enderdragon.magic_and_taboo.capability;

import enderdragon.magic_and_taboo.init.MATCapabilities;
<<<<<<< Updated upstream
import enderdragon.magic_and_taboo.init.MagicPotionSolvents;
=======
<<<<<<< HEAD
import enderdragon.magic_and_taboo.init.MATSolvents;
=======
import enderdragon.magic_and_taboo.init.MagicPotionSolvents;
>>>>>>> b8efb8187c805fb72d3179702d879cf804ddbc8b
>>>>>>> Stashed changes
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

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MagicPotionImpl implements ICapabilityProvider, MagicPotion, INBTSerializable<CompoundTag> {
    public final LazyOptional<MagicPotion> holder = LazyOptional.of(() -> this);
<<<<<<< Updated upstream
=======
<<<<<<< HEAD
    private Object2FloatMap<Element> access;
    private String solvent;
    private int color = 3694022;
    private ObjectArrayList<MobEffectInstance> effects;
    protected Object2FloatMap<Element> elements;
=======
>>>>>>> Stashed changes
    private @Nullable Object2FloatMap<Element> access;
    private @Nullable FluidType solvent;
    private @Nullable List<MobEffectInstance> effects;
    protected @Nullable Object2FloatMap<Element> elements;
<<<<<<< Updated upstream
=======
>>>>>>> b8efb8187c805fb72d3179702d879cf804ddbc8b
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
    public List<MobEffectInstance> getEffects() {
        if (this.effects == null) {
            if (this.elements == null) return Collections.emptyList();
            this.effects = MagicPotionSolvents.getSolvent(this.solvent)
                    .getEffects(this.elements, this.timeFactor, this.baseLevel);
=======
<<<<<<< HEAD
    public int getColor() {
        if (this.solvent == null) return this.color;
        var s = MATSolvents.get(this.solvent);
        if (s != null) {
            return s.getColor();
        }
        return this.color;
    }

    @Override
    public List<MobEffectInstance> getEffectInstances() {
        if (this.effects == null) {
            if (this.elements == null) return Collections.emptyList();
//            var effects = new ObjectArrayList<MobEffectInstance>(this.elements.size());
//            for (var entry : this.elements.object2FloatEntrySet()) {
//                var element = entry.getKey();
//                if (element.concentration().min() <= entry.getFloatValue()) {
//                    effects.add(element.getEffect(entry.getFloatValue(), this.timeFactor, this.baseLevel));
//                }
//            }
//            var solvent = new Solvent(elements);
//            if (true) {
//                this.effects = solvent.getEffectInstances(this.timeFactor, this.baseLevel);
//            }
//            var s = SolventRegistry.create("water", elements);
//            this.effects = SolventRegistry.create("water", elements).getEffectInstances(this.timeFactor, this.baseLevel);
            var s = MATSolvents.get(this.solvent);
            if (s != null) {
                return s.getEffectInstances(elements, this.timeFactor, this.baseLevel);
            } else {
                return new Solvent().getEffectInstances(elements, this.timeFactor, this.baseLevel);
            }
=======
    public List<MobEffectInstance> getEffects() {
        if (this.effects == null) {
            if (this.elements == null) return Collections.emptyList();
            this.effects = MagicPotionSolvents.getSolvent(this.solvent)
                    .getEffects(this.elements, this.timeFactor, this.baseLevel);
>>>>>>> b8efb8187c805fb72d3179702d879cf804ddbc8b
>>>>>>> Stashed changes
        }
        return this.effects;
    }

    public static int getColor(int color, Collection<MobEffectInstance> effects) {
        int i = color;
        if (effects.isEmpty()) {
            return color;
        } else {
            float f = 0.0F;
            float f1 = 0.0F;
            float f2 = 0.0F;
            int j = 0;

            for (MobEffectInstance mobeffectinstance : effects) {
                if (mobeffectinstance.isVisible()) {
                    int k = mobeffectinstance.getEffect().getColor();
                    int l = mobeffectinstance.getAmplifier() + 1;
                    f += (float) (l * (k >> 16 & 255)) / 255.0F;
                    f1 += (float) (l * (k >> 8 & 255)) / 255.0F;
                    f2 += (float) (l * (k >> 0 & 255)) / 255.0F;
                    j += l;
                }
            }

            if (j == 0) {
                return 0;
            } else {
                f = f / (float) j * 255.0F;
                f1 = f1 / (float) j * 255.0F;
                f2 = f2 / (float) j * 255.0F;
                return (int) f << 16 | (int) f1 << 8 | (int) f2;
            }
        }
    }
}
