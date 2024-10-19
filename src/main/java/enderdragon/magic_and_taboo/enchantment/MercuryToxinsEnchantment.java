package enderdragon.magic_and_taboo.enchantment;

import enderdragon.magic_and_taboo.effect.MATEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class MercuryToxinsEnchantment extends Enchantment {
    public MercuryToxinsEnchantment(Rarity rarity, EnchantmentCategory category, EquipmentSlot[] slots) {
        super(rarity, category, slots);
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public void doPostAttack(LivingEntity attacker, Entity target, int level) {
        if (target instanceof LivingEntity victim) {
            var effect = MATEffect.MERCURY_TOXINS.get();
            if (victim.hasEffect(effect)) {
                victim.hurt(attacker.damageSources().magic(), 2.0F * level);
            } else {
                victim.addEffect(new MobEffectInstance(effect, level * 20 * 6, level - 1));
            }
        }
    }
}
