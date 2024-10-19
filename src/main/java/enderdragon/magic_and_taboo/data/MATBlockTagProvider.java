package enderdragon.magic_and_taboo.data;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static enderdragon.magic_and_taboo.MagicAndTabooMod.MOD_ID;
import static enderdragon.magic_and_taboo.init.MATBlocks.*;
import static enderdragon.magic_and_taboo.tag.MATBlockTags.FIR_LOGS;
import static net.minecraft.tags.BlockTags.*;

public class MATBlockTagProvider extends BlockTagsProvider {
    public MATBlockTagProvider(PackOutput output, CompletableFuture<Provider> registry, @Nullable ExistingFileHelper helper) {
        super(output, registry, MOD_ID, helper);
    }

    @Override
    protected void addTags(@NotNull Provider registry) {
        this.tag(NEEDS_IRON_TOOL).add(MERCURY_ORE.get());
        this.tag(MINEABLE_WITH_PICKAXE).add(MERCURY_ORE.get());
        this.tag(FIR_LOGS)
                .add(FIR_LOG.get())
                .add(FIR_WOOD.get())
                .add(STRIPPED_FIR_LOG.get())
                .add(STRIPPED_FIR_WOOD.get());
        this.tag(LOGS_THAT_BURN).addTag(FIR_LOGS);
        this.tag(SAPLINGS).add(FIR_SAPLING.get());
        this.tag(LEAVES).add(FIR_LEAVES.get());
        this.tag(PLANKS).add(FIR_PLANKS.get());
        this.tag(STANDING_SIGNS).add(FIR_SIGN.get());
        this.tag(WALL_SIGNS).add(FIR_WALL_SIGN.get());
        this.tag(CEILING_HANGING_SIGNS).add(FIR_HANGING_SIGN.get());
        this.tag(WALL_HANGING_SIGNS).add(FIR_WALL_HANGING_SIGN.get());
        this.tag(WOODEN_BUTTONS).add(FIR_BUTTON.get());
        this.tag(WOODEN_DOORS).add(FIR_DOOR.get());
        this.tag(WOODEN_STAIRS).add(FIR_STAIRS.get());
        this.tag(WOODEN_SLABS).add(FIR_SLAB.get());
        this.tag(WOODEN_FENCES).add(FIR_FENCE.get());
        this.tag(WOODEN_PRESSURE_PLATES).add(FIR_PRESSURE_PLATE.get());
        this.tag(WOODEN_TRAPDOORS).add(FIR_TRAPDOOR.get());
        this.tag(WALLS)
                .add(GILDED_MARBLE_WALL.get())
                .add(POLISHED_GILDED_MARBLE_WALL.get());
    }
}
