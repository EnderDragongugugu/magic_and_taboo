package enderdragon.magic_and_taboo.item;

import enderdragon.magic_and_taboo.capability.ElementStorageImpl;
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
    public static ItemStack makeDisplayStack(Holder.Reference<Element> element) {
        var stack = new ItemStack(MATItems.ALCHEMY_ELEMENT.get());
        var storage = CapabilityUtil.getCapability(stack, MATCapabilities.ELEMENT_STORAGE);
        if (storage == null) return stack;
        storage.setConcentration(element.value(), element.value().concentration().min() * 0.1F);
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
        var storage = CapabilityUtil.getCapability(stack, MATCapabilities.ELEMENT_STORAGE);
        if (storage == null) return super.getName(stack);
        var major = storage.getMajorElement();
        if (major == null) return super.getName(stack);
        var concentrations = storage.getConcentrations();
        return switch (concentrations.size()) {
            case 0 -> super.getName(stack);
            case 1 -> Component.translatable("item.magic_and_taboo.alchemy_element.pure", major.getDisplayName());
            default -> Component.translatable("item.magic_and_taboo.alchemy_element.mixed", major.getDisplayName());
        };
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag root) {
        var storage = new ElementStorageImpl();
        if (root != null) {
            storage.deserializeNBT(root);
        }
        return storage;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltips, TooltipFlag pIsAdvanced) {
        var storage = CapabilityUtil.getCapability(stack, MATCapabilities.ELEMENT_STORAGE);
        if (storage == null) return;
        var concentrations = storage.getConcentrations();
        if (concentrations.size() == 1) {
            var entry = concentrations.reference2FloatEntrySet().iterator().next();
            var major = entry.getKey();
            if (major.concentration().min() * 0.1F == entry.getFloatValue()) {
                tooltips.add(Component.translatable("tooltip.magic_and_taboo.alchemy_element.pure"));
            } else {
                tooltips.add(Component.translatable("tooltip.magic_and_taboo.alchemy_element.mixed", entry.getFloatValue(), major.getDisplayName()));
            }
        } else {
            for (var entry : concentrations.reference2FloatEntrySet()) {
                tooltips.add(Component.translatable("tooltip.magic_and_taboo.alchemy_element.mixed", entry.getFloatValue(), entry.getKey().getDisplayName()));
            }
        }
    }
}
