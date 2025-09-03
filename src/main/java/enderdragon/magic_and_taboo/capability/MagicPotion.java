package enderdragon.magic_and_taboo.capability;

import enderdragon.magic_and_taboo.init.MATCapabilities;
import enderdragon.magic_and_taboo.registry.Element;
import enderdragon.magic_and_taboo.util.CapabilityUtil;
import it.unimi.dsi.fastutil.objects.Reference2FloatMap;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@AutoRegisterCapability
public interface MagicPotion {
    int DEFAULT_COLOR = 0x385DC6;

    Reference2FloatMap<Element> getElements();

    /// @return {@code null} if the type is {@link net.minecraftforge.common.ForgeMod#EMPTY_TYPE}
    @Nullable FluidType getSolvent();

    void setContent(@Nullable FluidType solvent, Reference2FloatMap<Element> elements);

    boolean isFatal();

    List<MobEffectInstance> getEffects();

    default int getColor() {
        return DEFAULT_COLOR;
    }

    static int getLayerColor(ItemStack stack, int layer) {
        if (layer > 0) return -1;
        var potion = CapabilityUtil.getCapability(stack, MATCapabilities.MAGIC_POTION);
        return potion == null ? DEFAULT_COLOR : potion.getColor();
    }
}
