package enderdragon.magicandtaboo.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

/**
 * @deprecated use {@link net.minecraft.world.inventory.ResultSlot} instead
 */
@Deprecated
public class ResultSlot extends Slot {
    public ResultSlot(Container pContainer, int pSlot, int pX, int pY) {
        super(pContainer, pSlot, pX, pY);
    }

    public boolean mayPlace(ItemStack pStack) {
        return false;
    }
}
