package net.magic_and_taboo.init;

import net.magic_and_taboo.MagicAndTabooMod;
import net.magic_and_taboo.item.StarMapItems;
import net.magic_and_taboo.item.TimeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;


public class MATItems {
    public static final Item COPPER_COIN = basicItem(MATItemGroup.MAGIC,64);
    public static final Item SILVER_COIN = basicItem(MATItemGroup.MAGIC,64);
    public static final Item GOLD_COIN = basicItem(MATItemGroup.MAGIC,64);
    public static final Item CALENDAR = timeItem(MATItemGroup.MAGIC);
    public static final Item CALENDAR_WATCHES = timeItem(MATItemGroup.MAGIC);
    public static final Item COPPER_WATCHES = timeItem(MATItemGroup.MAGIC);
    public static final Item STAR_BOOK = basicItem(MATItemGroup.MAGIC,1);
    public static final Item STAR_BOOK_MAX = basicItem(MATItemGroup.MAGIC,1);
    public static final Item STAR_MAP_EMPTY = starMapItem("magic_and_taboo.star_map_empty");
    public static final Item STAR_MAP_1 = starMapItem("magic_and_taboo.star_map_1");
    public static final Item STAR_MAP_2 = starMapItem("magic_and_taboo.star_map_2");
    public static final Item STAR_MAP_3 = starMapItem("magic_and_taboo.star_map_3");
    public static final Item STAR_MAP_4 = starMapItem("magic_and_taboo.star_map_4");
    public static final Item STAR_MAP_5 = starMapItem("magic_and_taboo.star_map_5");
    public static final Item STAR_MAP_6 = starMapItem("magic_and_taboo.star_map_6");
    public static final Item STAR_MAP_7 = starMapItem("magic_and_taboo.star_map_7");
    public static final Item STAR_MAP_8 = starMapItem("magic_and_taboo.star_map_8");
    public static final Item STAR_MAP_9 = starMapItem("magic_and_taboo.star_map_9");
    public static final Item STAR_MAP_10 = starMapItem("magic_and_taboo.star_map_10");
    public static final Item STAR_MAP_11 = starMapItem("magic_and_taboo.star_map_11");
    public static final Item STAR_MAP_12 = starMapItem("magic_and_taboo.star_map_12");
    public static Item basicItem(ItemGroup group, int count){
        return new Item(new Item.Settings().group(group).maxCount(count));
    }
    public static Item starMapItem(String str){
        return new StarMapItems(new Item.Settings().group(MATItemGroup.MOONLIGHT).maxCount(16),str);
    }
    public static Item timeItem(ItemGroup group){
        return new TimeItem(new Item.Settings().group(MATItemGroup.MOONLIGHT).maxCount(1));
    }
}
