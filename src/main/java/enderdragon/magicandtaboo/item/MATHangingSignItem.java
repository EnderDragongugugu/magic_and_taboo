package enderdragon.magicandtaboo.item;

import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

public class MATHangingSignItem extends HangingSignItem {
    public final int burnTime;

    public MATHangingSignItem(
            RegistryObject<? extends Block> standing,
            RegistryObject<? extends Block> wall,
            int burnTime,
            Properties props
    ) {
        super(standing.get(), wall.get(), props);
        this.burnTime = burnTime;
    }

    @Override
    public int getBurnTime(ItemStack stack, @Nullable RecipeType<?> type) {
        return this.burnTime;
    }
}
