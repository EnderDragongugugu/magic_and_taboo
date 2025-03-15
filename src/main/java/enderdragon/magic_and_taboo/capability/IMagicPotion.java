package enderdragon.magic_and_taboo.capability;

import enderdragon.magic_and_taboo.registry.Element;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatMaps;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import java.util.List;

@AutoRegisterCapability
public interface IMagicPotion {
    Object2FloatMap<Element> getElements();

    void setElements(Object2FloatMap<Element> elements);


    List<MobEffectInstance> getEffectInstances();

    IMagicPotion EMPTY = new IMagicPotion() {
        @Override
        public Object2FloatMap<Element> getElements() {
            return Object2FloatMaps.emptyMap();
        }

        @Override
        public void setElements(Object2FloatMap<Element> elements) {}

        @Override
        public List<MobEffectInstance> getEffectInstances() {
            return List.of();
        }
    };
}
