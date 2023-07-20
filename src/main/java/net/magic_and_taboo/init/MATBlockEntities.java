package net.magic_and_taboo.init;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.magic_and_taboo.block.entity.SpyglassSextantBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static net.magic_and_taboo.MagicAndTabooMod.MOD_ID;

public class MATBlockEntities {
    public static final BlockEntityType<SpyglassSextantBlockEntity> SPYGLASS_SEXTANT_BLOCK_ENTITY = register(
            "spyglass_sextant",
            MATBlocks.SPYGLASS_SEXTANT_BLOCK,
            SpyglassSextantBlockEntity::new
    );

    public static <T extends BlockEntity> BlockEntityType<T> register(String name, Block block, FabricBlockEntityTypeBuilder.Factory<T> factory) {
        return Registry.register(
                Registry.BLOCK_ENTITY_TYPE,
                new Identifier(MOD_ID, name),
                FabricBlockEntityTypeBuilder.create(factory, block).build()
        );
    }

    public static void onInitialize() {
        //to load this class
    }
}
