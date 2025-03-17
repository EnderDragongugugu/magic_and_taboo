package enderdragon.magic_and_taboo.init;

import enderdragon.magic_and_taboo.MagicAndTabooMod;
import enderdragon.magic_and_taboo.util.ItemGroup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;

public class MATItemGroups {
    public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MagicAndTabooMod.MOD_ID);
    public static final ItemGroup ITEMS = new ItemGroup(REGISTRY, "items", "itemGroup.magic_and_taboo.items", () -> new ItemStack(MATItems.SACRIFICIAL_DAGGER.get()));
    public static final ItemGroup BLOCKS = new ItemGroup(REGISTRY, "blocks", "itemGroup.magic_and_taboo.blocks", () -> new ItemStack(MATBlocks.FIR_LOG.get()));
    public static final ItemGroup POTION = new ItemGroup(REGISTRY, "potion", "itemGroup.magic_and_taboo.potion", () -> new ItemStack(MATBlocks.ENCHANTED_CRUCIBLE.get()));
}
