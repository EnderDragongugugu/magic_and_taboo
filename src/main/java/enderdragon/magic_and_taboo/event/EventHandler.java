package enderdragon.magic_and_taboo.event;

import enderdragon.magic_and_taboo.MagicAndTabooMod;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MagicAndTabooMod.MOD_ID)
public class EventHandler {
//    @SubscribeEvent
//    public static void playerAttack(LivingHurtEvent event) {
//        LivingEntity hurtEntity = event.getEntity();
//        Entity damageEntity = event.getSource().getEntity();
//        if (damageEntity == null) return;
//        if (damageEntity instanceof LivingEntity livingDamageEntity) {
//            ItemStack handItemStack = ItemUtil.getUsingItemStack((LivingEntity) damageEntity);
//            int level = EnchantmentHelper.getItemEnchantmentLevel(MATEnchants.mercuryToxins.get(), handItemStack);
//            if (level > 0) {
//                MobEffectInstance effect = new MobEffectInstance(MATEffect.mercuryToxins.get(), level * 20 * 6, level - 1);
//                if (!hurtEntity.hasEffect(MATEffect.mercuryToxins.get())) {
//                    hurtEntity.addEffect(effect);
//                } else {
//                    hurtEntity.hurt(livingDamageEntity.damageSources().magic(), 2.0F * level);
//                }
//            }
//        }
//    }
}
