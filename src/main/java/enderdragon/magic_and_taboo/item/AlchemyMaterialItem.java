package enderdragon.magic_and_taboo.item;

import enderdragon.magic_and_taboo.capability.ElementStorageImpl;
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
import java.util.function.Consumer;

public class AlchemyMaterialItem extends Item {
    public static void makeDisplayStacks(Holder.Reference<AlchemyElement> elements, Consumer<ItemStack> stream) {
        var concentrations = new Reference2FloatOpenHashMap<Element>();
        for (var entry : elements.get().concentrations().object2FloatEntrySet()) {
            concentrations.addTo(entry.getKey().get(), entry.getFloatValue());
        }
        for (var stack : new ItemStack[]{
                new ItemStack(MATItems.ALCHEMY_PASTE.get()),
                new ItemStack(MATItems.ALCHEMY_SOLUTION.get()),
                new ItemStack(MATItems.ALCHEMY_POWDER.get())
        }) {
            var storage = CapabilityUtil.getCapability(stack, MATCapabilities.ELEMENT_STORAGE);
            if (storage == null) continue;
            // 理论上可以省一次拷贝，但是丑
            storage.setConcentrations(new Reference2FloatOpenHashMap<>(concentrations));
            stream.accept(stack);
        }
    }

    public static void fillDisplayStacks(CreativeModeTab.ItemDisplayParameters context, CreativeModeTab.Output entries) {
        context.holders().lookupOrThrow(AlchemyElement.RESOURCE_KEY)
                .listElements()
                .mapMulti(AlchemyMaterialItem::makeDisplayStacks)
                .forEach(entries::accept);
    }

    public AlchemyMaterialItem(Properties props) {
        super(props);
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
        var elements = storage.getConcentrations();
        if (elements.size() < 10) {
            for (var entry : elements.reference2FloatEntrySet()) {
                tooltips.add(Component.translatable("tooltip.magic_and_taboo.alchemy_material.suspected", entry.getKey().getDisplayName()));
            }
        } else {
            tooltips.add(Component.translatable("tooltip.magic_and_taboo.alchemy_material.unidentifiable"));
        }

    }
}
