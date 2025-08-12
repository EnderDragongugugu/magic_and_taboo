package enderdragon.magic_and_taboo.init;

import enderdragon.magic_and_taboo.MagicAndTabooMod;
import enderdragon.magic_and_taboo.item.*;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.Item.Properties;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static enderdragon.magic_and_taboo.init.MATItemGroups.*;

public class MATItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, MagicAndTabooMod.MOD_ID);
    public static final RegistryObject<FuelBlockItem> FIR_LOG = BLOCKS.register(REGISTRY, "fir_log", () ->
            new FuelBlockItem(MATBlocks.FIR_LOG, 300, new Properties())
    );
    public static final RegistryObject<FuelBlockItem> FIR_WOOD = BLOCKS.register(REGISTRY, "fir_wood", () ->
            new FuelBlockItem(MATBlocks.FIR_WOOD, 300, new Properties())
    );
    public static final RegistryObject<FuelBlockItem> STRIPPED_FIR_LOG = BLOCKS.register(REGISTRY, "stripped_fir_log", () ->
            new FuelBlockItem(MATBlocks.STRIPPED_FIR_LOG, 300, new Properties())
    );
    public static final RegistryObject<FuelBlockItem> STRIPPED_FIR_WOOD = BLOCKS.register(REGISTRY, "stripped_fir_wood", () ->
            new FuelBlockItem(MATBlocks.STRIPPED_FIR_WOOD, 300, new Properties())
    );
    public static final RegistryObject<FuelBlockItem> FIR_PLANKS = BLOCKS.register(REGISTRY, "fir_planks", () ->
            new FuelBlockItem(MATBlocks.FIR_PLANKS, 300, new Properties())
    );
    public static final RegistryObject<FuelBlockItem> FIR_STAIRS = BLOCKS.register(REGISTRY, "fir_stairs", () ->
            new FuelBlockItem(MATBlocks.FIR_STAIRS, 300, new Properties())
    );
    public static final RegistryObject<FuelBlockItem> FIR_SLAB = BLOCKS.register(REGISTRY, "fir_slab", () ->
            new FuelBlockItem(MATBlocks.FIR_SLAB, 150, new Properties())
    );
    public static final RegistryObject<FuelBlockItem> FIR_FENCE = BLOCKS.register(REGISTRY, "fir_fence", () ->
            new FuelBlockItem(MATBlocks.FIR_FENCE, 300, new Properties())
    );
    public static final RegistryObject<FuelBlockItem> FIR_FENCE_GATE = BLOCKS.register(REGISTRY, "fir_fence_gate", () ->
            new FuelBlockItem(MATBlocks.FIR_FENCE_GATE, 300, new Properties())
    );
    public static final RegistryObject<FuelBlockItem> FIR_DOOR = BLOCKS.register(REGISTRY, "fir_door", () ->
            new FuelBlockItem(MATBlocks.FIR_DOOR, 200, new Properties())
    );
    public static final RegistryObject<FuelBlockItem> FIR_TRAPDOOR = BLOCKS.register(REGISTRY, "fir_trapdoor", () ->
            new FuelBlockItem(MATBlocks.FIR_TRAPDOOR, 300, new Properties())
    );
    public static final RegistryObject<FuelBlockItem> FIR_PRESSURE_PLATE = BLOCKS.register(REGISTRY, "fir_pressure_plate", () ->
            new FuelBlockItem(MATBlocks.FIR_PRESSURE_PLATE, 300, new Properties())
    );
    public static final RegistryObject<FuelBlockItem> FIR_BUTTON = BLOCKS.register(REGISTRY, "fir_button", () ->
            new FuelBlockItem(MATBlocks.FIR_BUTTON, 100, new Properties())
    );
    public static final RegistryObject<BlockItem> GILDED_MARBLE = BLOCKS.register(REGISTRY, "gilded_marble", () ->
            new BlockItem(MATBlocks.GILDED_MARBLE.get(), new Properties()));
    public static final RegistryObject<BlockItem> GILDED_MARBLE_STAIRS = BLOCKS.register(REGISTRY, "gilded_marble_stairs", () ->
            new BlockItem(MATBlocks.GILDED_MARBLE_STAIRS.get(), new Properties())
    );
    public static final RegistryObject<BlockItem> GILDED_MARBLE_SLAB = BLOCKS.register(REGISTRY, "gilded_marble_slab", () ->
            new BlockItem(MATBlocks.GILDED_MARBLE_SLAB.get(), new Properties())
    );
    public static final RegistryObject<BlockItem> GILDED_MARBLE_WALL = BLOCKS.register(REGISTRY, "gilded_marble_wall", () ->
            new BlockItem(MATBlocks.GILDED_MARBLE_WALL.get(), new Properties())
    );
    public static final RegistryObject<BlockItem> CHISELED_GILDED_MARBLE = BLOCKS.register(REGISTRY, "chiseled_gilded_marble", () ->
            new BlockItem(MATBlocks.CHISELED_GILDED_MARBLE.get(), new Properties())
    );
    public static final RegistryObject<BlockItem> POLISHED_GILDED_MARBLE = BLOCKS.register(REGISTRY, "polished_gilded_marble", () ->
            new BlockItem(MATBlocks.POLISHED_GILDED_MARBLE.get(), new Properties())
    );
    public static final RegistryObject<BlockItem> POLISHED_GILDED_MARBLE_STAIRS = BLOCKS.register(REGISTRY, "polished_gilded_marble_stairs", () ->
            new BlockItem(MATBlocks.POLISHED_GILDED_MARBLE_STAIRS.get(), new Properties())
    );
    public static final RegistryObject<BlockItem> POLISHED_GILDED_MARBLE_SLAB = BLOCKS.register(REGISTRY, "polished_gilded_marble_slab", () ->
            new BlockItem(MATBlocks.POLISHED_GILDED_MARBLE_SLAB.get(), new Properties())
    );
    public static final RegistryObject<BlockItem> POLISHED_GILDED_MARBLE_WALL = BLOCKS.register(REGISTRY, "polished_gilded_marble_wall", () ->
            new BlockItem(MATBlocks.POLISHED_GILDED_MARBLE_WALL.get(), new Properties())
    );
    public static final RegistryObject<BlockItem> POLISHED_GILDED_MARBLE_PRESSURE_PLATE = BLOCKS.register(REGISTRY, "polished_gilded_marble_pressure_plate", () ->
            new BlockItem(MATBlocks.POLISHED_GILDED_MARBLE_PRESSURE_PLATE.get(), new Properties())
    );
    public static final RegistryObject<BlockItem> POLISHED_GILDED_MARBLE_BUTTON = BLOCKS.register(REGISTRY, "polished_gilded_marble_button", () ->
            new BlockItem(MATBlocks.POLISHED_GILDED_MARBLE_BUTTON.get(), new Properties())
    );
    public static final RegistryObject<BlockItem> CONDENSER = BLOCKS.register(REGISTRY, "condenser", () ->
            new BlockItem(MATBlocks.CONDENSER.get(), new Properties())
    );
    public static final RegistryObject<BlockItem> MAGIC_PERFUSION_PEDESTAL = BLOCKS.register(REGISTRY, "magic_perfusion_pedestal", () ->
            new BlockItem(MATBlocks.MAGIC_PERFUSION_PEDESTAL.get(), new Properties())
    );
    public static final RegistryObject<BlockItem> GOLD_GRAINED_MARBLE_PEDESTAL = BLOCKS.register(REGISTRY, "gold_grained_marble_pedestal", () ->
            new BlockItem(MATBlocks.GOLD_GRAINED_MARBLE_PEDESTAL.get(), new Properties())
    );
    public static final RegistryObject<BlockItem> MAGIC_CRAFTSMAN_TABLE = BLOCKS.register(REGISTRY, "magic_craftsman_table", () ->
            new BlockItem(MATBlocks.MAGIC_CRAFTSMAN_TABLE.get(), new Properties())
    );
    public static final RegistryObject<BlockItem> MERCURY_ORE = BLOCKS.register(REGISTRY, "mercury_ore", () ->
            new BlockItem(MATBlocks.MERCURY_ORE.get(), new Properties())
    );
    public static final RegistryObject<WorkHubBlockItem> WORK_HUB = BLOCKS.register(REGISTRY, "work_hub", () ->
            new WorkHubBlockItem(MATBlocks.WORK_HUB.get(), new Properties())
    );
    public static final RegistryObject<EnchantedCrucibleBlockItem> ENCHANTED_CRUCIBLE = BLOCKS.register(REGISTRY, "enchanted_crucible", () ->
            new EnchantedCrucibleBlockItem(MATBlocks.ENCHANTED_CRUCIBLE.get(), new Properties())
    );
    public static final RegistryObject<BlockItem> FIR_LEAVES = BLOCKS.register(REGISTRY, "fir_leaves", () ->
            new BlockItem(MATBlocks.FIR_LEAVES.get(), new Properties())
    );
    public static final RegistryObject<SacrificialDaggerItem> SACRIFICIAL_DAGGER = ITEMS.register(REGISTRY, "sacrificial_dagger", () ->
            new SacrificialDaggerItem(Tiers.IRON, 3, -2.4F, new Properties())
    );
    public static final RegistryObject<BloodBottleItem> BLOOD_BOTTLE = REGISTRY.register("blood_bottle", () ->
            new BloodBottleItem(new Properties().stacksTo(1))
    );
    public static final RegistryObject<FuelBlockItem> FIR_SAPLING = ITEMS.register(REGISTRY, "fir_sapling", () ->
            new FuelBlockItem(MATBlocks.FIR_SAPLING, 100, new Properties())
    );
    public static final RegistryObject<MATSignItem> FIR_SIGN = ITEMS.register(REGISTRY, "fir_sign", () ->
            new MATSignItem(MATBlocks.FIR_SIGN, MATBlocks.FIR_WALL_SIGN, 200, new Properties())
    );
    public static final RegistryObject<MATHangingSignItem> FIR_HANGING_SIGN = ITEMS.register(REGISTRY, "fir_hanging_sign", () ->
            new MATHangingSignItem(MATBlocks.FIR_HANGING_SIGN, MATBlocks.FIR_WALL_HANGING_SIGN, 200, new Properties())
    );
    public static final RegistryObject<Item> MORTAR = ITEMS.register(REGISTRY, "mortar", () ->
            new Item(new Properties().durability(128))
    );
    public static final RegistryObject<Item> BLAZE_BURNER = ITEMS.register(REGISTRY, "blaze_burner", () ->
            new Item(new Properties().durability(256))
    );
    public static final RegistryObject<Item> BLAZE_BLAST_BURNER = ITEMS.register(REGISTRY, "blaze_blast_burner", () ->
            new Item(new Properties().durability(32))
    );
    public static final RegistryObject<MagicPotionItem> POTION_BOTTLE = POTION.register(REGISTRY, "potion_bottle", () ->
            new MagicPotionItem(new Properties().stacksTo(4))
    );
    public static final RegistryObject<PotionBottleRedItem> POTION_BOTTLE_RED = POTION.register(REGISTRY, "potion_bottle_red", () ->
            new PotionBottleRedItem(new Properties().stacksTo(4))
    );
    public static final RegistryObject<PotionBottleGlowItem> POTION_BOTTLE_GLOW = POTION.register(REGISTRY, "potion_bottle_glow", () ->
            new PotionBottleGlowItem(new Properties().stacksTo(4))
    );
    public static final RegistryObject<PotionSyringeItem> POTION_SYRINGE = POTION.register(REGISTRY, "potion_syringe", () ->
            new PotionSyringeItem(new Properties().stacksTo(4))
    );
    public static final RegistryObject<AlchemyElementItem> ALCHEMY_ELEMENT = POTION.register(REGISTRY, "alchemy_element", () ->
            new AlchemyElementItem(new Properties().stacksTo(16))
    );
    public static final RegistryObject<GlassMagicPotionBottleItem> GLASS_POTION_BOTTLE = POTION.register(REGISTRY, "glass_potion_bottle", () ->
            new GlassMagicPotionBottleItem(new Properties(), POTION_BOTTLE.get())
    );
    public static final RegistryObject<GlassMagicPotionBottleItem> GLASS_POTION_BOTTLE_RED = POTION.register(REGISTRY, "glass_potion_bottle_red", () ->
            new GlassMagicPotionBottleItem(new Properties(), POTION_BOTTLE_RED.get())
    );
    public static final RegistryObject<GlassMagicPotionBottleItem> GLASS_POTION_BOTTLE_GLOW = POTION.register(REGISTRY, "glass_potion_bottle_glow", () ->
            new GlassMagicPotionBottleItem(new Properties(), POTION_BOTTLE_GLOW.get())
    );
    public static final RegistryObject<GlassMagicPotionBottleItem> GLASS_POTION_SYRINGE = POTION.register(REGISTRY, "glass_potion_syringe", () ->
            new GlassMagicPotionBottleItem(new Properties(), POTION_SYRINGE.get())
    );
    public static final RegistryObject<BucketItem> HONEY_BUCKET = POTION.register(REGISTRY, "honey_bucket", () ->
            new BucketItem(MATFluids.HONEY, new Properties().stacksTo(1))
    );
    public static final RegistryObject<BucketItem> PLANT_EXTRACT_BUCKET = POTION.register(REGISTRY, "plant_extract_bucket", () ->
            new BucketItem(MATFluids.PLANT_EXTRACT, new Properties().stacksTo(1))
    );
    public static final RegistryObject<OccultCodexItem> OCCULT_CODEX = ITEMS.register(REGISTRY, "occult_codex", () ->
            new OccultCodexItem(new Properties().stacksTo(1))
    );
    public static final RegistryObject<LimiteArmorItem> LIMITE_HELMET = ITEMS.register(REGISTRY, "limite_helmet", () ->
            new LimiteArmorItem(MATArmorMaterials.LIMITE, ArmorItem.Type.HELMET, new Properties())
    );
    public static final RegistryObject<LimiteArmorItem> LIMITE_CHESTPLATE = ITEMS.register(REGISTRY, "limite_chestplate", () ->
            new LimiteArmorItem(MATArmorMaterials.LIMITE, ArmorItem.Type.CHESTPLATE, new Properties())
    );
    public static final RegistryObject<LimiteArmorItem> LIMITE_LEGGINGS = ITEMS.register(REGISTRY, "limite_leggings", () ->
            new LimiteArmorItem(MATArmorMaterials.LIMITE, ArmorItem.Type.LEGGINGS, new Properties())
    );
    public static final RegistryObject<LimiteArmorItem> LIMITE_BOOTS = ITEMS.register(REGISTRY, "limite_boots", () ->
            new LimiteArmorItem(MATArmorMaterials.LIMITE, ArmorItem.Type.BOOTS, new Properties())
    );
    public static final RegistryObject<Item> PARCHMENT = ITEMS.register(REGISTRY, "parchment", () ->
            new Item(new Properties())
    );
    public static final RegistryObject<MagicPotionParchmentItem> MAGIC_POTION_PARCHMENT = REGISTRY.register("magic_potion_parchment", () ->
            new MagicPotionParchmentItem(new Properties().stacksTo(1))
    );
    public static final RegistryObject<Item> MERCURY_SLAG = POTION.register(REGISTRY, "mercury_slag", () -> new Item(new Properties().food(new FoodProperties.Builder()
            .alwaysEat()
            .nutrition(-5)
            .saturationMod(-0.6F)
            .effect(() -> new MobEffectInstance(MATEffects.MERCURY_TOXINS.get(), 15 * 20), 1)
            .build()
    )));
    public static final RegistryObject<Item> GROUND_MEAT = POTION.register(REGISTRY, "ground_meat", () -> new Item(new Properties().food(new FoodProperties.Builder()
            .meat()
            .alwaysEat()
            .nutrition(1)
            .saturationMod(0.6F)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 30 * 20), 1)
            .build()
    )));
    public static final RegistryObject<Item> CRUSHED_CARROTS = POTION.register(REGISTRY, "crushed_carrots", () -> new Item(new Properties().food(new FoodProperties.Builder()
            .alwaysEat()
            .nutrition(1)
            .saturationMod(0.5F)
            .build()
    )));
    public static final RegistryObject<Item> CARROTS_PASTE = POTION.register(REGISTRY, "carrots_paste", () -> new Item(new Properties().food(new FoodProperties.Builder()
            .alwaysEat()
            .nutrition(1)
            .saturationMod(0.5F)
            .build()
    )));
    public static final RegistryObject<Item> GOLDEN_CRUSHED_CARROTS = POTION.register(REGISTRY, "golden_crushed_carrots", () -> new Item(new Properties().food(new FoodProperties.Builder()
            .alwaysEat()
            .nutrition(1)
            .saturationMod(0.5F)
            .build()
    )));
    public static final RegistryObject<Item> HONEY_CRUSHED_CARROTS = POTION.register(REGISTRY, "honey_crushed_carrots", () -> new Item(new Properties().food(new FoodProperties.Builder()
            .alwaysEat()
            .nutrition(1)
            .saturationMod(0.5F)
            .build()
    )));
}
