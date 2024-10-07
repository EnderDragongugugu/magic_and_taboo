package enderdragon.magicandtaboo.util;

import net.minecraft.world.inventory.DataSlot;

public class DataSlotImpl extends DataSlot {
    private int value;

    @Override
    public int get() {
        return this.value;
    }

    @Override
    public void set(int value) {
        this.value = value;
    }
}
