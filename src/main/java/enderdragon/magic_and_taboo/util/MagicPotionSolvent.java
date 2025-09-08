package enderdragon.magic_and_taboo.util;

import enderdragon.magic_and_taboo.registry.Element;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.Reference2FloatMap;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.List;

public interface MagicPotionSolvent {
    int getColor();

    default List<MobEffectInstance> getEffects(Reference2FloatMap<Element> elements, float timeFactor, int baseLevel) {
        var effects = new ObjectArrayList<MobEffectInstance>(elements.size());
        for (var entry : elements.reference2FloatEntrySet()) {
            var element = entry.getKey();
            if (element.concentration().min() <= entry.getFloatValue()) {
                effects.add(element.resolveEffect(entry.getFloatValue(), timeFactor, baseLevel));
            }
        }
        return effects;
    }
}
