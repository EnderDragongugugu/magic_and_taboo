package enderdragon.magic_and_taboo.data;

import enderdragon.magic_and_taboo.data.recipe.WorkHubRecipeBuilder;
import enderdragon.magic_and_taboo.init.MATBlocks;
import enderdragon.magic_and_taboo.init.MATItems;
import enderdragon.magic_and_taboo.tag.MATItemTags;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class MATRecipeProvider extends RecipeProvider {
    public MATRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> writer) {
        planksFromLog(writer, MATItems.FIR_PLANKS.get(), MATItemTags.FIR_LOGS, 4);
        woodFromLogs(writer, MATItems.FIR_WOOD.get(), MATBlocks.FIR_LOG.get());
        woodFromLogs(writer, MATItems.STRIPPED_FIR_WOOD.get(), MATBlocks.STRIPPED_FIR_LOG.get());
        hangingSign(writer, MATItems.FIR_HANGING_SIGN.get(), MATItems.STRIPPED_FIR_LOG.get());
        generateRecipes(writer, new BlockFamily.Builder(MATBlocks.FIR_PLANKS.get())
                .button(MATBlocks.FIR_BUTTON.get())
                .fence(MATBlocks.FIR_FENCE.get())
                .fenceGate(MATBlocks.FIR_FENCE_GATE.get())
                .pressurePlate(MATBlocks.FIR_PRESSURE_PLATE.get())
                .sign(MATBlocks.FIR_SIGN.get(), MATBlocks.FIR_WALL_SIGN.get())
                .slab(MATBlocks.FIR_SLAB.get())
                .stairs(MATBlocks.FIR_STAIRS.get())
                .door(MATBlocks.FIR_DOOR.get())
                .trapdoor(MATBlocks.FIR_TRAPDOOR.get())
                .recipeGroupPrefix("wooden")
                .recipeUnlockedBy("has_planks")
                .getFamily()
        );
        generateRecipes(writer, new BlockFamily.Builder(MATBlocks.GILDED_MARBLE.get())
                .wall(MATBlocks.GILDED_MARBLE_WALL.get())
                .stairs(MATBlocks.GILDED_MARBLE_STAIRS.get())
                .slab(MATBlocks.GILDED_MARBLE_SLAB.get())
                .polished(MATBlocks.POLISHED_GILDED_MARBLE.get())
                .chiseled(MATBlocks.CHISELED_GILDED_MARBLE.get())
                .getFamily()
        );
        generateRecipes(writer, new BlockFamily.Builder(MATBlocks.POLISHED_GILDED_MARBLE.get())
                .wall(MATBlocks.POLISHED_GILDED_MARBLE_WALL.get())
                .pressurePlate(MATBlocks.POLISHED_GILDED_MARBLE_PRESSURE_PLATE.get())
                .button(MATBlocks.POLISHED_GILDED_MARBLE_BUTTON.get())
                .stairs(MATBlocks.POLISHED_GILDED_MARBLE_STAIRS.get())
                .slab(MATBlocks.POLISHED_GILDED_MARBLE_SLAB.get())
                .getFamily()
        );
        stonecutterResultFromBase(writer, RecipeCategory.BUILDING_BLOCKS, MATBlocks.GILDED_MARBLE_SLAB.get(), MATBlocks.GILDED_MARBLE.get(), 2);
        stonecutterResultFromBase(writer, RecipeCategory.BUILDING_BLOCKS, MATBlocks.GILDED_MARBLE_STAIRS.get(), MATBlocks.GILDED_MARBLE.get());
        stonecutterResultFromBase(writer, RecipeCategory.DECORATIONS, MATBlocks.GILDED_MARBLE_WALL.get(), MATBlocks.GILDED_MARBLE.get());
        stonecutterResultFromBase(writer, RecipeCategory.BUILDING_BLOCKS, MATBlocks.POLISHED_GILDED_MARBLE.get(), MATBlocks.GILDED_MARBLE.get());
        stonecutterResultFromBase(writer, RecipeCategory.DECORATIONS, MATBlocks.POLISHED_GILDED_MARBLE_WALL.get(), MATBlocks.GILDED_MARBLE.get());
        stonecutterResultFromBase(writer, RecipeCategory.BUILDING_BLOCKS, MATBlocks.POLISHED_GILDED_MARBLE_SLAB.get(), MATBlocks.GILDED_MARBLE.get(), 2);
        stonecutterResultFromBase(writer, RecipeCategory.BUILDING_BLOCKS, MATBlocks.POLISHED_GILDED_MARBLE_STAIRS.get(), MATBlocks.GILDED_MARBLE.get());
        stonecutterResultFromBase(writer, RecipeCategory.BUILDING_BLOCKS, MATBlocks.CHISELED_GILDED_MARBLE.get(), MATBlocks.GILDED_MARBLE.get());
        stonecutterResultFromBase(writer, RecipeCategory.BUILDING_BLOCKS, MATBlocks.POLISHED_GILDED_MARBLE_SLAB.get(), MATBlocks.POLISHED_GILDED_MARBLE.get(), 2);
        stonecutterResultFromBase(writer, RecipeCategory.BUILDING_BLOCKS, MATBlocks.POLISHED_GILDED_MARBLE_STAIRS.get(), MATBlocks.POLISHED_GILDED_MARBLE.get());
        stonecutterResultFromBase(writer, RecipeCategory.DECORATIONS, MATBlocks.POLISHED_GILDED_MARBLE_WALL.get(), MATBlocks.POLISHED_GILDED_MARBLE.get());
        stonecutterResultFromBase(writer, RecipeCategory.BUILDING_BLOCKS, MATBlocks.CHISELED_GILDED_MARBLE.get(), MATBlocks.POLISHED_GILDED_MARBLE.get());
        new WorkHubRecipeBuilder(false, 100, 10, MATItems.FIR_SAPLING.get(), 1)
                .requires(Items.DRAGON_EGG)
                .requires(Items.DRAGON_HEAD)
                .withBlazeBurner(MATItems.BLAZE_BLAST_BURNER.get())
                .withContainer(Items.BUCKET)
                .unlockedBy("has_material", has(MATItems.BLAZE_BLAST_BURNER.get()))
                .save(writer);
    }
}
