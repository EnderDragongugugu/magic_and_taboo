package enderdragon.magic_and_taboo.tag;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ForgeTags {
    public static final TagKey<Item> RAW_MEATS = create("raw_meats");

    static TagKey<Item> create(String path) {
        return ItemTags.create(new ResourceLocation("forge", path));
    }
}
