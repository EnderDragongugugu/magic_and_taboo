package enderdragon.magicandtaboo.item;

import enderdragon.magicandtaboo.MagicAndTabooMod;
import enderdragon.magicandtaboo.block.MATBlocks;
import enderdragon.magicandtaboo.item.equipment.SacrificialDagger;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MATItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MagicAndTabooMod.MOD_ID);
<<<<<<< Updated upstream
    //    public static final RegistryObject<Item> MERCURY_ORE = ITEMS.register("mercury_ore",()->new TooltipItem(new Item.Properties(),"114514"));
    public static final RegistryObject<Item> MERCURY_ORE = ITEMS.register("mercury_ore", () -> new TooltipBlockItem(MATBlocks.MERCURY_ORE.get(), new Item.Properties(), "114514"));
    public static final RegistryObject<Item> UNKNOWN_SWORD = ITEMS.register("unknown_sword", () -> new UnknownSwordItem(Tiers.NETHERITE, 20, -2.4F, new Item.Properties()));
=======
    public static final RegistryObject<Item> MERCURY_ORE = ITEMS.register("mercury_ore", () -> new BlockItem(MATBlocks.MERCURY_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Item> SACRIFICIAL_DAGGER = ITEMS.register("sacrificial_dagger", () -> new SacrificialDagger(new Item.Properties()));

>>>>>>> Stashed changes
}
