package enderdragon.magicandtaboo.item;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

public class MATSignItem extends SignItem {
    public final int burnTime;

    public MATSignItem(
            RegistryObject<? extends Block> standing,
            RegistryObject<? extends Block> wall,
            int burnTime,
            Properties props
    ) {
        super(props, standing.get(), wall.get(), Direction.DOWN);
        this.burnTime = burnTime;
    }

    @Override
    public int getBurnTime(ItemStack stack, @Nullable RecipeType<?> type) {
        return this.burnTime;
    }
}
