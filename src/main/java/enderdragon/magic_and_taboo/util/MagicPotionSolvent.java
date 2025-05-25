package enderdragon.magic_and_taboo.util;

import enderdragon.magic_and_taboo.registry.Element;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.List;

public interface MagicPotionSolvent {
    List<MobEffectInstance> getEffects(Object2FloatMap<Element> elements, float timeFactor, int baseLevel);
}
