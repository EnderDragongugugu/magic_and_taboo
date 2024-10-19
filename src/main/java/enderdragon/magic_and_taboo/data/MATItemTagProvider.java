package enderdragon.magic_and_taboo.data;

import enderdragon.magic_and_taboo.init.MATItems;
import enderdragon.magic_and_taboo.tag.MATBlockTags;
import enderdragon.magic_and_taboo.tag.MATItemTags;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static enderdragon.magic_and_taboo.MagicAndTabooMod.MOD_ID;

public class MATItemTagProvider extends ItemTagsProvider {
    public MATItemTagProvider(PackOutput output, CompletableFuture<Provider> registry, CompletableFuture<TagLookup<Block>> blocks, @Nullable ExistingFileHelper helper) {
        super(output, registry, blocks, MOD_ID, helper);
    }

    @Override
    protected void addTags(@NotNull Provider registry) {
        this.copy(MATBlockTags.FIR_LOGS, MATItemTags.FIR_LOGS);
        this.copy(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
        this.copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
        this.copy(BlockTags.LEAVES, ItemTags.LEAVES);
        this.copy(BlockTags.PLANKS, ItemTags.PLANKS);
        this.copy(BlockTags.STANDING_SIGNS, ItemTags.SIGNS);
        this.copy(BlockTags.CEILING_HANGING_SIGNS, ItemTags.HANGING_SIGNS);
        this.copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
        this.copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
        this.copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
        this.copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
        this.copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
        this.copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
        this.copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
        this.tag(MATItemTags.MORTARS)
                .add(MATItems.MORTAR.get());
        this.tag(MATItemTags.BLAZE_BLAST_BURNERS)
                .add(MATItems.BLAZE_BLAST_BURNER.get());
        this.tag(MATItemTags.BLAZE_BURNERS)
                .add(MATItems.BLAZE_BURNER.get())
                .addTag(MATItemTags.BLAZE_BLAST_BURNERS);
    }
}
