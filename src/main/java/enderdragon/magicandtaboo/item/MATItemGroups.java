package enderdragon.magicandtaboo.item;

import enderdragon.magicandtaboo.MagicAndTabooMod;
import enderdragon.magicandtaboo.block.MATBlocks;
import enderdragon.magicandtaboo.util.ItemGroup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.registries.DeferredRegister;

public class MATItemGroups {
    public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MagicAndTabooMod.MOD_ID);
    public static ItemGroup ITEMS = new ItemGroup(REGISTRY, "items", "itemGroup.magicandtaboo.items", MATBlocks.MERCURY_ORE);
    public static ItemGroup BLOCKS = new ItemGroup(REGISTRY, "blocks", "itemGroup.magicandtaboo.blocks", MATBlocks.MERCURY_ORE);
}
