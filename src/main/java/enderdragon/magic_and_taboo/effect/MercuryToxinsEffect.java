package enderdragon.magic_and_taboo.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class MercuryToxinsEffect extends MobEffect {
    public MercuryToxinsEffect() {
        super(MobEffectCategory.BENEFICIAL, 114514);
    }

    @Override
    public void applyEffectTick(LivingEntity host, int amplifier) {
        host.hurt(host.damageSources().magic(), 1F + amplifier * 0.75F);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration % 20 == 0;
    }
}
