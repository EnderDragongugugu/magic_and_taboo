package enderdragon.magic_and_taboo.solvent;

import enderdragon.magic_and_taboo.registry.Element;
import enderdragon.magic_and_taboo.util.MagicPotionSolvent;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.List;
import java.util.Map;

public class HoneySolvent implements MagicPotionSolvent {
    @Override
    public int getColor() {
        return 0xD28D2B;
    }

    @Override
    public List<MobEffectInstance> getEffects(Object2FloatMap<Element> elements, float timeFactor, int baseLevel) {
        var effects = new ObjectArrayList<MobEffectInstance>(elements.size());
        for (var entry : elements.object2FloatEntrySet()) {
            var element = entry.getKey();
            var max = element.fusionElementMap().entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey);
            max.ifPresent(elementHolder -> {
                var e = elementHolder.get();
                effects.add(e.getEffect(entry.getFloatValue(), timeFactor, baseLevel));
            });
        }
        return effects;
    }
}
