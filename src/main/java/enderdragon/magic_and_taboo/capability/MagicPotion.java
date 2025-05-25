package enderdragon.magic_and_taboo.capability;

import enderdragon.magic_and_taboo.registry.Element;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatMaps;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

@AutoRegisterCapability
public interface MagicPotion {

    Object2FloatMap<Element> getElements();

    /// @return {@code null} if the type is {@link net.minecraftforge.common.ForgeMod#EMPTY_TYPE}
    @Nullable FluidType getSolvent();

    void setContent(@Nullable FluidType solvent, Object2FloatMap<Element> elements);

    boolean isFatal();

    List<MobEffectInstance> getEffects();

    MagicPotion EMPTY = new MagicPotion() {

        @Override
        public Object2FloatMap<Element> getElements() {
            return Object2FloatMaps.emptyMap();
        }

        @Override
        public void setContent(@Nullable FluidType solvent, Object2FloatMap<Element> elements) {}

        @Override
        public @Nullable FluidType getSolvent() {
            return null;
        }

        @Override
        public boolean isFatal() {
            return false;
        }

        @Override
        public List<MobEffectInstance> getEffects() {
            return Collections.emptyList();
        }
    };
}
