package enderdragon.magicandtaboo.init;

import enderdragon.magicandtaboo.MagicAndTabooMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class MATTag {
    public static final TagKey<Item> BLAZE_LANTERN = createItemTag("blaze_lantern");
    public static final TagKey<Item> MORTAR = createItemTag("mortar");
    public static TagKey<Item> createItemTag(String tag){
        return ItemTags.create(new ResourceLocation(MagicAndTabooMod.MOD_ID,tag));
    }
}
