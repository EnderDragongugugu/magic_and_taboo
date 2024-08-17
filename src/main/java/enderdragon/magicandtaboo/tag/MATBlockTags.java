package enderdragon.magicandtaboo.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import static enderdragon.magicandtaboo.MagicAndTabooMod.makeId;

public class MATBlockTags {
    public static final TagKey<Block> FIR_LOGS = create("fir_logs");

    static TagKey<Block> create(String name) {
        return TagKey.create(Registries.BLOCK, makeId(name));
    }
}
