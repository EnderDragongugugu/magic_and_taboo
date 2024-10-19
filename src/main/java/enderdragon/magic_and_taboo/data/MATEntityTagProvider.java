package enderdragon.magic_and_taboo.data;

import enderdragon.magic_and_taboo.tag.MATEntityTags;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static enderdragon.magic_and_taboo.MagicAndTabooMod.MOD_ID;

public class MATEntityTagProvider extends EntityTypeTagsProvider {
    public MATEntityTagProvider(PackOutput output, CompletableFuture<Provider> registry, @Nullable ExistingFileHelper helper) {
        super(output, registry, MOD_ID, helper);
    }

    @Override
    protected void addTags(@NotNull Provider registry) {
        this.tag(MATEntityTags.ARBOREAL).add(EntityType.OCELOT).add(EntityType.PARROT);
    }
}
