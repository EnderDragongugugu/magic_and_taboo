package enderdragon.magic_and_taboo.item;

import enderdragon.magic_and_taboo.capability.ElementHolderImpl;
import enderdragon.magic_and_taboo.init.MATCapabilities;
import enderdragon.magic_and_taboo.init.MATItems;
import enderdragon.magic_and_taboo.registry.AlchemyElement;
import enderdragon.magic_and_taboo.registry.Element;
import enderdragon.magic_and_taboo.util.CapabilityUtil;
import it.unimi.dsi.fastutil.objects.Reference2FloatOpenHashMap;
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

public class AlchemyMaterialItem extends Item {
    public static ItemStack makeDisplayStack(Item item, Holder.Reference<AlchemyElement> alchemyElement) {
        var stack = new ItemStack(item);
        var holder = CapabilityUtil.getCapability(stack, MATCapabilities.ELEMENT_HOLDER);
        if (holder == null) return stack;

        var map = new Reference2FloatOpenHashMap<Element>();
        for (var entry : alchemyElement.get().elementMap().object2FloatEntrySet()) {
            map.addTo(entry.getKey().get(), entry.getFloatValue());
        }

        holder.setElement(map);
        return stack;
    }

    public static ItemStack makeAlchemyPasteStack(Holder.Reference<AlchemyElement> alchemyElement) {
        return makeDisplayStack(MATItems.ALCHEMY_PASTE.get(), alchemyElement);
    }

    public static ItemStack makeAlchemySolutionStack(Holder.Reference<AlchemyElement> alchemyElement) {
        return makeDisplayStack(MATItems.ALCHEMY_SOLUTION.get(), alchemyElement);
    }

    public static ItemStack makeAlchemyPowderStack(Holder.Reference<AlchemyElement> alchemyElement) {
        return makeDisplayStack(MATItems.ALCHEMY_POWDER.get(), alchemyElement);
    }

    public static void fillDisplayStacks(CreativeModeTab.ItemDisplayParameters context, CreativeModeTab.Output entries) {
        var key = context.holders().lookupOrThrow(AlchemyElement.RESOURCE_KEY);
        key.listElements()
                .map(AlchemyMaterialItem::makeAlchemySolutionStack)
                .forEach(entries::accept);
        key.listElements()
                .map(AlchemyMaterialItem::makeAlchemyPasteStack)
                .forEach(entries::accept);
        key.listElements()
                .map(AlchemyMaterialItem::makeAlchemyPowderStack)
                .forEach(entries::accept);
    }

    public AlchemyMaterialItem(Properties props) {
        super(props);
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
        var elements = holder.getElements();
        if (elements.reference2FloatEntrySet().size() < 10) {
            for (var entry : elements.reference2FloatEntrySet()) {
                tooltips.add(Component.translatable("tooltip.magic_and_taboo.alchemy_material.element.0", entry.getKey().getDisplayName()));
            }
        } else {
            tooltips.add(Component.translatable("tooltip.magic_and_taboo.alchemy_material.element.1"));
        }

    }
}
