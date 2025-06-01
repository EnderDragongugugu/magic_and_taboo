package enderdragon.magic_and_taboo.item;

import enderdragon.magic_and_taboo.capability.AlchemyElementImpl;
import enderdragon.magic_and_taboo.init.MATItems;
import enderdragon.magic_and_taboo.registry.Element;
import enderdragon.magic_and_taboo.util.RegistryAccessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;

public class AlchemyElementItem extends Item {
    public AlchemyElementItem(Properties pProperties) {
        super(pProperties);
    }

    public static CompoundTag serializeNBT(ResourceKey<Element> key, Float count) {
        var tag = new CompoundTag();
        var element = new CompoundTag();
        element.putFloat(key.location().toString(), count);
        tag.put("Element", element);
        return tag;
    }

    @Nullable
    public static Element deserializeNBT(ItemStack stack) {
        var nbt = stack.getOrCreateTag();
        var registries = RegistryAccessor.access();
        if (!nbt.contains("Element")) return null;
        var element = nbt.getCompound("Element");
        var lookup = registries.registryOrThrow(Element.RESOURCE_KEY);
        for (var entry : lookup.entrySet()) {
            float value = element.getFloat(entry.getKey().location().toString());
            if (value <= 0.0F) continue;
            return entry.getValue();
        }
        return null;
    }

    public static ItemStack createForElement(ResourceKey<Element> key) {
        ItemStack itemStack = new ItemStack(MATItems.ALCHEMY_ELEMENT.get());
        var tag = serializeNBT(key, 10.0F);
        itemStack.setTag(tag);
//        var cap = itemStack.getCapability(MATCapabilities.ALCHEMY_ELEMENT).orElse(AlchemyElement.EMPTY);
//        cap.setElement(key, 10.0F);
        return itemStack;
    }

    @Override
    public Component getName(ItemStack pStack) {
        var element = deserializeNBT(pStack);
        if (element != null) {
            return Component.translatable("item.magic_and_taboo.alchemy_element.element", element.getDisplayName());
        }
//        var cap = pStack.getCapability(MATCapabilities.ALCHEMY_ELEMENT).orElse(AlchemyElement.EMPTY);
//        if (cap.getElement() != null) {
//            return Component.translatable("item.magic_and_taboo.alchemy_element.element", cap.getElement().getDisplayName());
//        }
        return super.getName(pStack);
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new AlchemyElementImpl();
    }
}
