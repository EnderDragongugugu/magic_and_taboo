package net.magic_and_taboo.init;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.magic_and_taboo.MagicAndTabooMod;
import net.magic_and_taboo.block.entity.SpyglassSextantEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static net.magic_and_taboo.init.MATBlocks.SPYGLASS_SEXTANT_BLOCK;

public class MATBlockEntities {
    public static final BlockEntityType<SpyglassSextantEntity> SPYGLASS_SEXTANT_BLOCK_ENTITY = FabricBlockEntityTypeBuilder.create(SpyglassSextantEntity::new,SPYGLASS_SEXTANT_BLOCK).build();
//    public void registryBlockEntity(String path,BlockEntityType ){
//        Registry.register(Registry.BLOCK_ENTITY_TYPE,new Identifier(MagicAndTabooMod.MOD_ID,path),blockEntity);
//    }
}
