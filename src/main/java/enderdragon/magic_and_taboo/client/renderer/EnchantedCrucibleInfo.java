package enderdragon.magic_and_taboo.client.renderer;

import enderdragon.magic_and_taboo.capability.MagicPotion;
import enderdragon.magic_and_taboo.registry.Element;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.Reference2FloatMap;
import it.unimi.dsi.fastutil.objects.Reference2FloatMaps;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
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
    public @Nonnull Reference2FloatMap<Element> elements = Reference2FloatMaps.emptyMap();

    @Override
    public Reference2FloatMap<Element> getElements() {
        return this.elements;
    }

    @Override
    public @Nullable FluidType getSolvent() {
        return this.solvent;
    }

    @Override
    public void setContent(@Nullable FluidType solvent, Reference2FloatMap<Element> elements) {
        this.solvent = solvent;
        this.elements = elements;
    }

    @Override
    public boolean isFatal() {
        return false;
    }

    @Override
    public List<MobEffectInstance> getEffects() {
        var effects = new ObjectArrayList<MobEffectInstance>(this.elements.size());
        for (var entry : this.elements.reference2FloatEntrySet()) {
            effects.add(entry.getKey().getEffect(entry.getFloatValue(), 1.0F, 0));
        }
        return effects;
    }
}
