package enderdragon.magic_and_taboo.effect;

import enderdragon.magic_and_taboo.init.MATEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.UUID;

public class HemolysisEffect extends MobEffect {
    UUID uuid = UUID.fromString("9960efbd-6a15-47b7-b378-766e9825ee69");

    public HemolysisEffect() {
        super(MobEffectCategory.HARMFUL, 0x7c5656);
    }

    @Override
    public void addAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
        AttributeInstance health = pLivingEntity.getAttribute(Attributes.MAX_HEALTH);
        if (health != null && pLivingEntity.hasEffect(this)) {
            var modifier = new AttributeModifier(uuid, "hemolysis", -health.getBaseValue() * 0.3D, AttributeModifier.Operation.ADDITION);
            health.addPermanentModifier(modifier);
        }
        super.addAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
    }

    @Override
    public void removeAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
        AttributeInstance health = pLivingEntity.getAttribute(Attributes.MAX_HEALTH);
        if (health != null) {
            health.removeModifier(uuid);
        }
        super.removeAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
    }

    public static void livingHurtEvent(LivingHurtEvent event) {
        var entity = event.getEntity();
        if (entity.hasEffect(MATEffects.HEMOLYSIS.get())) {
            event.setAmount(event.getAmount() * 2);
        }
    }
}
