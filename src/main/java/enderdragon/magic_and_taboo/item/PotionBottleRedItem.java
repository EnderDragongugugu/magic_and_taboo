package enderdragon.magic_and_taboo.item;

import enderdragon.magic_and_taboo.capability.MagicPotion;
import enderdragon.magic_and_taboo.init.MATItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;

public class PotionBottleRedItem extends MagicPotionItem {
    public PotionBottleRedItem(Properties props) {
        super(props);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        return consumeStack(stack, entity, MATItems.GLASS_POTION_BOTTLE_RED);
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new MagicPotion(2.0F, -1);
    }
}
