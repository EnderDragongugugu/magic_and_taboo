package enderdragon.magic_and_taboo.effect;

import enderdragon.magic_and_taboo.init.MATEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class HemolysisEffect extends MobEffect {
    public HemolysisEffect() {
        super(MobEffectCategory.HARMFUL, 0x7C5656);
        this.addAttributeModifier(
                Attributes.MAX_HEALTH,
                "9960EFBD-6A15-47B7-B378-766E9825EE69",
                -0.3,
                AttributeModifier.Operation.MULTIPLY_BASE
        );
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration > 0;
    }

    @Override
    public void applyEffectTick(LivingEntity host, int amplifier) {
        if (host.getHealth() > host.getMaxHealth()) {
            host.setHealth(host.getMaxHealth());
        }
    }

    public static void livingHurtEvent(LivingHurtEvent event) {
        if (event.getEntity().hasEffect(MATEffects.HEMOLYSIS.get())) {
            event.setAmount(event.getAmount() * 2);
        }
    }
}
