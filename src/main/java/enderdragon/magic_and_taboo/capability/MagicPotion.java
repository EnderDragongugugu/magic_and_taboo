package enderdragon.magic_and_taboo.capability;

import enderdragon.magic_and_taboo.init.MATCapabilities;
import enderdragon.magic_and_taboo.registry.Element;
import enderdragon.magic_and_taboo.util.RegistryAccessor;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatMaps;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashSet;

public class MagicPotion implements ICapabilityProvider, IMagicPotion, INBTSerializable<CompoundTag> {
    public final LazyOptional<IMagicPotion> holder = LazyOptional.of(() -> this);
    private Object2FloatMap<Element> access;
    protected Object2FloatMap<Element> elements;

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
        this.elements = elements;
    }

    @Override
    public Collection<MobEffectInstance> getEffectInstances() {
        Collection<MobEffectInstance> set = new HashSet<>();
        if (this.elements == null) return set;
        for (var entry : this.elements.object2FloatEntrySet()) {
            var element = entry.getKey();
            var effectInstance = new MobEffectInstance(element.effect());
            set.add(effectInstance);
        }
        return set;
    }
}
