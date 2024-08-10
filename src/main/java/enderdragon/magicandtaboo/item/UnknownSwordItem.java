package enderdragon.magicandtaboo.item;

import enderdragon.magicandtaboo.client.ClientUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;

public class UnknownSwordItem extends SwordItem {
    private static final Item[] POOL = new Item[]{
            Items.NETHERITE_INGOT,
            Items.DIAMOND,
            Items.ENCHANTED_GOLDEN_APPLE,
            Items.ECHO_SHARD,
            Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
            Items.KNOWLEDGE_BOOK,
            Items.NETHERITE_BLOCK,
            Items.CREEPER_HEAD,
            Items.DRAGON_HEAD,
            Items.WITHER_SKELETON_SKULL,
            Items.DRAGON_EGG,
            Items.SNIFFER_EGG,
            Items.TOTEM_OF_UNDYING
    };

    public UnknownSwordItem(Tier tier, int damageModifier, float speedModifier, Properties props) {
        super(tier, damageModifier, speedModifier, props);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return super.isFoil(stack) || (FMLEnvironment.dist.isClient() && ClientUtil.isUsing(stack));
    }

    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity killer && killer.getMainHandItem().getItem() instanceof UnknownSwordItem) {
            var victim = event.getEntity();
            var random = killer.getRandom();
            for (int i = 0, n = 1 + random.nextInt(3), size = POOL.length; i < n; ++i) {
                victim.spawnAtLocation(new ItemStack(POOL[random.nextInt(size)]));
            }
        }
    }
}
