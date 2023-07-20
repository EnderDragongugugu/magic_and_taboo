package net.magic_and_taboo;

import net.fabricmc.api.ModInitializer;
import net.magic_and_taboo.screen.SpyglassSextantScreenHandler;
import net.magic_and_taboo.init.MATBlockEntities;
import net.magic_and_taboo.init.MATBlocks;
import net.magic_and_taboo.init.MATItems;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class MagicAndTabooMod implements ModInitializer {
    public static final String MOD_ID = "magic_and_taboo";

    public MagicAndTabooMod() {
    }

    public static final ScreenHandlerType<SpyglassSextantScreenHandler> SPYGLASS_SEXTANT_SCREEN_HANDLER =
            Registry.register(Registry.SCREEN_HANDLER, new Identifier(MOD_ID, "screen"), new ScreenHandlerType<>(SpyglassSextantScreenHandler::new));

    public void onInitialize() {
        MATItems.onInitialize();
        MATBlocks.onInitialize();
        MATBlockEntities.onInitialize();
//        itemList.add(registry.basicItem("copper_coin", MagicAndTabooMod.MAGIC,64));

////        itemList.add(registry.basicItem("gold_coin", Main.MAGIC,64));
////        itemList.add(registry.basicItem("punk_helmet",ItemGroup.COMBAT,1));
////        itemList.add(registry.basicItem("fir_sapling",main.MAGIC,64));
////        itemList.add(registry.basicItem("moon_helmet",main.MAGIC,64));
////        itemList.add(registry.basicItem("moon_chestplate",main.MAGIC,64));
////        itemList.add(registry.basicItem("moon_leggings",main.MAGIC,64));
////        itemList.add(registry.basicItem("moon_boots",main.MAGIC,64));
//        itemList.add(registry.basicItem("sextant", MagicAndTabooMod.MOONLIGHT,1));
//        itemList.add(registry.basicItem("fir_sextant", MagicAndTabooMod.MOONLIGHT,1));
//        itemList.add(registry.basicItem("star_map_empty", MagicAndTabooMod.MOONLIGHT,64));
//        itemList.add(registry.basicItem("star_map_1", MagicAndTabooMod.MOONLIGHT,1));
//        itemList.add(registry.basicItem("star_map_2", MagicAndTabooMod.MOONLIGHT,1));
//        itemList.add(registry.basicItem("star_map_3", MagicAndTabooMod.MOONLIGHT,1));
//        itemList.add(registry.basicItem("star_map_4", MagicAndTabooMod.MOONLIGHT,1));
//        itemList.add(registry.basicItem("star_map_5", MagicAndTabooMod.MOONLIGHT,1));
//        itemList.add(registry.basicItem("star_map_6", MagicAndTabooMod.MOONLIGHT,1));
//        itemList.add(registry.basicItem("star_map_7", MagicAndTabooMod.MOONLIGHT,1));
//        itemList.add(registry.basicItem("star_map_8", MagicAndTabooMod.MOONLIGHT,1));
//        itemList.add(registry.basicItem("star_map_9", MagicAndTabooMod.MOONLIGHT,1));
//        itemList.add(registry.basicItem("star_map_10", MagicAndTabooMod.MOONLIGHT,1));
//        itemList.add(registry.basicItem("star_map_11", MagicAndTabooMod.MOONLIGHT,1));
//        itemList.add(registry.basicItem("star_map_12", MagicAndTabooMod.MOONLIGHT,1));
//        itemList.add(registry.basicItem("copper_watches", MagicAndTabooMod.MAGIC,1));
//        itemList.add(registry.basicItem("calendar", MagicAndTabooMod.MAGIC,1));
//        itemList.add(registry.basicItem("calendar_watches", MagicAndTabooMod.MAGIC,1));
//        itemList.add(registry.basicItem("star_book", MagicAndTabooMod.MOONLIGHT,1));
//        itemList.add(registry.basicItem("star_book_max", MagicAndTabooMod.MOONLIGHT,1));
//        itemList.add(registry.basicItem("brass_ingot", MagicAndTabooMod.MAGIC,64));
////        itemList.add(registry.basicItem("alchemy", Main.MAGIC,16));
////        itemList.add(registry.basicItem("alchemy_book", Main.MAGIC,1));
//        itemList.add(registry.basicItem("magic_lasurite", MagicAndTabooMod.MAGIC,1));
//        itemList.add(registry.basicItem("magic_gold_bracelet", MagicAndTabooMod.MAGIC,1));
//        itemList.add(registry.basicItem("mercury_slag", MagicAndTabooMod.MAGIC,64));
//        itemList.add(registry.basicItem("soak_book_mercury", MagicAndTabooMod.MAGIC,1));
//        itemList.add(registry.basicItem("soak_book_blood", MagicAndTabooMod.MAGIC,1));
//        itemList.add(registry.basicItem("soak_book_soul", MagicAndTabooMod.MAGIC,64));
//        itemList.add(registry.basicItem("snoop_eye", MagicAndTabooMod.MAGIC,1));
//        itemList.add(registry.basicItem("remains_soul", MagicAndTabooMod.BLOOD,64));
//        itemList.add(registry.basicItem("ground_meat", MagicAndTabooMod.BLOOD,64));
//        itemList.add(registry.basicItem("blood_bottle", MagicAndTabooMod.BLOOD,64));
//        itemList.add(registry.basicItem("wind_wand", MagicAndTabooMod.MAGIC,1));
//        itemList.add(registry.basicItem("basics_scroll", MagicAndTabooMod.MAGIC,1));
//        itemList.add(registry.basicItem("rod_end", MagicAndTabooMod.MAGIC,64));
//        itemList.add(registry.basicItem("rod_handle", MagicAndTabooMod.MAGIC,64));
//        itemList.add(registry.basicItem("holy_feather", MagicAndTabooMod.MAGIC,64));
    }
}
