package enderdragon.magic_and_taboo.crafting;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import enderdragon.magic_and_taboo.block.entity.WorkHubBlockEntity;
import enderdragon.magic_and_taboo.init.MATRecipeTypes;
import enderdragon.magic_and_taboo.init.MATSerializers;
import enderdragon.magic_and_taboo.tag.MATItemTags;
import enderdragon.magic_and_taboo.util.IngredientUtil;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.util.RecipeMatcher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public record WorkHubRecipe(
        ImmutableList<Ingredient> ingredients,
        ResourceLocation id,
        String group,
        ItemStack output,
        Ingredient container,
        Ingredient burner,
        float experience,
        int workTime,
        boolean requireMortar
) implements Recipe<WorkHubBlockEntity> {
    private static final Logger LOGGER = LogManager.getLogger();

    public WorkHubRecipe(
            ResourceLocation id,
            ImmutableList<Ingredient> ingredients,
            Ingredient container,
            Ingredient burner,
            ItemStack output,
            String group,
            float experience,
            int workTime,
            boolean requireMortar
    ) {
        this(
                ingredients,
                id,
                group,
                output,
                container.isEmpty() ? Ingredient.of(output.getCraftingRemainingItem()) : container,
                burner,
                experience,
                workTime,
                requireMortar
        );
    }

    @Override
    public boolean matches(WorkHubBlockEntity hub, Level level) {
        if (this.requireMortar && !hub.getStackInSlot(0).is(MATItemTags.MORTARS)) return false;
        if (!this.burner.isEmpty() && !this.burner.test(hub.getStackInSlot(1))) return false;
        var inputs = new ArrayList<ItemStack>(6);
        for (int i = 2; i <= 7; ++i) {
            var stack = hub.getItem(i);
            if (!stack.isEmpty()) {
                inputs.add(stack);
            }
        }
        boolean temp = RecipeMatcher.findMatches(inputs, this.ingredients) != null;
        LOGGER.debug("test recipe {}: {}", this.id, temp);
        return temp;
    }

    @Override
    public @NotNull ItemStack assemble(WorkHubBlockEntity hub, RegistryAccess access) {
        return this.output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= this.ingredients.size();
    }

    @Override
    public ItemStack getResultItem(RegistryAccess access) {
        return this.output;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return MATSerializers.WORK_HUB_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return MATRecipeTypes.WORK_HUB_RECIPE_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<WorkHubRecipe> {
        @Override
        public WorkHubRecipe fromJson(ResourceLocation id, JsonObject recipe) {
            final ImmutableList<Ingredient> ingredients = IngredientUtil.parse(GsonHelper.getAsJsonArray(recipe, "ingredients"));
            if (ingredients.isEmpty()) throw new JsonParseException("No ingredients for work hub recipe");
            if (ingredients.size() > WorkHubBlockEntity.MAX_SIZE)
                throw new JsonParseException("ingredients too many, the max is " + WorkHubBlockEntity.MAX_SIZE);
            return new WorkHubRecipe(
                    id,
                    ingredients,
                    IngredientUtil.parse(recipe, "container"),
                    IngredientUtil.parse(recipe, "blaze_burner"),
                    GsonHelper.isValidNode(recipe, "result")
                            ? CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(recipe, "result"), true)
                            : ItemStack.EMPTY,
                    GsonHelper.getAsString(recipe, "group", ""),
                    GsonHelper.getAsFloat(recipe, "experience", 0.0F),
                    GsonHelper.getAsInt(recipe, "work_time", 200),
                    GsonHelper.getAsBoolean(recipe, "requireMortar", false)
            );
        }

        @Override
        public @Nullable WorkHubRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            int size = buffer.readVarInt();
            var ingredients = new Ingredient[size];
            for (int i = 0, j = 0; i < size; ++i) {
                ingredients[j++] = Ingredient.fromNetwork(buffer);
            }
            return new WorkHubRecipe(
                    id,
                    ImmutableList.copyOf(ingredients),
                    Ingredient.fromNetwork(buffer),
                    Ingredient.fromNetwork(buffer),
                    buffer.readItem(),
                    buffer.readUtf(),
                    buffer.readFloat(),
                    buffer.readVarInt(),
                    buffer.readBoolean()
            );
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, WorkHubRecipe recipe) {
            buffer.writeVarInt(recipe.ingredients.size());
            for (var ingredient : recipe.ingredients) {
                ingredient.toNetwork(buffer);
            }
            recipe.container.toNetwork(buffer);
            recipe.burner.toNetwork(buffer);
            buffer.writeItem(recipe.output);
            buffer.writeUtf(recipe.group);
            buffer.writeBoolean(recipe.requireMortar);
            buffer.writeFloat(recipe.experience);
            buffer.writeVarInt(recipe.workTime);
        }
    }
}
