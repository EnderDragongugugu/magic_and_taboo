package net.magic_and_taboo.init;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.magic_and_taboo.block.FlammableBlock;
import net.magic_and_taboo.block.SpyglassSextantBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;

public class MATBlocks {
    public static final Block FIR_PLANKS = PlanksBlock(1.6F,5, 5);
    public static final Block SPYGLASS_SEXTANT_BLOCK = new SpyglassSextantBlock(FabricBlockSettings.of(Material.WOOD).strength(1.6F).nonOpaque().sounds(BlockSoundGroup.WOOD));

    public static Block basicBlock(Material material, float num){
        return new Block(FabricBlockSettings
                .of(material)
                .strength(num)
        );
    }
    public static Block PlanksBlock( float num,int burn, int spread){
        final Block block = new FlammableBlock(FabricBlockSettings
                .of(Material.WOOD)
                .strength(num)
                .sounds(BlockSoundGroup.WOOD),
                burn,
                spread
        );
        return block;
    }
}
