package enderdragon.magicandtaboo.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import static enderdragon.magicandtaboo.MagicAndTabooMod.makeId;

public class MATItemTags {
    public static final TagKey<Item> FIR_LOGS = create("fir_logs");
    public static final TagKey<Item> GILDED_MARBLE = create("gilded_marble");

    static TagKey<Item> create(String name) {
        return TagKey.create(Registries.ITEM, makeId(name));
    }
}
