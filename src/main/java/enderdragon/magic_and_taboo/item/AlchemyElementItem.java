package enderdragon.magic_and_taboo.item;

import enderdragon.magic_and_taboo.capability.ElementHolderImpl;
import enderdragon.magic_and_taboo.init.MATCapabilities;
import enderdragon.magic_and_taboo.init.MATItems;
import enderdragon.magic_and_taboo.registry.Element;
import enderdragon.magic_and_taboo.util.CapabilityUtil;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AlchemyElementItem extends Item {
    public static int getColor(ItemStack stack, int layer) {
        if (layer > 0) return -1;
        var holder = CapabilityUtil.getCapability(stack, MATCapabilities.ELEMENT_HOLDER);
        if (holder == null) return -1;
        var element = holder.getElement();
        return element == null ? -1 : element.effect().getColor();
    }

    public static ItemStack makeDisplayStack(Holder.Reference<Element> element) {
        var stack = new ItemStack(MATItems.ALCHEMY_ELEMENT.get());
        var holder = CapabilityUtil.getCapability(stack, MATCapabilities.ELEMENT_HOLDER);
        if (holder == null) return stack;
        holder.setElement(element.value());
        holder.setAmount(element.value().concentration().min() * 0.1F);
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
        var holder = CapabilityUtil.getCapability(stack, MATCapabilities.ELEMENT_HOLDER);
        if (holder == null) return super.getName(stack);
        var element = holder.getElement();
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

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltips, TooltipFlag pIsAdvanced) {
        var holder = CapabilityUtil.getCapability(stack, MATCapabilities.ELEMENT_HOLDER);
        if (holder == null) return;
        var element = holder.getElement();
        if (element != null) {
            tooltips.add(Component.translatable("tooltip.magic_and_taboo.alchemy_element.element", element.getDisplayName()));
        }
    }
}
