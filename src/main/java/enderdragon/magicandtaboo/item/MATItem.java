package enderdragon.magicandtaboo.item;

import enderdragon.magicandtaboo.MagicAndTabooMod;
import enderdragon.magicandtaboo.block.MATBlock;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MATItem {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MagicAndTabooMod.MODID);
//    public static final RegistryObject<Item> MERCURY_ORE = ITEMS.register("mercury_ore",()->new TooltipItem(new Item.Properties(),"114514"));
    public static final RegistryObject<Item> MERCURY_ORE = ITEMS.register("mercury_ore",()->new TooltipBlockItem(MATBlock.MERCURY_ORE.get(), new Item.Properties(),"114514"));
}
