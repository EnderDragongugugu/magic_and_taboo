package enderdragon.magicandtaboo.event;

import enderdragon.magicandtaboo.MagicAndTabooMod;
import enderdragon.magicandtaboo.effect.MATEffect;
import enderdragon.magicandtaboo.enchants.MATEnchants;
import enderdragon.magicandtaboo.util.ItemUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MagicAndTabooMod.MODID)
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
