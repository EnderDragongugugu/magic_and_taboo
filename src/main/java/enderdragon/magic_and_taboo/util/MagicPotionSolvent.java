package enderdragon.magic_and_taboo.util;

import enderdragon.magic_and_taboo.registry.Element;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.List;

<<<<<<< Updated upstream
public interface MagicPotionSolvent {
    List<MobEffectInstance> getEffects(Object2FloatMap<Element> elements, float timeFactor, int baseLevel);
=======
<<<<<<< HEAD:src/main/java/enderdragon/magic_and_taboo/util/IMagicPotionSolvent.java
public interface IMagicPotionSolvent {
    List<MobEffectInstance> getEffectInstances(Object2FloatMap<Element> elements, float timeFactor, int baseLevel);

    int getColor();
=======
public interface MagicPotionSolvent {
    List<MobEffectInstance> getEffects(Object2FloatMap<Element> elements, float timeFactor, int baseLevel);
>>>>>>> b8efb8187c805fb72d3179702d879cf804ddbc8b:src/main/java/enderdragon/magic_and_taboo/util/MagicPotionSolvent.java
>>>>>>> Stashed changes
}
