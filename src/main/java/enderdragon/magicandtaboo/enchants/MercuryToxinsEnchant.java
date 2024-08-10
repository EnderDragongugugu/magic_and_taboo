package enderdragon.magicandtaboo.enchants;


import enderdragon.magicandtaboo.MagicAndTabooMod;
import enderdragon.magicandtaboo.effect.MATEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;


public class MercuryToxinsEnchant extends Enchantment {
    protected MercuryToxinsEnchant(Rarity p_44676_, EnchantmentCategory p_44677_, EquipmentSlot[] p_44678_) {
        super(p_44676_, p_44677_, p_44678_);
    }

    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public void doPostAttack(LivingEntity p_44686_, Entity p_44687_, int p_44688_) {
        if (p_44687_ instanceof LivingEntity hurtEntity) {
            MobEffectInstance effect = new MobEffectInstance(MATEffect.mercuryToxins.get(), p_44688_ * 20 * 6, p_44688_ - 1);
            if (!hurtEntity.hasEffect(MATEffect.mercuryToxins.get())) {
                hurtEntity.addEffect(effect);
            } else {
                hurtEntity.hurt(p_44686_.damageSources().magic(), 2.0F * p_44688_);
            }
        }
    }
}
