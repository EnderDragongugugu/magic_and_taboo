package enderdragon.magic_and_taboo.solvent;

import enderdragon.magic_and_taboo.registry.Element;
import enderdragon.magic_and_taboo.util.MagicPotionSolvent;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.Reference2FloatMap;
import net.minecraft.world.effect.MobEffectInstance;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PlantExtractSolvent implements MagicPotionSolvent {
    @Override
    public int getColor() {
        return 0x044923;
    }

    @Override
    public List<MobEffectInstance> getEffects(@NotNull Reference2FloatMap<Element> elements, float timeFactor, int baseLevel) {
        var effects = new ObjectArrayList<MobEffectInstance>(elements.size());
        for (var entry : elements.reference2FloatEntrySet()) {
            var element = entry.getKey();
            if (element.concentration().min() <= entry.getFloatValue()) {
                var effect = element.resolveEffect(entry.getFloatValue(), timeFactor, baseLevel);
                effects.add(new MobEffectInstance(
                        element.effect(),
                        15 * 20,
                        Math.round(((float) effect.getDuration() / element.maxTime()) * element.maxLevel())
                ));
            }
        }
        return effects;
    }
}
