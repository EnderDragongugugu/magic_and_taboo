package enderdragon.magicandtaboo.data;

import enderdragon.magicandtaboo.init.MATBlocks;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static enderdragon.magicandtaboo.MagicAndTabooMod.MOD_ID;

public class MATBlockTagProvider extends BlockTagsProvider {
    public MATBlockTagProvider(PackOutput output, CompletableFuture<Provider> registry, @Nullable ExistingFileHelper helper) {
        super(output, registry, MOD_ID, helper);
    }

    @Override
    protected void addTags(@NotNull Provider registry) {
        this.tag(BlockTags.NEEDS_IRON_TOOL).add(MATBlocks.MERCURY_ORE.get());
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(MATBlocks.MERCURY_ORE.get());
    }
}
