package enderdragon.magic_and_taboo.init;

import enderdragon.magic_and_taboo.MagicAndTabooMod;
import enderdragon.magic_and_taboo.item.AlchemyElementItem;
import enderdragon.magic_and_taboo.util.ItemGroup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;

public class MATItemGroups {
    public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MagicAndTabooMod.MOD_ID);
    public static final ItemGroup ITEMS = ItemGroup.of();
    public static final ItemGroup BLOCKS = ItemGroup.of();
    public static final ItemGroup POTION = ItemGroup.of(AlchemyElementItem::fillDisplayStacks);

    static {
        ITEMS.register(REGISTRY, "items", "itemGroup.magic_and_taboo.items", () -> new ItemStack(MATItems.SACRIFICIAL_DAGGER.get()));
        BLOCKS.register(REGISTRY, "blocks", "itemGroup.magic_and_taboo.blocks", () -> new ItemStack(MATItems.FIR_LOG.get()));
        POTION.register(REGISTRY, "potion", "itemGroup.magic_and_taboo.potion", () -> new ItemStack(MATItems.ENCHANTED_CRUCIBLE.get()));
    }
}
