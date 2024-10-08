package enderdragon.magicandtaboo.inventory;

import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class PlaceItemTagSlot extends Slot {
    protected final TagKey<Item> tag;
    public PlaceItemTagSlot(Container pContainer, TagKey<Item> tag, int pSlot, int pX, int pY) {
        super(pContainer, pSlot, pX, pY);
        this.tag = tag;
    }
    public boolean mayPlace(ItemStack pStack) {
        return pStack.is(tag);
    }
}
