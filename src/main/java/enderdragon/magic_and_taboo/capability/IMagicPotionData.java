package enderdragon.magic_and_taboo.capability;

import enderdragon.magic_and_taboo.registry.Element;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import java.util.Collection;

@AutoRegisterCapability
public interface IMagicPotionData {
    Object2FloatOpenHashMap<Element> getElementMap();

    void setElementMap(Object2FloatOpenHashMap<Element> map);

    Collection<MobEffectInstance> getEffectInstances();

}
