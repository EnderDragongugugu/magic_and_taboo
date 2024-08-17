package enderdragon.magicandtaboo.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

public class FuelBlockItem extends BlockItem {
    public final int burnTime;

    public FuelBlockItem(RegistryObject<? extends Block> block, int burnTime, Properties props) {
        super(block.get(), props);
        this.burnTime = burnTime;
    }

    @Override
    public int getBurnTime(ItemStack stack, @Nullable RecipeType<?> type) {
        return this.burnTime;
    }
}
