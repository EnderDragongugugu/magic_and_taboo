package enderdragon.magic_and_taboo.item;

import enderdragon.magic_and_taboo.capability.ElementHolderImpl;
import enderdragon.magic_and_taboo.capability.MagicPotion;
import enderdragon.magic_and_taboo.init.MATCapabilities;
import enderdragon.magic_and_taboo.init.MATItems;
import enderdragon.magic_and_taboo.init.MagicPotionSolvents;
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
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static enderdragon.magic_and_taboo.capability.ElementHolder.DEFAULT_FLUID_TYPE;

public class AlchemyElementItem extends Item {
//    public static int getColor(ItemStack stack, int layer) {
//        if (layer > 0) return -1;
//        var holder = CapabilityUtil.getCapability(stack, MATCapabilities.ELEMENT_HOLDER);
//        if (holder == null) return -1;
//        var elements = holder.getElements();
//        return elements.size() == 0 ? -1 :
//        return elements.size() == 0 ? -1 :
//                elements.size() == 1 ?
//                holder.getMaxElement().effect().getColor() : MagicPotion.getLayerColor(stack , layer);
//    }

    public static ItemStack makeDisplayStack(Holder.Reference<Element> element) {
        var stack = new ItemStack(MATItems.ALCHEMY_ELEMENT.get());
        var holder = CapabilityUtil.getCapability(stack, MATCapabilities.ELEMENT_HOLDER);
        if (holder == null) return stack;
        var map = new Reference2FloatOpenHashMap<Element>();
        map.addTo(element.value(),element.value().concentration().min() * 0.1F);
        holder.setElement(map);
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
        var elements = holder.getElements();
        var element = holder.getMaxElement();
        if(elements.size() == 0){
            return super.getName(stack);
        }
        return Component.translatable("item.magic_and_taboo.alchemy_element.element." + (elements.size() == 1 ? 0 : 1), element.getDisplayName());
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
        if(elements.reference2FloatEntrySet().size() == 1){
            var max = holder.getMaxElement();
            if(max.concentration().min() * 0.1F != holder.getAmount(max)){
                tooltips.add(Component.translatable("tooltip.magic_and_taboo.alchemy_element.element.1",holder.getAmount(max),max.getDisplayName()));
            }else {
                tooltips.add(Component.translatable("tooltip.magic_and_taboo.alchemy_element.element.0"));
            }
        }else {
            for(var entry : elements.reference2FloatEntrySet()){
                tooltips.add(Component.translatable("tooltip.magic_and_taboo.alchemy_element.element.1", entry.getFloatValue(),entry.getKey().getDisplayName()));
            }
        }
//        tooltips.add(Component.translatable("tooltip.magic_and_taboo.alchemy_element.element.1", 1,holder.getMaxElement().getDisplayName()));


    }
}
