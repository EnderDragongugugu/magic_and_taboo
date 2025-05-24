package enderdragon.magic_and_taboo.capability;

import enderdragon.magic_and_taboo.registry.Element;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatMaps;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import java.util.Collections;
import java.util.List;

@AutoRegisterCapability
public interface MagicPotion {

    Object2FloatMap<Element> getElements();

    void setElements(Object2FloatMap<Element> elements);

    void setSolventType(String type);

    String getSolventType();


    boolean isFatal();

    List<MobEffectInstance> getEffectInstances();

    MagicPotion EMPTY = new MagicPotion() {

        @Override
        public Object2FloatMap<Element> getElements() {
            return Object2FloatMaps.emptyMap();
        }

        @Override
        public void setElements(Object2FloatMap<Element> elements) {
        }

        @Override
        public void setSolventType(String type) {

        }

        @Override
        public String getSolventType() {
            return null;
        }

        @Override
        public boolean isFatal() {
            return false;
        }

        @Override
        public List<MobEffectInstance> getEffectInstances() {
            return Collections.emptyList();
        }
    };
}
