package enderdragon.magicandtaboo.data;

import enderdragon.magicandtaboo.init.MATBlocks;
import enderdragon.magicandtaboo.init.MATItems;
import enderdragon.magicandtaboo.tag.MATItemTags;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
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
    }
}
