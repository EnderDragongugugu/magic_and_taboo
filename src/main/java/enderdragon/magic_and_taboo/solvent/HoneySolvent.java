package enderdragon.magic_and_taboo.solvent;

import enderdragon.magic_and_taboo.registry.Element;
import enderdragon.magic_and_taboo.util.MagicPotionSolvent;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.Reference2FloatMap;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;

public class HoneySolvent implements MagicPotionSolvent {
    @Override
    public int getColor() {
        return 0xD28D2B;
    }

    @Override
    public List<MobEffectInstance> getEffects(@NotNull Reference2FloatMap<Element> elements, float timeFactor, int baseLevel) {
        Comparator<Object2FloatMap.Entry<Holder<Element>>> comparator =
                (left, right) -> Float.compare(left.getFloatValue(), right.getFloatValue());
        var effects = new ObjectArrayList<MobEffectInstance>(elements.size());
        for (var entry : elements.reference2FloatEntrySet()) {
            var fusion = entry.getKey()
                    .fusionElementMap()
                    .object2FloatEntrySet()
                    .stream()
                    .max(comparator)
                    .orElse(null);
            if (fusion == null) continue;
            effects.add(fusion.getKey().value().getEffect(entry.getFloatValue(), timeFactor, baseLevel));
        }
        return effects;
    }
}
