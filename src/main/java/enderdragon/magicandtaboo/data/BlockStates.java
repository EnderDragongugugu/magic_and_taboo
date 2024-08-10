package enderdragon.magicandtaboo.data;

import enderdragon.magicandtaboo.MagicAndTabooMod;
import enderdragon.magicandtaboo.block.MATBlock;
import enderdragon.magicandtaboo.util.DatagenUtil;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockStates extends BlockStateProvider {
    public BlockStates(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MagicAndTabooMod.MODID, exFileHelper);
        MagicAndTabooMod.LOGGER.debug("114514 114514 114514 114514 114514 114514 114514 114514 114514 114514 114514 114514 114514 114514 ");
    }

    @Override
    protected void registerStatesAndModels() {
        MagicAndTabooMod.LOGGER.debug("1111111111111111111111111");
        blockCubeAllModel(MATBlock.MERCURY_ORE.get());
    }

    public void blockCubeAllModel(Block block) {
        String path = DatagenUtil.getBlockName(block);
        ResourceLocation texture = DatagenUtil.resourceBlock(DatagenUtil.getBlockName(block));
//        simpleBlock(block,models().cubeAll(path, texture));
//        simpleBlock(block);
        simpleBlockWithItem(block,models().cubeAll(path, texture));
    }
}
