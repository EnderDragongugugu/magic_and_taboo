package enderdragon.magicandtaboo.init;

import enderdragon.magicandtaboo.MagicAndTabooMod;
import enderdragon.magicandtaboo.item.BloodBottle;
import enderdragon.magicandtaboo.item.UnknownSwordItem;
import enderdragon.magicandtaboo.item.equipment.SacrificialDagger;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static enderdragon.magicandtaboo.init.MATItemGroups.BLOCKS;
import static enderdragon.magicandtaboo.init.MATItemGroups.ITEMS;

public class MATItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, MagicAndTabooMod.MOD_ID);
    public static final RegistryObject<UnknownSwordItem> UNKNOWN_SWORD = ITEMS.register(REGISTRY, "unknown_sword", () ->
            new UnknownSwordItem(Tiers.NETHERITE, 20, -2.4F, new Properties())
    );
    public static final RegistryObject<BlockItem> MERCURY_ORE = BLOCKS.register(REGISTRY, "mercury_ore", () ->
            new BlockItem(MATBlocks.MERCURY_ORE.get(), new Properties())
    );
    public static final RegistryObject<SacrificialDagger> SACRIFICIAL_DAGGER = ITEMS.register(REGISTRY, "sacrificial_dagger", () ->
            new SacrificialDagger(Tiers.IRON, 1, 1, new Properties())
    );
    public static final RegistryObject<BloodBottle> BLOOD_BOTTLE = ITEMS.register(REGISTRY, "blood_bottle", () ->
            new BloodBottle(new Properties().stacksTo(1))
    );
    public static final RegistryObject<BlockItem> FIR_PLANKS = BLOCKS.register(REGISTRY, "fir_planks", () ->
            new BlockItem(MATBlocks.FIR_PLANKS.get(), new Properties())
    );
    public static final RegistryObject<BlockItem> FIR_SAPLING = BLOCKS.register(REGISTRY, "fir_sapling", () ->
            new BlockItem(MATBlocks.FIR_SAPLING.get(), new Properties())
    );
    public static final RegistryObject<BlockItem> FIR_LOG = BLOCKS.register(REGISTRY, "fir_log", () ->
            new BlockItem(MATBlocks.FIR_LOG.get(), new Properties())
    );
    public static final RegistryObject<BlockItem> STRIPPED_FIR_LOG = BLOCKS.register(REGISTRY, "stripped_fir_log", () ->
            new BlockItem(MATBlocks.STRIPPED_FIR_LOG.get(), new Properties())
    );
    public static final RegistryObject<BlockItem> FIR_WOOD = BLOCKS.register(REGISTRY, "fir_wood", () ->
            new BlockItem(MATBlocks.FIR_WOOD.get(), new Properties())
    );
    public static final RegistryObject<BlockItem> STRIPPED_FIR_WOOD = BLOCKS.register(REGISTRY, "stripped_fir_wood", () ->
            new BlockItem(MATBlocks.STRIPPED_FIR_WOOD.get(), new Properties())
    );
    public static final RegistryObject<BlockItem> FIR_LEAVES = BLOCKS.register(REGISTRY, "fir_leaves", () ->
            new BlockItem(MATBlocks.FIR_LEAVES.get(), new Properties())
    );
    public static final RegistryObject<BlockItem> FIR_SIGN = BLOCKS.register(REGISTRY, "fir_sign", () ->
            new BlockItem(MATBlocks.FIR_SIGN.get(), new Properties())
    );
    public static final RegistryObject<BlockItem> FIR_HANGING_SIGN = BLOCKS.register(REGISTRY, "fir_hanging_sing", () ->
            new BlockItem(MATBlocks.FIR_HANGING_SIGN.get(), new Properties())
    );
    public static final RegistryObject<BlockItem> FIR_PRESSURE_PLATE = BLOCKS.register(REGISTRY, "fir_pressure_plate", () ->
            new BlockItem(MATBlocks.FIR_PRESSURE_PLATE.get(), new Properties())
    );
    public static final RegistryObject<BlockItem> FIR_TRAPDOOR = BLOCKS.register(REGISTRY, "fir_trapdoor", () ->
            new BlockItem(MATBlocks.FIR_TRAPDOOR.get(), new Properties())
    );
    public static final RegistryObject<BlockItem> FIR_STAIRS = BLOCKS.register(REGISTRY, "fir_stairs", () ->
            new BlockItem(MATBlocks.FIR_STAIRS.get(), new Properties())
    );
    public static final RegistryObject<BlockItem> FIR_BUTTON = BLOCKS.register(REGISTRY, "fir_button", () ->
            new BlockItem(MATBlocks.FIR_BUTTON.get(), new Properties())
    );
    public static final RegistryObject<BlockItem> FIR_SLAB = BLOCKS.register(REGISTRY, "fir_slab", () ->
            new BlockItem(MATBlocks.FIR_SLAB.get(), new Properties())
    );
    public static final RegistryObject<BlockItem> FIR_FENCE_GATE = BLOCKS.register(REGISTRY, "fir_fence_gate", () ->
            new BlockItem(MATBlocks.FIR_FENCE_GATE.get(), new Properties())
    );
    public static final RegistryObject<BlockItem> FIR_FENCE = BLOCKS.register(REGISTRY, "fir_fence", () ->
            new BlockItem(MATBlocks.FIR_FENCE.get(), new Properties())
    );
    public static final RegistryObject<BlockItem> FIR_DOOR = BLOCKS.register(REGISTRY, "fir_door", () ->
            new BlockItem(MATBlocks.FIR_DOOR.get(), new Properties())
    );
}
