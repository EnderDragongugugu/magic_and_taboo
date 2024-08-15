package enderdragon.magicandtaboo.data;

import init.MATBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import static enderdragon.magicandtaboo.MagicAndTabooMod.MOD_ID;

public class MATBlockStateProvider extends BlockStateProvider {
    public MATBlockStateProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, MOD_ID, helper);
    }

    @Override
    protected void registerStatesAndModels() {
        this.simpleBlockWithItem(MATBlocks.MERCURY_ORE);
    }

    public void simpleBlockWithItem(RegistryObject<? extends Block> block) {
        var id = block.getId();
        this.simpleBlockWithItem(block.get(), this.models().cubeAll(id.getPath(), id.withPrefix("block/")));
    }
}
