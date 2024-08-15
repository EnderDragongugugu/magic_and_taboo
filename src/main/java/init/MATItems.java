package init;

import enderdragon.magicandtaboo.MagicAndTabooMod;
import enderdragon.magicandtaboo.item.BloodBottle;
import enderdragon.magicandtaboo.item.UnknownSwordItem;
import enderdragon.magicandtaboo.item.equipment.SacrificialDagger;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static enderdragon.magicandtaboo.item.MATItemGroups.BLOCKS;
import static enderdragon.magicandtaboo.item.MATItemGroups.ITEMS;

public class MATItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, MagicAndTabooMod.MOD_ID);
    public static final RegistryObject<Item> UNKNOWN_SWORD = ITEMS.register(REGISTRY, "unknown_sword", () ->
            new UnknownSwordItem(Tiers.NETHERITE, 20, -2.4F, new Item.Properties())
    );
    public static final RegistryObject<Item> MERCURY_ORE = BLOCKS.register(REGISTRY, "mercury_ore", () ->
            new BlockItem(MATBlocks.MERCURY_ORE.get(), new Item.Properties())
    );
    public static final RegistryObject<Item> SACRIFICIAL_DAGGER = ITEMS.register(REGISTRY, "sacrificial_dagger", () ->
            new SacrificialDagger(Tiers.IRON, 1, 1, new Item.Properties())
    );
    public static final RegistryObject<Item> BLOOD_BOTTLE = ITEMS.register(REGISTRY,"blood_bottle",()->
            new BloodBottle(new Item.Properties()
                    .stacksTo(1)));
}
