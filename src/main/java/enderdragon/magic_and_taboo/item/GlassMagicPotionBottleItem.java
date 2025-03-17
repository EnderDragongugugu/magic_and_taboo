package enderdragon.magic_and_taboo.item;

import enderdragon.magic_and_taboo.util.IMagicPotionBottle;
import net.minecraft.world.item.Item;

public class GlassMagicPotionBottleItem extends Item implements IMagicPotionBottle {
    public final Item filled;

    public GlassMagicPotionBottleItem(Properties props, Item filled) {
        super(props);
        this.filled = filled;
    }

    @Override
    public Item getFilled() {
        return this.filled;
    }
}
