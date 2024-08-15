package enderdragon.magicandtaboo.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class BloodBottle extends Item {
    char[] colorList = {
            2,
            3,
            4,
            5,
            6,
            7,
            8,
            9,
            'A',
            'B',
            'C',
            'D',
            'E',
            'F'
    };

    public BloodBottle(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean isBarVisible(ItemStack pStack) {
        CompoundTag data = pStack.getTag();
        if (data != null && data.contains("blood_bottle_data", 10)) {
            return true;
        }
        return false;
    }

    @Override
    public int getBarWidth(ItemStack pStack) {
        return width(pStack);
    }

    @Override
    public int getBarColor(ItemStack pStack) {
        return (width(pStack) - 15 * -1) << 20;
    }

    public int width(ItemStack itemStack) {
        CompoundTag data = itemStack.getTag();
        if (data != null && data.contains("blood_bottle_data", 10)) {
            CompoundTag bloodBottleData = data.getCompound("blood_bottle_data");
            return Math.round(13.0F - (2400.0F - (float) bloodBottleData.getInt("pureness")) * 13.0F / 2400.0F);
        }
        return 13;
    }
}
