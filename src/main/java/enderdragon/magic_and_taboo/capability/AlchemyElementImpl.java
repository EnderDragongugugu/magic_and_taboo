package enderdragon.magic_and_taboo.capability;

import enderdragon.magic_and_taboo.init.MATCapabilities;
import enderdragon.magic_and_taboo.registry.Element;
import enderdragon.magic_and_taboo.util.RegistryAccessor;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AlchemyElementImpl implements ICapabilityProvider, AlchemyElement, INBTSerializable<CompoundTag> {
    public final LazyOptional<AlchemyElement> holder = LazyOptional.of(() -> this);

    private ResourceKey<Element> key;
    private Element element;
    private Float count = 0.0F;

    @Override
    public void setElement(ResourceKey<Element> key, Float count) {
        this.key = key;
        this.count = count;
    }

    @Override
    public Element getElement() {
        return element;
    }

    @Override
    public float getCount() {
        return this.count;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return MATCapabilities.ALCHEMY_ELEMENT.orEmpty(cap, this.holder);
    }

    @Override
    public CompoundTag serializeNBT() {
        var root = new CompoundTag();
        if (this.key == null) return root;
        var elements = new CompoundTag();
        elements.putFloat(this.key.location().toString(), this.count);
        root.put("Element", elements);
        return root;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        var registries = RegistryAccessor.access();
        var element = nbt.getCompound("Element");
        var lookup = registries.registryOrThrow(Element.RESOURCE_KEY);
        for (var entry : lookup.entrySet()) {
            float value = element.getFloat(entry.getKey().location().toString());
            if (value <= 0.0F) continue;
            this.element = entry.getValue();
        }
    }
}
