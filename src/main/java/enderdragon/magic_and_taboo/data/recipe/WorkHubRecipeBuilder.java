package enderdragon.magic_and_taboo.data.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import enderdragon.magic_and_taboo.init.MATSerializers;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class WorkHubRecipeBuilder implements RecipeBuilder {
    private final Item result;
    private final int count;
    private final int duration;
    private final int experience;
    private final List<Ingredient> ingredients = new ObjectArrayList<>(6);
    private final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();
    private final boolean requireMortar;
    private Ingredient blazeBurner = Ingredient.EMPTY;
    private Ingredient container = Ingredient.EMPTY;
    @Nullable
    private String group;

    public WorkHubRecipeBuilder(boolean requireMortar, int duration, int experience, ItemLike result, int count) {
        this.requireMortar = requireMortar;
        this.duration = duration;
        this.experience = experience;
        this.result = result.asItem();
        this.count = count;
    }

    public static WorkHubRecipeBuilder slow(boolean requireMortar, int experience, ItemLike result, int count) {
        return new WorkHubRecipeBuilder(requireMortar, 800, experience, result, count);
    }

    public static WorkHubRecipeBuilder normal(boolean requireMortar, int experience, ItemLike result, int count) {
        return new WorkHubRecipeBuilder(requireMortar, 600, experience, result, count);
    }

    public static WorkHubRecipeBuilder quick(boolean requireMortar, int experience, ItemLike result, int count) {
        return new WorkHubRecipeBuilder(requireMortar, 400, experience, result, count);
    }

    public WorkHubRecipeBuilder requires(TagKey<Item> tag) {
        return this.requires(Ingredient.of(tag));
    }

    public WorkHubRecipeBuilder requires(ItemLike item) {
        return this.requires(item, 1);
    }

    public WorkHubRecipeBuilder requires(ItemLike item, int quantity) {
        return this.requires(Ingredient.of(item), quantity);
    }

    public WorkHubRecipeBuilder requires(Ingredient ingredient) {
        return this.requires(ingredient, 1);
    }

    public WorkHubRecipeBuilder requires(Ingredient ingredient, int quantity) {
        for (int i = 0; i < quantity; ++i) {
            this.ingredients.add(ingredient);
        }
        return this;
    }

    public WorkHubRecipeBuilder withBlazeBurner(TagKey<Item> tag) {
        return this.withBlazeBurner(Ingredient.of(tag));
    }

    public WorkHubRecipeBuilder withBlazeBurner(ItemLike burner) {
        return this.withBlazeBurner(Ingredient.of(burner));
    }

    public WorkHubRecipeBuilder withBlazeBurner(Ingredient burner) {
        this.blazeBurner = burner;
        return this;
    }

    public WorkHubRecipeBuilder withContainer(TagKey<Item> tag) {
        return this.withContainer(Ingredient.of(tag));
    }

    public WorkHubRecipeBuilder withContainer(ItemLike container) {
        return this.withContainer(Ingredient.of(container));
    }

    public WorkHubRecipeBuilder withContainer(Ingredient container) {
        this.container = container;
        return this;
    }

    @Override
    public WorkHubRecipeBuilder unlockedBy(String criterion, CriterionTriggerInstance trigger) {
        this.advancement.addCriterion(criterion, trigger);
        return this;
    }

    @Override
    public WorkHubRecipeBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    @Override
    public Item getResult() {
        return this.result;
    }

    @Override
    public void save(Consumer<FinishedRecipe> consumer, ResourceLocation identifier) {
        this.advancement.parent(ROOT_RECIPE_ADVANCEMENT).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(identifier)).rewards(AdvancementRewards.Builder.recipe(identifier)).requirements(RequirementsStrategy.OR);
        consumer.accept(new Result(
                identifier,
                this.group == null ? "" : this.group,
                this.requireMortar,
                this.duration,
                this.experience,
                this.result,
                this.count,
                this.ingredients,
                this.blazeBurner,
                this.container,
                this.advancement,
                identifier.withPrefix("recipes/work_hub/")
        ));
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final String group;
        private final boolean requireMortar;
        private final int duration;
        private final int experience;
        private final Item result;
        private final int count;
        private final List<Ingredient> ingredients;
        private final Ingredient blazeBurner;
        private final Ingredient container;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(
                ResourceLocation id,
                String group,
                boolean requireMortar,
                int duration,
                int experience,
                Item result,
                int count,
                List<Ingredient> ingredients,
                Ingredient blazeBurner,
                Ingredient container,
                Advancement.Builder advancement,
                ResourceLocation advancementId
        ) {
            this.id = id;
            this.group = group;
            this.requireMortar = requireMortar;
            this.duration = duration;
            this.experience = experience;
            this.result = result;
            this.count = count;
            this.ingredients = ingredients;
            this.blazeBurner = blazeBurner;
            this.container = container;
            this.advancement = advancement;
            this.advancementId = advancementId;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            if (!this.group.isEmpty()) {
                json.addProperty("group", this.group);
            }
            json.addProperty("work_time", this.duration);
            json.addProperty("experience", this.experience);
            json.addProperty("require_mortar", this.requireMortar);
            JsonArray ingredients = new JsonArray();
            for (Ingredient ingredient : this.ingredients) {
                ingredients.add(ingredient.toJson());
            }
            if (!this.blazeBurner.isEmpty()) {
                json.add("blaze_burner", this.blazeBurner.toJson());
            }
            if (!this.container.isEmpty()) {
                json.add("container", this.container.toJson());
            }
            json.add("ingredients", ingredients);
            JsonObject result = new JsonObject();
            result.addProperty("item", Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(this.result)).toString());
            if (this.count > 1) {
                result.addProperty("count", this.count);
            }
            json.add("result", result);
        }

        @Override
        public ResourceLocation getId() {
            return this.id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return MATSerializers.WORK_HUB_RECIPE_SERIALIZER.get();
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}
