package enderdragon.magicandtaboo.data;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static enderdragon.magicandtaboo.MagicAndTabooMod.MOD_ID;

public class MATItemTagProvider extends ItemTagsProvider {
    public MATItemTagProvider(PackOutput output, CompletableFuture<Provider> registry, CompletableFuture<TagLookup<Block>> blocks, @Nullable ExistingFileHelper helper) {
        super(output, registry, blocks, MOD_ID, helper);
    }

    @Override
    protected void addTags(@NotNull Provider registry) {

    }
}
