package enderdragon.magicandtaboo.item.equipment;

import enderdragon.magicandtaboo.util.ContainerUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class SacrificialDagger extends SwordItem {
    private static final Logger LOGGER = LogManager.getLogger();

    public SacrificialDagger(Tier tier, int damageModifier, float speedModifier, Properties props) {
        super(tier, damageModifier, speedModifier, props);
    }

//    @Override
//    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
//        LogManager.getLogger().debug("test");
//        return super.use(pLevel, pPlayer, pUsedHand);
//    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof Player player) {
            int slot = ContainerUtil.find(player.getInventory(), Items.GLASS_BOTTLE);
            player.displayClientMessage(Component.literal(Integer.toString(slot)), false);
            LOGGER.info("slot: {}", slot);
        }
        return super.hurtEnemy(stack, target, attacker);
    }
}
