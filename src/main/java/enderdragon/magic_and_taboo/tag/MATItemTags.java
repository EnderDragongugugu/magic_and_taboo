package enderdragon.magic_and_taboo.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import static enderdragon.magic_and_taboo.MagicAndTabooMod.makeId;

public class MATItemTags {
    public static final TagKey<Item> RAW_MEAT = cTag("foods/raw_meat");

    public static final TagKey<Item> BLAZE_BURNERS = matTag("blaze_burners");
    public static final TagKey<Item> BLAZE_BLAST_BURNERS = matTag("blaze_blast_burners");
    public static final TagKey<Item> FIR_LOGS = matTag("fir_logs");
    public static final TagKey<Item> GILDED_MARBLE = matTag("gilded_marble");
    public static final TagKey<Item> MORTARS = matTag("mortars");
    public static final TagKey<Item> MAGIC_POTION_BOTTLE = matTag("glass_potion_bottle");
    public static final TagKey<Item> COOLANT = matTag("coolant");
    public static final TagKey<Item> UNFINISHED = matTag("unfinished");
    public static final TagKey<Item> IS_ALCHEMY = matTag("is_alchemy");
    public static final TagKey<Item> IS_ALCHEMY_MATERIALS = matTag("is_alchemy_materials");
    public static final TagKey<Item> IS_SOLVENT = matTag("is_solvent");
    public static final TagKey<Item> IS_GRINDABLE = matTag("is_grindable");

    // 元素处理相关标签
    public static final TagKey<Item> ELEMENT_ENHANCERS = matTag("element_enhancers");
    public static final TagKey<Item> ELEMENT_Diluents = matTag("element_diluents");
    public static final TagKey<Item> ELEMENT_STABILIZERS = matTag("element_stabilizers");
    // 特殊元素处理物品标签
    public static final TagKey<Item> ELEMENT_CONCENTRATED = matTag("element_concentrated");
    public static final TagKey<Item> ELEMENT_STABILIZED = matTag("element_stabilized");
    public static final TagKey<Item> ELEMENT_DILUTED = matTag("element_diluted");
    public static final TagKey<Item> ALCHEMY_INGREDIENTS = matTag("alchemy_ingredients");
    public static final TagKey<Item> CONTAINERS = matTag("containers");

    static TagKey<Item> matTag(String name) {
        return TagKey.create(Registries.ITEM, makeId(name));
    }

    static TagKey<Item> cTag(String name) {
        return TagKey.create(Registries.ITEM, new ResourceLocation("c", name));
    }
}
