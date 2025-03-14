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
    public MATItemModelProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, MOD_ID, helper);
    }

    @Override
    protected void registerModels() {
        var firPlanks = makeId("block/fir/fir_planks");
        var polishedMarble = makeId("block/polished_gilded_marble");
        this.basicItem(MATItems.GROUND_MEAT.getId());
        this.basicItem(MATItems.CRUSHED_CARROTS.getId());
        this.basicItem(MATItems.CARROTS_PASTE.getId());
        this.basicItem(MATItems.GOLDEN_CRUSHED_CARROTS.getId());
        this.basicItem(MATItems.HONEY_CRUSHED_CARROTS.getId());
        this.basicItem(MATItems.HONEY_BUCKET.getId());
        this.basicItem(MATItems.PLANT_EXTRACT_BUCKET.getId());
        this.basicItem(MATItems.MERCURY_SLAG.getId());
        this.basicItem(MATItems.MORTAR.getId());
        this.basicItem(MATItems.BLAZE_BURNER.getId());
        this.basicItem(MATItems.BLAZE_BLAST_BURNER.getId());
        this.basicItem(MATItems.BLOOD_BOTTLE.getId());
        this.basicItem(MATBlocks.FIR_FENCE_GATE);
        this.basicItem(MATBlocks.FIR_WOOD);
        this.basicItem(MATBlocks.STRIPPED_FIR_WOOD);
        this.basicItem(MATBlocks.FIR_LOG);
        this.basicItem(MATBlocks.STRIPPED_FIR_LOG);
        this.basicItem(MATBlocks.FIR_PRESSURE_PLATE);
        this.basicItem(MATBlocks.FIR_SLAB);
        this.basicItem(MATBlocks.FIR_STAIRS);
        this.withExistingParent(MATBlocks.FIR_TRAPDOOR.getId().getPath(), makeId("block/fir_trapdoor_bottom"));
        this.basicItem(MATItems.FIR_DOOR.getId());
        this.basicItem(MATItems.FIR_SIGN.getId());
        this.basicItem(MATItems.FIR_HANGING_SIGN.getId());
        this.buttonItem(MATBlocks.FIR_BUTTON, firPlanks);
        this.withExistingParent(MATBlocks.FIR_FENCE.getId().getPath(), "block/fence_inventory")
                .texture("texture", firPlanks);
        this.getBuilder(MATItems.FIR_SAPLING.getId().getPath())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", makeId("block/fir/fir_sapling"));
        this.basicItem(MATBlocks.GILDED_MARBLE_STAIRS);
        this.basicItem(MATBlocks.GILDED_MARBLE_SLAB);
        this.wallItem(MATBlocks.GILDED_MARBLE_WALL, makeId("block/gilded_marble"));
        this.basicItem(MATBlocks.POLISHED_GILDED_MARBLE_STAIRS);
        this.basicItem(MATBlocks.POLISHED_GILDED_MARBLE_SLAB);
        this.wallItem(MATBlocks.POLISHED_GILDED_MARBLE_WALL, makeId("block/polished_gilded_marble"));
        this.basicItem(MATBlocks.POLISHED_GILDED_MARBLE_PRESSURE_PLATE);
        this.buttonItem(MATBlocks.POLISHED_GILDED_MARBLE_BUTTON, polishedMarble);
        this.basicItem(MATBlocks.WORK_HUB);
        this.basicItem(MATBlocks.ENCHANTED_CRUCIBLE);
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
        bottleItem(MATItems.POTION_BOTTLE);
        bottleItem(MATItems.POTION_BOTTLE_RED);
        bottleItem(MATItems.POTION_BOTTLE_GLOW);
        bottleItem(MATItems.POTION_SYRINGE);
        this.getBuilder(MATItems.GLASS_POTION_BOTTLE.getId().getPath())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", MATItems.POTION_BOTTLE.getId().withPrefix("item/"));
        this.getBuilder(MATItems.GLASS_POTION_BOTTLE_RED.getId().getPath())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", MATItems.POTION_BOTTLE_RED.getId().withPrefix("item/"));
        this.getBuilder(MATItems.GLASS_POTION_BOTTLE_GLOW.getId().getPath())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", MATItems.POTION_BOTTLE_GLOW.getId().withPrefix("item/"));
        this.getBuilder(MATItems.GLASS_POTION_SYRINGE.getId().getPath())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", MATItems.POTION_SYRINGE.getId().withPrefix("item/"));
    }

    public ItemModelBuilder handheldItem(RegistryObject<? extends Item> item) {
        var id = item.getId();
        return this.getBuilder(id.getPath())
                .parent(new ModelFile.UncheckedModelFile("item/handheld"))
                .texture("layer0", id.withPrefix("item/"));
    }

    public ItemModelBuilder bottleItem(RegistryObject<? extends Item> item) {
        var id = item.getId();
        return this.getBuilder(id.getPath())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", id.withPrefix("item/") + "_overlay")
                .texture("layer1", id.withPrefix("item/"));
    }


    public ItemModelBuilder basicItem(RegistryObject<? extends Block> block) {
        var id = block.getId();
        return this.getBuilder(id.getPath()).parent(new ModelFile.UncheckedModelFile(id.withPrefix("block/")));
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
}
