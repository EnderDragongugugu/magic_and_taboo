package enderdragon.magic_and_taboo.capability;

import enderdragon.magic_and_taboo.init.MATCapabilities;
import enderdragon.magic_and_taboo.registry.Element;
import enderdragon.magic_and_taboo.util.RegistryAccessor;
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
        CompoundTag element = new CompoundTag();
        if (this.elements == null) return element;
        var registries = RegistryAccessor.access();
        if (registries == null) return element;
        var lookup = registries.registryOrThrow(Element.RESOURCE_KEY);
        for (var entry : this.elements.object2FloatEntrySet()) {
            //noinspection DataFlowIssue
            element.putFloat(lookup.getKey(entry.getKey()).toString(), entry.getFloatValue());
        }
        return element;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        var map = new Object2FloatOpenHashMap<Element>();
        var registries = RegistryAccessor.access();
        if (registries == null) return;
        var lookup = registries.registryOrThrow(Element.RESOURCE_KEY);
        for (var entry : lookup.entrySet()) {
            float value = nbt.getFloat(entry.getKey().location().toString());
            if (value <= 0.0F) continue;
            map.put(entry.getValue(), value);
        }
        this.setElements(map);
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
            var effects = new ObjectArrayList<MobEffectInstance>(this.elements.size());
            for (var entry : this.elements.object2FloatEntrySet()) {
                var element = entry.getKey();
                if (element.concentration().min() <= entry.getFloatValue()) {
                    effects.add(element.getEffect(entry.getFloatValue(), this.timeFactor, this.baseLevel));
                }
            }
            this.effects = effects;
        }
        return this.effects;
    }
}
