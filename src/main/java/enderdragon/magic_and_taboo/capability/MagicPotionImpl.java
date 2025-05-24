package enderdragon.magic_and_taboo.capability;

import enderdragon.magic_and_taboo.init.MATCapabilities;
import enderdragon.magic_and_taboo.init.SolventRegistry;
import enderdragon.magic_and_taboo.registry.Element;
import enderdragon.magic_and_taboo.util.RegistryAccessor;
import enderdragon.magic_and_taboo.util.Solvent;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatMaps;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class MagicPotionImpl implements ICapabilityProvider, MagicPotion, INBTSerializable<CompoundTag> {
    public final LazyOptional<MagicPotion> holder = LazyOptional.of(() -> this);
    private Object2FloatMap<Element> access;
    private String solvent;
    private ObjectArrayList<MobEffectInstance> effects;
    protected Object2FloatMap<Element> elements;
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

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        CompoundTag element = new CompoundTag();
        if (this.elements == null) return element;
        var registries = RegistryAccessor.access();
        if (registries == null) return element;
        var lookup = registries.registryOrThrow(Element.RESOURCE_KEY);
        for (var entry : this.elements.object2FloatEntrySet()) {
            //noinspection DataFlowIssue
            element.putFloat(lookup.getKey(entry.getKey()).toString(), entry.getFloatValue());
        }
        nbt.putString("Solvent", this.solvent);
        nbt.put("Elements", element);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        var map = new Object2FloatOpenHashMap<Element>();
        var registries = RegistryAccessor.access();
        if (registries == null) return;
        var elements = nbt.getCompound("Elements");
        var lookup = registries.registryOrThrow(Element.RESOURCE_KEY);
        for (var entry : lookup.entrySet()) {
            float value = elements.getFloat(entry.getKey().location().toString());
            if (value <= 0.0F) continue;
            map.put(entry.getValue(), value);
        }
        this.setElements(map);
        this.solvent = nbt.getString("Solvent");
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
    public void setElements(Object2FloatMap<Element> elements) {
        this.access = null;
        this.effects = null;
        this.elements = elements;
    }

    @Override
    public void setSolventType(String type) {
        this.solvent = type;
    }

    @Override
    public String getSolventType() {
        return this.solvent;
    }

    @Override
    public boolean isFatal() {
        for (var entry : this.elements.object2FloatEntrySet()) {
            if (entry.getFloatValue() >= entry.getKey().concentration().max()) {
                return true;
            }
        }
        return false;
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
            var s = SolventRegistry.get(this.solvent);
            if (s != null) {
                return s.getEffectInstances(elements, this.timeFactor, this.baseLevel);
            } else {
                return new Solvent().getEffectInstances(elements, this.timeFactor, this.baseLevel);
            }
        }
        return this.effects;
    }
}
