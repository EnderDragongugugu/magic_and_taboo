package enderdragon.magicandtaboo.data;

import enderdragon.magicandtaboo.init.MATBlocks;
import enderdragon.magicandtaboo.init.MATItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import static enderdragon.magicandtaboo.MagicAndTabooMod.MOD_ID;
import static enderdragon.magicandtaboo.MagicAndTabooMod.makeId;

@SuppressWarnings("UnusedReturnValue")
public class MATItemModelProvider extends ItemModelProvider {
    public MATItemModelProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, MOD_ID, helper);
    }

    @Override
    protected void registerModels() {
        var firPlanks = makeId("block/fir/fir_planks");
        var polishedMarble = makeId("block/polished_gilded_marble");
        this.withExistingParent(MATItems.SACRIFICIAL_DAGGER.getId().getPath(), "item/netherite_sword");
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
        this.withExistingParent(MATItems.FIR_DOOR.getId().getPath(), "item/spruce_door");
        this.withExistingParent(MATItems.FIR_SIGN.getId().getPath(), "item/spruce_sign");
        this.withExistingParent(MATItems.FIR_HANGING_SIGN.getId().getPath(), "item/spruce_hanging_sign");
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
    }

    public ItemModelBuilder handheldItem(RegistryObject<? extends Item> item) {
        var id = item.getId();
        return this.getBuilder(id.getPath())
                .parent(new ModelFile.UncheckedModelFile("item/handheld"))
                .texture("layer0", id.withPrefix("item/"));
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
