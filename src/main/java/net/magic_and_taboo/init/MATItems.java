package net.magic_and_taboo.init;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.magic_and_taboo.MagicAndTabooMod;
import net.magic_and_taboo.item.StarMapItem;
import net.magic_and_taboo.item.TimeItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static net.magic_and_taboo.init.MATItemGroups.MAGIC;
import static net.magic_and_taboo.init.MATItemGroups.MOONLIGHT;

public class MATItems {
    public static final Item COPPER_COIN = register("copper_coin", new Item(new FabricItemSettings().group(MAGIC)));
    public static final Item SILVER_COIN = register("silver_coin", new Item(new FabricItemSettings().group(MAGIC)));
    public static final Item GOLD_COIN = register("gold_coin", new Item(new FabricItemSettings().group(MAGIC)));
    public static final TimeItem CALENDAR = register("calendar", new TimeItem(new FabricItemSettings().group(MOONLIGHT).maxCount(1)));
    public static final TimeItem CALENDAR_WATCHES = register("calendar_watches", new TimeItem(new FabricItemSettings().group(MOONLIGHT).maxCount(1)));
    public static final TimeItem COPPER_WATCHES = register("copper_watches", new TimeItem(new FabricItemSettings().group(MOONLIGHT).maxCount(1)));
    public static final Item STAR_BOOK = register("star_book", new TimeItem(new FabricItemSettings().group(MOONLIGHT).maxCount(64)));
    public static final Item STAR_BOOK_MAX = register("star_book_max", new TimeItem(new FabricItemSettings().group(MOONLIGHT).maxCount(64)));
    public static final Item STAR_MAP_EMPTY = registerStarMap("star_map_empty");
    public static final Item STAR_MAP_1 = registerStarMap("star_map_1");
    public static final Item STAR_MAP_2 = registerStarMap("star_map_2");
    public static final Item STAR_MAP_3 = registerStarMap("star_map_3");
    public static final Item STAR_MAP_4 = registerStarMap("star_map_4");
    public static final Item STAR_MAP_5 = registerStarMap("star_map_5");
    public static final Item STAR_MAP_6 = registerStarMap("star_map_6");
    public static final Item STAR_MAP_7 = registerStarMap("star_map_7");
    public static final Item STAR_MAP_8 = registerStarMap("star_map_8");
    public static final Item STAR_MAP_9 = registerStarMap("star_map_9");
    public static final Item STAR_MAP_10 = registerStarMap("star_map_10");
    public static final Item STAR_MAP_11 = registerStarMap("star_map_11");
    public static final Item STAR_MAP_12 = registerStarMap("star_map_12");


    public static <T extends Item> T register(String name, T item) {
        return Registry.register(Registry.ITEM, new Identifier(MagicAndTabooMod.MOD_ID, name), item);
    }

    public static Item registerStarMap(String name) {
        return register(name, new StarMapItem(new Item.Settings().group(MOONLIGHT).maxCount(16), "magic_and_taboo." + name));
    }

    public static void onInitialize() {
        //to load this class
    }
}
