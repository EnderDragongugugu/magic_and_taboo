package enderdragon.magic_and_taboo.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class StaffItem extends Item {
    public StaffItem(Properties pProperties) {
        super(pProperties);
    }

    public static void setPart(ItemStack stack, String partType, String partId) {
        stack.getOrCreateTag().putString(partType, partId);
    }

    public static String getPart(ItemStack stack, String partType) {
        return stack.hasTag() ? stack.getTag().getString(partType) : "";
    }
}
