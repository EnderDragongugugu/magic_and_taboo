package enderdragon.magic_and_taboo.capability;

import enderdragon.magic_and_taboo.init.MATCapabilities;
import enderdragon.magic_and_taboo.registry.Element;
import enderdragon.magic_and_taboo.util.FloatMaps;
import enderdragon.magic_and_taboo.util.RegistryAccessor;
import it.unimi.dsi.fastutil.objects.Reference2FloatMap;
import it.unimi.dsi.fastutil.objects.Reference2FloatMaps;
import it.unimi.dsi.fastutil.objects.Reference2FloatOpenHashMap;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ElementStorageImpl implements ICapabilityProvider, ElementStorage, INBTSerializable<CompoundTag> {
    public final LazyOptional<? extends ElementStorage> holder = LazyOptional.of(() -> this);
    private @Nullable Reference2FloatMap<Element> concentrations;
    private @Nullable Reference2FloatMap<Element> access;
    private @Nullable Element major;
    private @Nullable List<MobEffectInstance> effects;
    private boolean invalidColor = true;
    private int color;

    protected int getDefaultColor() {
        return DEFAULT_COLOR;
    }

    protected List<MobEffectInstance> resolveEffects(Reference2FloatMap<Element> concentrations) {
        return Element.resolveEffects(concentrations);
    }

    public List<MobEffectInstance> getEffects() {
        if (this.effects == null) {
            if (this.concentrations == null) return Collections.emptyList();
            this.effects = this.resolveEffects(this.concentrations);
        }
        return this.effects;
    }

    @Override
    public @Nullable Element getMajorElement() {
        return this.major;
    }

    @Override
    public Reference2FloatMap<Element> getConcentrations() {
        if (this.access == null) {
            if (this.concentrations == null) {
                this.concentrations = new Reference2FloatOpenHashMap<>();
            }
            this.access = Reference2FloatMaps.unmodifiable(this.concentrations);
        }
        return this.access;
    }

    @Override
    public float getConcentration(Element element) {
        var concentrations = this.concentrations;
        return concentrations == null ? 0.0F : concentrations.getFloat(element);
    }

    @Override
    public void setConcentration(Element element, float concentration) {
        var concentrations = this.concentrations;
        if (concentrations == null) {
            this.concentrations = concentrations = new Reference2FloatOpenHashMap<>();
        }
        concentrations.put(element, concentration);
        this.major = findMajorElement(concentrations);
    }

    @Override
    public void setConcentrations(Reference2FloatMap<Element> concentrations) {
        this.access = null;
        this.effects = null;
        this.concentrations = concentrations;
        this.invalidColor = true;
        this.major = findMajorElement(concentrations);
    }

    @Override
    public int getColor() {
        if (this.invalidColor) {
            var effects = this.getEffects();
            this.color = effects.isEmpty() ? this.getDefaultColor() : resolveColor(effects);
            this.invalidColor = false;
        }
        return this.color;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        return cap == MATCapabilities.ELEMENT_SOURCE || cap == MATCapabilities.ELEMENT_STORAGE ? this.holder.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        var root = new CompoundTag();
        if (this.concentrations == null) return root;
        var registries = RegistryAccessor.getOptionalRegistries();
        if (registries == null) return root;
        var lookup = registries.registryOrThrow(Element.RESOURCE_KEY);
        for (var entry : this.concentrations.reference2FloatEntrySet()) {
            root.putFloat(String.valueOf(lookup.getKey(entry.getKey())), entry.getFloatValue());
        }
        return root;
    }

    @Override
    public void deserializeNBT(CompoundTag root) {
        var elements = deserializeElements(root);
        this.setConcentrations(elements == null ? new Reference2FloatOpenHashMap<>() : elements);
    }

    public static @Nullable Reference2FloatOpenHashMap<Element> deserializeElements(CompoundTag tag) {
        var registries = RegistryAccessor.getOptionalRegistries();
        if (registries == null) return null;
        var elements = new Reference2FloatOpenHashMap<Element>();
        var lookup = registries.registryOrThrow(Element.RESOURCE_KEY);
        for (var entry : lookup.entrySet()) {
            float value = tag.getFloat(entry.getKey().location().toString());
            if (value <= 0.0F) continue;
            elements.put(entry.getValue(), value);
        }
        return elements;
    }

    public static int resolveColor(Collection<MobEffectInstance> effects) {
        float red = 0.0F, green = 0.0F, blue = 0.0F;
        int weights = 0;
        for (var effect : effects) {
            if (effect.isVisible()) {
                int color = effect.getEffect().getColor();
                int weight = effect.getAmplifier() + 1;
                red += (float) (weight * (color >> 16 & 255)) / 255.0F;
                green += (float) (weight * (color >> 8 & 255)) / 255.0F;
                blue += (float) (weight * (color & 255)) / 255.0F;
                weights += weight;
            }
        }
        if (weights == 0) return 0;
        float factor = weights / 255.0F;
        return (int) (red / factor) << 16 | (int) (green / factor) << 8 | (int) (blue / factor);
    }

    public static @Nullable Element findMajorElement(Reference2FloatMap<Element> elements) {
        return elements.reference2FloatEntrySet().stream().max(FloatMaps::compareByFloat).map(Map.Entry::getKey).orElse(null);
    }
}
