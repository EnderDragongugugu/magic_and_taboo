package enderdragon.magic_and_taboo.capability;

import enderdragon.magic_and_taboo.init.MATCapabilities;
import enderdragon.magic_and_taboo.registry.Element;
import enderdragon.magic_and_taboo.util.RegistryAccessor;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ElementHolderImpl implements ICapabilityProvider, ElementHolder, INBTSerializable<CompoundTag> {
    public final LazyOptional<ElementHolder> holder = LazyOptional.of(() -> this);

    private Element element;
    private float amount;

    @Override
    public void setElement(@Nullable Element element) {
        this.element = element;
        if (element == null) {
            this.amount = 0.0F;
        }
    }

    @Override
    public Element getElement() {
        return this.element;
    }

    @Override
    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Override
    public float getAmount() {
        return this.amount;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return MATCapabilities.ELEMENT_HOLDER.orEmpty(cap, this.holder);
    }

    @Override
    public CompoundTag serializeNBT() {
        var root = new CompoundTag();
        if (this.element == null) return root;
        var registries = RegistryAccessor.getOptionalRegistries();
        if (registries == null) return root;
        var key = registries.registryOrThrow(Element.RESOURCE_KEY).getKey(this.element);
        if (key == null) return root;
        root.putString("Element", key.toString());
        root.putFloat("Amount", this.amount);
        return root;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.setAmount(0.0F);
        var registries = RegistryAccessor.getOptionalRegistries();
        if (registries == null) return;
        this.setAmount(nbt.getFloat("Amount"));
        this.setElement(registries.registryOrThrow(Element.RESOURCE_KEY).get(
                ResourceLocation.tryParse(nbt.getString("Element"))
        ));
    }
}
