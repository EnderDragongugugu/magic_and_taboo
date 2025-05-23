package enderdragon.magic_and_taboo.crafting;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import enderdragon.magic_and_taboo.block.MagicPerfusionPedestalBlock;
import enderdragon.magic_and_taboo.block.entity.MagicPerfusionPedestalBlockEntity;
import enderdragon.magic_and_taboo.block.entity.WorkHubBlockEntity;
import enderdragon.magic_and_taboo.init.MATRecipeTypes;
import enderdragon.magic_and_taboo.init.MATSerializers;
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
import org.jetbrains.annotations.Nullable;

public record PedestalRecipe(
        ResourceLocation id,
        ImmutableList<Ingredient> ingredients,
        Ingredient core,
        ItemStack output,
        String group,
        int time,
        int magic
) implements Recipe<MagicPerfusionPedestalBlockEntity> {
    public PedestalRecipe(
            ResourceLocation id,
            ImmutableList<Ingredient> ingredients,
            Ingredient core,
            ItemStack output,
            String group,
            int time,
            int magic
    ) {
        this.id = id;
        this.ingredients = ingredients;
        this.core = core.isEmpty() ? Ingredient.of(output.getCraftingRemainingItem()) : core;
        this.output = output;
        this.group = group;
        this.time = time;
        this.magic = magic;
    }

    @Override
    public boolean matches(MagicPerfusionPedestalBlockEntity pContainer, Level pLevel) {
        return false;
    }

    @Override
    public ItemStack assemble(MagicPerfusionPedestalBlockEntity pContainer, RegistryAccess pRegistryAccess) {
        return this.output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= this.ingredients.size();
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return this.output;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return MATSerializers.PEDESTAL_RECIPE_SERIALIZER.get();
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    @Override
    public RecipeType<?> getType() {
        return MATRecipeTypes.PEDESTAL_RECIPE_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<PedestalRecipe> {

        @Override
        public PedestalRecipe fromJson(ResourceLocation id, JsonObject recipe) {
            final ImmutableList<Ingredient> ingredients = IngredientUtil.parse(GsonHelper.getAsJsonArray(recipe, "ingredients"));
            if (ingredients.isEmpty()) throw new JsonParseException("No ingredients for pedestal recipe");
            if (ingredients.size() > WorkHubBlockEntity.MAX_SIZE)
                throw new JsonParseException("ingredients too many, the max is " + MagicPerfusionPedestalBlock.POS_LIST.length);
            return new PedestalRecipe(
                    id,
                    ingredients,
                    IngredientUtil.parse(recipe, "core"),
                    GsonHelper.isValidNode(recipe, "result")
                            ? CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(recipe, "result"), true)
                            : ItemStack.EMPTY,
                    GsonHelper.getAsString(recipe, "group", ""),
                    GsonHelper.getAsInt(recipe, "time", 200),
                    GsonHelper.getAsInt(recipe, "magic", 0)
            );
        }

        @Override
        public @Nullable PedestalRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            int size = buffer.readVarInt();
            var ingredients = new Ingredient[size];
            for (int i = 0, j = 0; i < size; ++i) {
                ingredients[j++] = Ingredient.fromNetwork(buffer);
            }
            return new PedestalRecipe(
                    id,
                    ImmutableList.copyOf(ingredients),
                    Ingredient.fromNetwork(buffer),
                    buffer.readItem(),
                    buffer.readUtf(),
                    buffer.readVarInt(),
                    buffer.readVarInt()
            );
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, PedestalRecipe recipe) {
            buffer.writeVarInt(recipe.ingredients.size());
            for (var ingredient : recipe.ingredients) {
                ingredient.toNetwork(buffer);
            }
            recipe.core.toNetwork(buffer);
            buffer.writeItem(recipe.output);
            buffer.writeUtf(recipe.group);
            buffer.writeVarInt(recipe.time);
            buffer.writeVarInt(recipe.magic);
        }
    }
}
