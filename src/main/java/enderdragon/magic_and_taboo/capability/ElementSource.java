package enderdragon.magic_and_taboo.capability;

import enderdragon.magic_and_taboo.init.MATCapabilities;
import enderdragon.magic_and_taboo.registry.Element;
import enderdragon.magic_and_taboo.util.CapabilityUtil;
import it.unimi.dsi.fastutil.objects.Reference2FloatMap;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public interface ElementSource {
    static int getLayerColor(ItemStack stack, int layer) {
        if (layer > 0) return -1;
        var potion = CapabilityUtil.getCapability(stack, MATCapabilities.ELEMENT_SOURCE);
        return potion == null ? DEFAULT_COLOR : potion.getColor();
    }

    int DEFAULT_COLOR = 0x385DC6;

    Reference2FloatMap<Element> getConcentrations();

    float getConcentration(Element element);

    default int getColor() {
        return DEFAULT_COLOR;
    }
}
