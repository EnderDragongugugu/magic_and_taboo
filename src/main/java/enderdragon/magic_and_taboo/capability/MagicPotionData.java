package enderdragon.magic_and_taboo.capability;

import enderdragon.magic_and_taboo.init.MATCapabilities;
import enderdragon.magic_and_taboo.registry.Element;
import enderdragon.magic_and_taboo.util.RegistryAccessor;
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

public class MagicPotionData implements ICapabilityProvider, IMagicPotionData, INBTSerializable<CompoundTag> {
    public final LazyOptional<IMagicPotionData> holder = LazyOptional.of(() -> this);
    Object2FloatOpenHashMap<Element> elementMap;

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return MATCapabilities.MAGIC_POTION_DATA.orEmpty(cap, holder);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag element = new CompoundTag();
        if (elementMap == null) return element;
        if (RegistryAccessor.access() == null) return element;
        var e = RegistryAccessor.access().registryOrThrow(Element.RESOURCE_KEY);
        for (var entry : elementMap.object2FloatEntrySet()) {
            element.putFloat(e.getKey(entry.getKey()).toString(), entry.getFloatValue());
        }
        return element;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        Object2FloatOpenHashMap<Element> map = new Object2FloatOpenHashMap<>();
        var registries = RegistryAccessor.access();
        if (registries == null) return;
        var e = registries.registryOrThrow(Element.RESOURCE_KEY);
        for (var element : e) {
            float value = nbt.getFloat(e.getKey(element).toString());
            if (value <= 0.0F) continue;
            map.addTo(element, value);
        }
        elementMap = map;
    }

    @Override
    public Object2FloatOpenHashMap<Element> getElementMap() {
        return elementMap;
    }

    @Override
    public void setElementMap(Object2FloatOpenHashMap<Element> map) {
        elementMap = map;
    }

    @Override
    public Collection<MobEffectInstance> getEffectInstances() {
        Collection<MobEffectInstance> set = new HashSet<>();
        if (elementMap == null) return set;
        for (var entry : elementMap.object2FloatEntrySet()) {
            var element = entry.getKey();
            var effectInstance = new MobEffectInstance(element.effect());
            set.add(effectInstance);
        }
        return set;
    }
}
