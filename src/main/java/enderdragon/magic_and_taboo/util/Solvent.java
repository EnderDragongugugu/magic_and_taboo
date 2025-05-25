package enderdragon.magic_and_taboo.util;

import enderdragon.magic_and_taboo.registry.Element;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.effect.MobEffectInstance;

public class Solvent implements IMagicPotionSolvent {
    public int getColor() {
        return 3694022;
    }

    @Override
    public ObjectArrayList<MobEffectInstance> getEffectInstances(Object2FloatMap<Element> elements, float timeFactor, int baseLevel) {
        var effects = new ObjectArrayList<MobEffectInstance>(elements.size());
        for (var entry : elements.object2FloatEntrySet()) {
            var element = entry.getKey();
            if (element.concentration().min() <= entry.getFloatValue()) {
                effects.add(element.getEffect(entry.getFloatValue(), timeFactor, baseLevel));
            }
        }
        return effects;
    }
}
