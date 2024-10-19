package enderdragon.magic_and_taboo.init;

import enderdragon.magic_and_taboo.MagicAndTabooMod;
import enderdragon.magic_and_taboo.util.ItemGroup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.registries.DeferredRegister;

public class MATItemGroups {
    public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MagicAndTabooMod.MOD_ID);
    public static ItemGroup ITEMS = new ItemGroup(REGISTRY, "items", "itemGroup.magic_and_taboo.items", MATBlocks.MERCURY_ORE);
    public static ItemGroup BLOCKS = new ItemGroup(REGISTRY, "blocks", "itemGroup.magic_and_taboo.blocks", MATBlocks.MERCURY_ORE);
}
