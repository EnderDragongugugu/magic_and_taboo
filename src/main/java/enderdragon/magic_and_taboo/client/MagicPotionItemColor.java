package enderdragon.magic_and_taboo.client;

import enderdragon.magic_and_taboo.init.MATItems;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.ItemStack;

public class MagicPotionItemColor implements ItemColor {
    @Override
    public int getColor(ItemStack pStack, int pTintIndex) {
        if (pStack.is(MATItems.MAGIC_POTION.get()) && pTintIndex == 1) {
            return 0x114514;

        }
        return 0;
    }
}
