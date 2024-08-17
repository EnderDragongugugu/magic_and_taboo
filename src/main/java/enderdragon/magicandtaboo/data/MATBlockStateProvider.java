package enderdragon.magicandtaboo.data;

import enderdragon.magicandtaboo.init.MATBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import static enderdragon.magicandtaboo.MagicAndTabooMod.MOD_ID;
import static enderdragon.magicandtaboo.MagicAndTabooMod.makeId;

public class MATBlockStateProvider extends BlockStateProvider {
    public MATBlockStateProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, MOD_ID, helper);
    }

    @Override
    protected void registerStatesAndModels() {
        this.simpleBlockWithItem(MATBlocks.MERCURY_ORE);
        this.firWood();
    }

    public void simpleBlockWithItem(RegistryObject<? extends Block> block) {
        var id = block.getId();
        this.simpleBlockWithItem(block.get(), this.models().cubeAll(id.getPath(), id.withPrefix("block/")));
    }

    void firWood() {
        var side = makeId("block/fir/fir_log_side");
        var top = makeId("block/fir/fir_log_top");
        var strippedSide = makeId("block/fir/stripped_fir_log");
        var strippedTop = makeId("block/fir/stripped_fir_log_top");
        var planks = makeId("block/fir/fir_planks");
        var sapling = makeId("block/fir/fir_sapling");
        var cutout = new ResourceLocation("cutout");
        this.axisBlock(MATBlocks.FIR_WOOD.get(), side, side);
        this.axisBlock(MATBlocks.FIR_LOG.get(), side, top);
        this.axisBlock(MATBlocks.STRIPPED_FIR_WOOD.get(), strippedSide, strippedSide);
        this.axisBlock(MATBlocks.STRIPPED_FIR_LOG.get(), strippedSide, strippedTop);
        this.simpleBlock(MATBlocks.FIR_SAPLING.get(), this.models()
                .cross(MATBlocks.FIR_SAPLING.getId().getPath(), sapling)
                .renderType(cutout)
        );
        this.simpleBlock(MATBlocks.POTTED_FIR_SAPLING.get(), this.models()
                .withExistingParent(MATBlocks.POTTED_FIR_SAPLING.getId().getPath(), "block/flower_pot_cross")
                .texture("plant", sapling)
                .renderType(cutout)
        );
        this.simpleBlockWithItem(MATBlocks.FIR_LEAVES.get(), this.models().leaves(MATBlocks.FIR_LEAVES.getId().getPath(), makeId("block/fir/fir_leaves")));
        this.simpleBlockWithItem(MATBlocks.FIR_PLANKS.get(), this.models().cubeAll(MATBlocks.FIR_PLANKS.getId().getPath(), planks));
        this.signBlock(MATBlocks.FIR_SIGN.get(), MATBlocks.FIR_WALL_SIGN.get(), planks);
        this.doorBlock(MATBlocks.FIR_DOOR.get(), new ResourceLocation("block/spruce_door_bottom"), new ResourceLocation("block/spruce_door_top"));
        this.trapdoorBlock(MATBlocks.FIR_TRAPDOOR.get(), new ResourceLocation("block/spruce_trapdoor"), true);
        this.fenceBlock(MATBlocks.FIR_FENCE.get(), planks);
        this.fenceGateBlock(MATBlocks.FIR_FENCE_GATE.get(), planks);
        this.stairsBlock(MATBlocks.FIR_STAIRS.get(), planks);
        this.slabBlock(MATBlocks.FIR_SLAB.get(), MATBlocks.FIR_PLANKS.getId(), planks);
        this.pressurePlateBlock(MATBlocks.FIR_PRESSURE_PLATE.get(), planks);
        this.buttonBlock(MATBlocks.FIR_BUTTON.get(), planks);
        var hanging = this.models().sign(MATBlocks.FIR_HANGING_SIGN.getId().getPath(), strippedSide);
        this.simpleBlock(MATBlocks.FIR_HANGING_SIGN.get(), hanging);
        this.simpleBlock(MATBlocks.FIR_WALL_HANGING_SIGN.get(), hanging);
    }
}
