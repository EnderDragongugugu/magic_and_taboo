package enderdragon.magic_and_taboo.data.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import enderdragon.magic_and_taboo.client.particle.PedestalParticleType;
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
public class PedestalRecipeBuilder implements RecipeBuilder {
    private final Item result;
    private final int count;
    private final int time;
    private final int magic;
    private final List<Ingredient> ingredients = new ObjectArrayList<>(8);
    private final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();
    private Ingredient core = Ingredient.EMPTY;
    private PedestalParticleType particleType = PedestalParticleType.NONE;
    @Nullable
    private String group;

    public PedestalRecipeBuilder(int time, int magic, ItemLike result, int count) {
        this.time = time;
        this.magic = magic;
        this.result = result.asItem();
        this.count = count;
    }

    public static PedestalRecipeBuilder slow(int magic, ItemLike result, int count) {
        return new PedestalRecipeBuilder(800, magic, result, count);
    }

    public static PedestalRecipeBuilder normal(int magic, ItemLike result, int count) {
        return new PedestalRecipeBuilder(600, magic, result, count);
    }

    public static PedestalRecipeBuilder quick(int magic, ItemLike result, int count) {
        return new PedestalRecipeBuilder(400, magic, result, count);
    }

    public PedestalRecipeBuilder requires(TagKey<Item> tag) {
        return this.requires(Ingredient.of(tag));
    }

    public PedestalRecipeBuilder requires(ItemLike item) {
        return this.requires(item, 1);
    }

    public PedestalRecipeBuilder requires(ItemLike item, int quantity) {
        return this.requires(Ingredient.of(item), quantity);
    }

    public PedestalRecipeBuilder requires(Ingredient ingredient) {
        return this.requires(ingredient, 1);
    }

    public PedestalRecipeBuilder requires(Ingredient ingredient, int quantity) {
        for (int i = 0; i < quantity; ++i) {
            this.ingredients.add(ingredient);
        }
        return this;
    }

    public PedestalRecipeBuilder withCore(TagKey<Item> tag) {
        return this.withCore(Ingredient.of(tag));
    }

    public PedestalRecipeBuilder withCore(ItemLike core) {
        return this.withCore(Ingredient.of(core));
    }

    public PedestalRecipeBuilder withCore(Ingredient core) {
        this.core = core;
        return this;
    }

    public PedestalRecipeBuilder withParticle(PedestalParticleType particleType) {
        this.particleType = particleType;
        return this;
    }

    @Override
    public PedestalRecipeBuilder unlockedBy(String criterion, CriterionTriggerInstance trigger) {
        this.advancement.addCriterion(criterion, trigger);
        return this;
    }

    @Override
    public PedestalRecipeBuilder group(@Nullable String group) {
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
                this.time,
                this.magic,
                this.result,
                this.count,
                this.ingredients,
                this.core,
                this.particleType,
                this.advancement,
                identifier.withPrefix("recipes/pedestal/")
        ));
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final String group;
        private final int time;
        private final int magic;
        private final Item result;
        private final int count;
        private final List<Ingredient> ingredients;
        private final Ingredient core;
        private final PedestalParticleType particleType;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(
                ResourceLocation id,
                String group,
                int time,
                int magic,
                Item result,
                int count,
                List<Ingredient> ingredients,
                Ingredient core,
                PedestalParticleType particleType,
                Advancement.Builder advancement,
                ResourceLocation advancementId
        ) {
            this.id = id;
            this.group = group;
            this.time = time;
            this.magic = magic;
            this.result = result;
            this.count = count;
            this.ingredients = ingredients;
            this.core = core;
            this.particleType = particleType;
            this.advancement = advancement;
            this.advancementId = advancementId;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            if (!this.group.isEmpty()) {
                json.addProperty("group", this.group);
            }
            json.addProperty("time", this.time);
            json.addProperty("magic", this.magic);
            if (this.particleType != PedestalParticleType.NONE) {
                json.addProperty("particle", this.particleType.getSerializedName());
            }
            JsonArray ingredients = new JsonArray();
            for (Ingredient ingredient : this.ingredients) {
                ingredients.add(ingredient.toJson());
            }
            if (!this.core.isEmpty()) {
                json.add("core", this.core.toJson());
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
            return MATSerializers.PEDESTAL_RECIPE_SERIALIZER.get();
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
