package enderdragon.magic_and_taboo.client.renderer;

import enderdragon.magic_and_taboo.capability.MagicPotion;
import enderdragon.magic_and_taboo.registry.Element;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatMaps;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;

public class EnchantedCrucibleInfo implements MagicPotion {
    public boolean changed = true;
    public FluidStack fluid = FluidStack.EMPTY;
    public int fluidColor = 0xFFFFFF;
    public int temperature;
    public @Nullable Component tip;
    public @Nullable FluidType solvent;
    public @Nonnull Object2FloatMap<Element> elements = Object2FloatMaps.emptyMap();

    @Override
    public @NotNull Object2FloatMap<Element> getElements() {
        return this.elements;
    }

    @Override
    public @Nullable FluidType getSolvent() {
        return this.solvent;
    }

    @Override
    public void setContent(@Nullable FluidType solvent, @NotNull Object2FloatMap<Element> elements) {
        this.solvent = solvent;
        this.elements = elements;
    }

    @Override
    public boolean isFatal() {
        return false;
    }

    @Override
    public @NotNull List<MobEffectInstance> getEffects() {
        var effects = new ObjectArrayList<MobEffectInstance>(this.elements.size());
        for (var entry : this.elements.object2FloatEntrySet()) {
            effects.add(entry.getKey().getEffect(entry.getFloatValue(), 1.0F, 0));
        }
        return effects;
    }
}
