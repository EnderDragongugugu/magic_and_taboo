package net.magic_and_taboo.util;
import net.magic_and_taboo.MagicAndTabooMod;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class MATRegistry {
    public void registryItem(String path,Item item){
        Registry.register(Registry.ITEM, new Identifier(MagicAndTabooMod.MOD_ID, path),item);
    }
    public void registryBlock(String path, Block block, Item.Settings settings){
        Registry.register(Registry.BLOCK,new Identifier(MagicAndTabooMod.MOD_ID,path), block);
        Registry.register(Registry.ITEM, new Identifier(MagicAndTabooMod.MOD_ID, path), new BlockItem(block,settings));
    }
    public void registryBlockEntity(String path, BlockEntityType blockEntity){
        Registry.register(Registry.BLOCK_ENTITY_TYPE,new Identifier(MagicAndTabooMod.MOD_ID,path),blockEntity);
    }
}
