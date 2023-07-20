package net.magic_and_taboo.init;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.magic_and_taboo.block.SpyglassSextantBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.PillarBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import javax.annotation.Nullable;

import static net.magic_and_taboo.MagicAndTabooMod.MOD_ID;
import static net.magic_and_taboo.init.MATItemGroups.MOONLIGHT;

public class MATBlocks {
    public static final PillarBlock FIR_LOG = registerFlammableBlock(
            "fir_log",
            new PillarBlock(FabricBlockSettings.of(Material.WOOD).strength(1.6F).sounds(BlockSoundGroup.WOOD)),
            new FabricItemSettings().group(MATItemGroups.MAGIC),
            5,
            5
    );
    public static final Block FIR_PLANKS = registerFlammableBlock(
            "fir_planks",
            new Block(FabricBlockSettings.of(Material.WOOD).strength(1.6F).sounds(BlockSoundGroup.WOOD)),
            new FabricItemSettings().group(MATItemGroups.MAGIC),
            5,
            5
    );
    public static final Block SPYGLASS_SEXTANT_BLOCK = register(
            "spyglass_sextant",
            new SpyglassSextantBlock(FabricBlockSettings.of(Material.WOOD).strength(1.6F).nonOpaque().sounds(BlockSoundGroup.WOOD)),
            new FabricItemSettings().group(MOONLIGHT)
    );

    public static <T extends Block> T registerFlammableBlock(String name, T block, Item.Settings settings, int burn, int spread) {
        FlammableBlockRegistry.getDefaultInstance().add(block, spread, burn);
        return register(name, block, settings);
    }

    public static <T extends Block> T register(String name, T block, @Nullable Item item) {
        Identifier identifier = new Identifier(MOD_ID, name);
        if (item != null) {
            Registry.register(Registry.ITEM, identifier, item);
        }
        return Registry.register(Registry.BLOCK, identifier, block);
    }

    public static <T extends Block> T register(String name, T block, Item.Settings settings) {
        return register(name, block, new BlockItem(block, settings));
    }

    public static void onInitialize() {
        //to load this class
    }
}
