package enderdragon.magic_and_taboo.capability;

import enderdragon.magic_and_taboo.init.MATCapabilities;
import enderdragon.magic_and_taboo.registry.Element;
import enderdragon.magic_and_taboo.util.CapabilityUtil;
import it.unimi.dsi.fastutil.objects.Reference2FloatMap;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@AutoRegisterCapability
public interface ElementHolder {
    int DEFAULT_COLOR = 0x385DC6;
//    int DEFAULT_COLOR = 0xFFFFFF;
    FluidType DEFAULT_FLUID_TYPE = ForgeMod.WATER_TYPE.get();

    Reference2FloatMap<Element> getElements();
    void setElement(Reference2FloatMap<Element> elements);

    Element getMaxElement();
    float getAmount(Element element);

    boolean isFatal();

    List<MobEffectInstance> getEffects();

    int getColor();

    static int getLayerColor(ItemStack stack, int layer) {
        if (layer > 0) return -1;
        var holder = CapabilityUtil.getCapability(stack, MATCapabilities.ELEMENT_HOLDER);
        return holder == null ? DEFAULT_COLOR : holder.getColor();
    }
}


