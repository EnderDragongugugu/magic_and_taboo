package enderdragon.magic_and_taboo.capability;

import enderdragon.magic_and_taboo.registry.Element;
import it.unimi.dsi.fastutil.objects.Reference2FloatMap;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@AutoRegisterCapability
public interface MagicPotion extends ElementSource {
    /// @return {@code null} if the type is {@link net.minecraftforge.common.ForgeMod#EMPTY_TYPE}
    @Nullable FluidType getSolvent();

    void setContent(@Nullable FluidType solvent, Reference2FloatMap<Element> elements);

    List<MobEffectInstance> getEffects();

    default boolean isFatal() {
        for (var entry : this.getConcentrations().reference2FloatEntrySet()) {
            if (entry.getFloatValue() >= entry.getKey().concentration().max()) {
                return true;
            }
        }
        return false;
    }
}
