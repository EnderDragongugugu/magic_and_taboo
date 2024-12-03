package enderdragon.magic_and_taboo.util;

import net.minecraft.world.inventory.DataSlot;

public final class DataSlotImpl extends DataSlot {
    private int value;

    @Override
    public int get() {
        return this.value;
    }

    @Override
    public void set(int value) {
        this.value = value;
    }

    public int increase() {
        return ++this.value;
    }
}
