package net.magic_and_taboo.lib;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class MJRegistry {
    public void registryItem(String path,Item item){
        Registry.register(Registry.ITEM, new Identifier("magic_and_taboo", path),item);
    }
    public void registryBlock(String path, Block block, Item.Settings settings){
        Registry.register(Registry.BLOCK,new Identifier("magic_and_taboo",path), block);
        Registry.register(Registry.ITEM, new Identifier("magic_and_taboo", path), new BlockItem(block,settings));
    }
}
