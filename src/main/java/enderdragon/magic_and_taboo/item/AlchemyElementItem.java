package enderdragon.magic_and_taboo.item;

import enderdragon.magic_and_taboo.capability.ElementHolder;
import enderdragon.magic_and_taboo.capability.ElementHolderImpl;
import enderdragon.magic_and_taboo.init.MATCapabilities;
import enderdragon.magic_and_taboo.init.MATItems;
import enderdragon.magic_and_taboo.registry.Element;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;

public class AlchemyElementItem extends Item {
    public static int getColor(ItemStack stack, int layer) {
        if (layer > 0) return -1;
        var element = stack.getCapability(MATCapabilities.ELEMENT_HOLDER).orElse(ElementHolder.EMPTY).getElement();
        return element == null ? -1 : element.effect().getColor();
    }

    public static ItemStack makeDisplayStack(Holder.Reference<Element> element) {
        var stack = new ItemStack(MATItems.ALCHEMY_ELEMENT.get());
        var holder = stack.getCapability(MATCapabilities.ELEMENT_HOLDER).orElse(ElementHolder.EMPTY);
        holder.setElement(element.value());
        holder.setAmount(10.0F);
        return stack;
    }

    public static void fillDisplayStacks(CreativeModeTab.ItemDisplayParameters context, CreativeModeTab.Output entries) {
        context.holders().lookupOrThrow(Element.RESOURCE_KEY)
                .listElements()
                .map(AlchemyElementItem::makeDisplayStack)
                .forEach(entries::accept);
    }

    public AlchemyElementItem(Properties props) {
        super(props);
    }

    @Override
    public Component getName(ItemStack stack) {
        var element = stack.getCapability(MATCapabilities.ELEMENT_HOLDER).orElse(ElementHolder.EMPTY).getElement();
        return element == null
                ? super.getName(stack)
                : Component.translatable("item.magic_and_taboo.alchemy_element.element", element.getDisplayName());
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        var element = new ElementHolderImpl();
        if (nbt != null) {
            element.deserializeNBT(nbt);
        }
        return element;
    }
}
