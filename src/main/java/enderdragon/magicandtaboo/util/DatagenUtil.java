package enderdragon.magicandtaboo.util;

import enderdragon.magicandtaboo.MagicAndTabooMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

public class DatagenUtil {
    public static String getItemNanme(Item item){
        return ForgeRegistries.ITEMS.getKey(item).getPath();
    }
    public static String getBlockName(Block block){
        return ForgeRegistries.BLOCKS.getKey(block).getPath();
    }
    public static ResourceLocation resourceBlock(String path){
        return new ResourceLocation(MagicAndTabooMod.MODID,"block/"+path);
    }
    public static ResourceLocation resourceItem(String path){
        return new ResourceLocation(MagicAndTabooMod.MODID,"item/"+path);
    }
}
