package enderdragon.magic_and_taboo.data;

import enderdragon.magic_and_taboo.init.MATBlocks;
import enderdragon.magic_and_taboo.init.MATItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import static enderdragon.magic_and_taboo.MagicAndTabooMod.MOD_ID;
import static enderdragon.magic_and_taboo.MagicAndTabooMod.makeId;

@SuppressWarnings("UnusedReturnValue")
public class MATItemModelProvider extends ItemModelProvider {
    private static final ModelFile GENERATED_MODEL = new ModelFile.UncheckedModelFile("item/generated");

    public MATItemModelProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, MOD_ID, helper);
    }

    @Override
    protected void registerModels() {
        var firPlanks = makeId("block/fir/fir_planks");
        var polishedMarble = makeId("block/polished_gilded_marble");
        this.basicItem(MATItems.GROUND_MEAT);
        this.basicItem(MATItems.OCCULT_CODEX);
        this.basicItem(MATItems.MAGIC_POTION_PARCHMENT);
        this.basicItem(MATItems.PARCHMENT);
        this.basicItem(MATItems.CRUSHED_CARROTS);
        this.basicItem(MATItems.CARROTS_PASTE);
        this.basicItem(MATItems.GOLDEN_CRUSHED_CARROTS);
        this.basicItem(MATItems.HONEY_CRUSHED_CARROTS);
        this.basicItem(MATItems.HONEY_BUCKET);
        this.basicItem(MATItems.PLANT_EXTRACT_BUCKET);
        this.basicItem(MATItems.MERCURY_SLAG);
        this.basicItem(MATItems.MORTAR);
        this.basicItem(MATItems.BLAZE_BURNER);
        this.basicItem(MATItems.BLAZE_BLAST_BURNER);
        this.basicItem(MATItems.BLOOD_BOTTLE);
        
        this.basicBlockItem(MATBlocks.MAGIC_CRAFTSMAN_TABLE);
        this.basicBlockItem(MATBlocks.CONDENSER);
        this.basicBlockItem(MATBlocks.MAGIC_PERFUSION_PEDESTAL);
        this.basicBlockItem(MATBlocks.GOLD_GRAINED_MARBLE_PEDESTAL);
        this.basicBlockItem(MATBlocks.FIR_FENCE_GATE);
        this.basicBlockItem(MATBlocks.FIR_WOOD);
        this.basicBlockItem(MATBlocks.STRIPPED_FIR_WOOD);
        this.basicBlockItem(MATBlocks.FIR_LOG);
        this.basicBlockItem(MATBlocks.STRIPPED_FIR_LOG);
        this.basicBlockItem(MATBlocks.FIR_PRESSURE_PLATE);
        this.basicBlockItem(MATBlocks.FIR_SLAB);
        this.basicBlockItem(MATBlocks.FIR_STAIRS);
        this.withExistingParent(MATBlocks.FIR_TRAPDOOR.getId().getPath(), makeId("block/fir_trapdoor_bottom"));
        this.basicItem(MATItems.FIR_DOOR);
        this.basicItem(MATItems.FIR_SIGN);
        this.basicItem(MATItems.FIR_HANGING_SIGN);
        this.buttonItem(MATBlocks.FIR_BUTTON, firPlanks);
        this.withExistingParent(MATBlocks.FIR_FENCE.getId().getPath(), "block/fence_inventory")
                .texture("texture", firPlanks);
        this.getBuilder(MATItems.FIR_SAPLING.getId().getPath())
                .parent(GENERATED_MODEL)
                .texture("layer0", makeId("block/fir/fir_sapling"));
        this.basicBlockItem(MATBlocks.GILDED_MARBLE_STAIRS);
        this.basicBlockItem(MATBlocks.GILDED_MARBLE_SLAB);
        this.wallItem(MATBlocks.GILDED_MARBLE_WALL, makeId("block/gilded_marble"));
        this.basicBlockItem(MATBlocks.POLISHED_GILDED_MARBLE_STAIRS);
        this.basicBlockItem(MATBlocks.POLISHED_GILDED_MARBLE_SLAB);
        this.wallItem(MATBlocks.POLISHED_GILDED_MARBLE_WALL, makeId("block/polished_gilded_marble"));
        this.basicBlockItem(MATBlocks.POLISHED_GILDED_MARBLE_PRESSURE_PLATE);
        this.buttonItem(MATBlocks.POLISHED_GILDED_MARBLE_BUTTON, polishedMarble);
        this.basicBlockItem(MATBlocks.WORK_HUB);
        this.basicBlockItem(MATBlocks.ENCHANTED_CRUCIBLE);
        var handheld = new ModelFile.UncheckedModelFile("item/handheld");
        this.getBuilder("sacrificial_dagger")
                .parent(handheld)
                .texture("layer0", makeId("item/sacrificial_dagger"))
                .override()
                .predicate(new ResourceLocation("bloody"), 1.0F)
                .model(this.getBuilder("bloody_sacrificial_dagger")
                        .parent(handheld)
                        .texture("layer0", makeId("item/bloody_sacrificial_dagger"))
                );
        this.bottleItems(MATItems.POTION_BOTTLE, MATItems.GLASS_POTION_BOTTLE);
        this.bottleItems(MATItems.POTION_BOTTLE_RED, MATItems.GLASS_POTION_BOTTLE_RED);
        this.bottleItems(MATItems.POTION_SYRINGE, MATItems.GLASS_POTION_SYRINGE);
        this.bottleItems(MATItems.POTION_BOTTLE_GLOW, MATItems.GLASS_POTION_BOTTLE_GLOW);
        this.bottleItems(MATItems.ALCHEMY_ELEMENT);

    }

    public ItemModelBuilder handheldItem(RegistryObject<? extends Item> item) {
        var id = item.getId();
        return this.getBuilder(id.getPath())
                .parent(new ModelFile.UncheckedModelFile("item/handheld"))
                .texture("layer0", id.withPrefix("item/"));
    }

    public ItemModelBuilder basicBlockItem(RegistryObject<? extends Block> block) {
        var id = block.getId();
        return this.getBuilder(id.getPath()).parent(new ModelFile.UncheckedModelFile(id.withPrefix("block/")));
    }

    public ItemModelBuilder basicItem(RegistryObject<? extends Item> item) {
        var id = item.getId();
        return this.getBuilder(id.getPath())
                .parent(GENERATED_MODEL)
                .texture("layer0", id.withPrefix("item/"));
    }

    public ItemModelBuilder buttonItem(RegistryObject<? extends Block> block, ResourceLocation texture) {
        return this.getBuilder(block.getId().getPath())
                .parent(new ModelFile.UncheckedModelFile("block/button_inventory"))
                .texture("texture", texture);
    }

    public ItemModelBuilder wallItem(RegistryObject<? extends Block> block, ResourceLocation texture) {
        return this.getBuilder(block.getId().getPath())
                .parent(new ModelFile.UncheckedModelFile("block/wall_inventory"))
                .texture("wall", texture);
    }

//    public void colorItems(RegistryObject<? extends Item> filled, RegistryObject<? extends Item> container) {
//        var id = filled.getId();
//        var item = id.withPrefix("item/");
//        this.getBuilder(id.getPath())
//                .parent(GENERATED_MODEL)
//                .texture("layer0", id.withPath(path -> "item/" + path + "_overlay"))
//                .texture("layer1", item);
//    }

    public void bottleItems(RegistryObject<? extends Item> filled, RegistryObject<? extends Item> container) {
        var id = filled.getId();
        var bottle = id.withPrefix("item/");
        this.getBuilder(id.getPath())
                .parent(GENERATED_MODEL)
                .texture("layer0", id.withPath(path -> "item/" + path + "_overlay"))
                .texture("layer1", bottle);
        this.getBuilder(container.getId().getPath())
                .parent(GENERATED_MODEL)
                .texture("layer0", bottle);
    }

    public void bottleItems(RegistryObject<? extends Item> filled) {
        var id = filled.getId();
        var bottle = id.withPrefix("item/");
        this.getBuilder(id.getPath())
                .parent(GENERATED_MODEL)
                .texture("layer0", id.withPath(path -> "item/" + path + "_overlay"))
                .texture("layer1", bottle);
    }
}
