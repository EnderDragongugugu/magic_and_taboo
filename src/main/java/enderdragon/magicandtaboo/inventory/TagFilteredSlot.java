package enderdragon.magicandtaboo.inventory;

import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class TagFilteredSlot extends Slot {
    public final TagKey<Item> tag;

    public TagFilteredSlot(Container container, TagKey<Item> tag, int slot, int x, int y) {
        super(container, slot, x, y);
        this.tag = tag;
    }

    public boolean mayPlace(ItemStack stack) {
        return stack.is(tag);
    }
}
