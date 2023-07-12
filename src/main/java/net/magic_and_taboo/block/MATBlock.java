package net.magic_and_taboo.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;

public class MATBlock {
//    public static final BasicBlock BB = new BasicBlock();
//    public static final MATPlanks MAT_PLANKS = new MATPlanks();
    public static final Block FIR_PLANKS = PlanksBlock(Material.WOOD, 1.6F,5, 5);
    public static final Block SPYGLASSSEXTANT = new SpyglassSextantBlock(FabricBlockSettings.of(Material.WOOD).strength(1.6F));

    public static Block basicBlock(Material material, float num){
        return new Block(FabricBlockSettings
                .of(material)
                .strength(num)
        );
    }
    public static Block PlanksBlock(Material material, float num,int burn, int spread){
        final Block block = new FlammableBlock(FabricBlockSettings
                .of(material)
                .strength(num),
                burn,
                spread
        );
        return block;
    }
}
