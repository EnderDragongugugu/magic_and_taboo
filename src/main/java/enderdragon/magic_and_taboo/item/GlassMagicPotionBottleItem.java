package enderdragon.magic_and_taboo.item;

import enderdragon.magic_and_taboo.util.IMagicPotionBottleType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class GlassMagicPotionBottleItem extends Item implements IMagicPotionBottleType {
    Item item;

    public GlassMagicPotionBottleItem(Properties pProperties, Item item) {
        super(pProperties);
        this.item = item;
    }

    @Override
    public ItemStack getPotionBottle() {
        return new ItemStack(item);
    }
}
