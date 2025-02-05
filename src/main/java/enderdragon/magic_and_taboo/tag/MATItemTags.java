package enderdragon.magic_and_taboo.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import static enderdragon.magic_and_taboo.MagicAndTabooMod.makeId;

public class MATItemTags {
    public static final TagKey<Item> BLAZE_BURNERS = matTag("blaze_burners");
    public static final TagKey<Item> BLAZE_BLAST_BURNERS = matTag("blaze_blast_burners");
    public static final TagKey<Item> FIR_LOGS = matTag("fir_logs");
    public static final TagKey<Item> GILDED_MARBLE = matTag("gilded_marble");
    public static final TagKey<Item> MORTARS = matTag("mortars");
    public static final TagKey<Item> RAW_MEAT = cTag("foods/raw_meat");

    static TagKey<Item> matTag(String name) {
        return TagKey.create(Registries.ITEM, makeId(name));
    }

    static TagKey<Item> cTag(String name) {
        return TagKey.create(Registries.ITEM, new ResourceLocation("c", name));
    }
}
