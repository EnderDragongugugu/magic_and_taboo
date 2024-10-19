package enderdragon.magic_and_taboo.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import static enderdragon.magic_and_taboo.MagicAndTabooMod.makeId;

public class MATItemTags {
    public static final TagKey<Item> BLAZE_BURNERS = create("blaze_burners");
    public static final TagKey<Item> BLAZE_BLAST_BURNERS = create("blaze_blast_burners");
    public static final TagKey<Item> FIR_LOGS = create("fir_logs");
    public static final TagKey<Item> GILDED_MARBLE = create("gilded_marble");
    public static final TagKey<Item> MORTARS = create("mortars");

    static TagKey<Item> create(String name) {
        return TagKey.create(Registries.ITEM, makeId(name));
    }
}
