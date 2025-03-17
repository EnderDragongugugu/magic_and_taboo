package enderdragon.magic_and_taboo.client.render;

import enderdragon.magic_and_taboo.capability.IMagicPotion;
import enderdragon.magic_and_taboo.registry.Element;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatMaps;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EnchantedCrucibleInfo implements IMagicPotion {
    public boolean changed = true;
    public FluidStack fluid = FluidStack.EMPTY;
    public int fluidColor = 0xFFFFFF;
    public int temperature;
    public Object2FloatMap<Element> elements = Object2FloatMaps.emptyMap();

    @Override
    public @NotNull Object2FloatMap<Element> getElements() {
        return this.elements;
    }

    @Override
    public void setElements(@NotNull Object2FloatMap<Element> elements) {
        this.elements = elements;
    }

    @Override
    public boolean isFatal() {
        return false;
    }

    @Override
    public @NotNull List<MobEffectInstance> getEffectInstances() {
        var effects = new ObjectArrayList<MobEffectInstance>(this.elements.size());
        for (var entry : this.elements.object2FloatEntrySet()) {
            effects.add(entry.getKey().getEffect(entry.getFloatValue(), 1.0F, 0));
        }
        return effects;
    }
}
