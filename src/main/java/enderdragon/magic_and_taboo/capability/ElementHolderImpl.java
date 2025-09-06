package enderdragon.magic_and_taboo.capability;


import enderdragon.magic_and_taboo.init.MATCapabilities;
import enderdragon.magic_and_taboo.init.MagicPotionSolvents;
import enderdragon.magic_and_taboo.registry.Element;
import enderdragon.magic_and_taboo.util.RegistryAccessor;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.Reference2FloatMap;
import it.unimi.dsi.fastutil.objects.Reference2FloatMaps;
import it.unimi.dsi.fastutil.objects.Reference2FloatOpenHashMap;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
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

import static enderdragon.magic_and_taboo.capability.MagicPotionImpl.populateColor;

public class ElementHolderImpl implements ICapabilityProvider, ElementHolder, INBTSerializable<CompoundTag> {
    public final LazyOptional<ElementHolder> holder = LazyOptional.of(() -> this);

    private @Nullable Reference2FloatMap<Element> access;
    private Element maxElement;

    private @Nullable List<MobEffectInstance> effects;
    protected @Nullable Reference2FloatMap<Element> elements;
    private boolean invalidColor = true;
    private int color;

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        return MATCapabilities.ELEMENT_HOLDER.orEmpty(cap, this.holder);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public CompoundTag serializeNBT() {
        var root = new CompoundTag();
        var elements = new CompoundTag();
        if (this.elements == null) return root;
        var registries = RegistryAccessor.getOptionalRegistries();
        if (registries == null) return root;
        var lookup = registries.registryOrThrow(Element.RESOURCE_KEY);
        for (var entry : this.elements.reference2FloatEntrySet()) {
            elements.putFloat(lookup.getKey(entry.getKey()).toString(), entry.getFloatValue());
        }
        root.put("Elements", elements);
        return root;
    }

    @Override
    public void deserializeNBT(CompoundTag root) {
        var map = new Reference2FloatOpenHashMap<Element>();
        var registries = RegistryAccessor.getOptionalRegistries();
        var elements = root.getCompound("Elements");
        var lookup = registries.registryOrThrow(Element.RESOURCE_KEY);
        for (var entry : lookup.entrySet()) {
            float value = elements.getFloat(entry.getKey().location().toString());
            if (value <= 0.0F) continue;
            map.put(entry.getValue(), value);
        }
        this.setElement(map);

    }

    @Override
    public Reference2FloatMap<Element> getElements() {
        if (this.access == null) {
            if (this.elements == null) {
                this.elements = new Reference2FloatOpenHashMap<>();
            }
            this.access = Reference2FloatMaps.unmodifiable(this.elements);
        }
        return this.access;
    }


    @Override
    public void setElement(Reference2FloatMap<Element> elements) {
        this.access = null;
        this.effects = null;
        this.elements = elements;
        this.invalidColor = true;
        if(this.elements.reference2FloatEntrySet().size() == 1){
            this.maxElement = this.elements.reference2FloatEntrySet().iterator().next().getKey();
        }else {
            var max = this.elements.reference2FloatEntrySet().stream().max((p1 , p2) ->Float.compare(p1.getFloatValue(), p2.getFloatValue()));
            max.ifPresent(elementEntry -> this.maxElement = elementEntry.getKey());
        }
    }

    @Override
    public Element getMaxElement() {
        return this.maxElement;
    }

    @Override
    public float getAmount(Element element) {
        if(this.elements != null){
            return this.elements.getFloat(element);
        }
        return 0.0F;
    }


    @Override
    public boolean isFatal() {
        if (this.elements == null) return false;
        for (var entry : this.elements.reference2FloatEntrySet()) {
            if (entry.getFloatValue() >= entry.getKey().concentration().max()) {
                return true;
            }
        }
        return false;
    }

    public List<MobEffectInstance> getEffects() {
        if (this.effects == null) {
            if (this.elements == null) return Collections.emptyList();
            var effects = new ObjectArrayList<MobEffectInstance>(elements.size());
            for (var entry : elements.reference2FloatEntrySet()) {
                var element = entry.getKey();
                effects.add(element.getEffect(entry.getFloatValue(), 1, 1));
            }
            this.effects = effects;
        }
        return this.effects;
    }

    @Override
    public int getColor() {
        if (this.invalidColor) {
            this.invalidColor = false;
            this.color = populateColor( this.getEffects());
        }
        return this.color;
    }
    public int populateColor( Collection<MobEffectInstance> effects) {
        if (effects.isEmpty()) return DEFAULT_COLOR;
        float f = 0.0F;
        float f1 = 0.0F;
        float f2 = 0.0F;
        int j = 0;

        for (var effect : effects) {
            if (effect.isVisible()) {
                int k = effect.getEffect().getColor();
                int l = effect.getAmplifier() + 1;
                f += (float) (l * (k >> 16 & 255)) / 255.0F;
                f1 += (float) (l * (k >> 8 & 255)) / 255.0F;
                f2 += (float) (l * (k & 255)) / 255.0F;
                j += l;
            }
        }

        if (j == 0) return 0;
        f = f / (float) j * 255.0F;
        f1 = f1 / (float) j * 255.0F;
        f2 = f2 / (float) j * 255.0F;
        return (int) f << 16 | (int) f1 << 8 | (int) f2;
    }
}
