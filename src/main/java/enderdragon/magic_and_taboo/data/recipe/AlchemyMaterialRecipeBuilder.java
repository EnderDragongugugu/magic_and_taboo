package enderdragon.magic_and_taboo.data.recipe;

import com.google.gson.JsonObject;
import enderdragon.magic_and_taboo.init.MATSerializers;
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
import java.util.Objects;
import java.util.function.Consumer;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class AlchemyMaterialRecipeBuilder implements RecipeBuilder {
    private final Item result;
    private final int count;
    private final int duration;
    private final int experience;
    private Ingredient ingredient = Ingredient.EMPTY;
    private final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();
    private final boolean requireMortar;
    private Ingredient blazeBurner = Ingredient.EMPTY;
    private Ingredient container = Ingredient.EMPTY;
    @Nullable
    private String group;
    private boolean allowMultipleMaterials = false;
    private boolean applyAntagonism = true;
    private float baseMaterialLoss = 0.1f;

    public AlchemyMaterialRecipeBuilder(boolean requireMortar, int duration, int experience, ItemLike result, int count) {
        this.requireMortar = requireMortar;
        this.duration = duration;
        this.experience = experience;
        this.result = result.asItem();
        this.count = count;
    }

    public static AlchemyMaterialRecipeBuilder slow(boolean requireMortar, int experience, ItemLike result, int count) {
        return new AlchemyMaterialRecipeBuilder(requireMortar, 800, experience, result, count);
    }

    public static AlchemyMaterialRecipeBuilder normal(boolean requireMortar, int experience, ItemLike result, int count) {
        return new AlchemyMaterialRecipeBuilder(requireMortar, 600, experience, result, count);
    }

    public static AlchemyMaterialRecipeBuilder quick(boolean requireMortar, int experience, ItemLike result, int count) {
        return new AlchemyMaterialRecipeBuilder(requireMortar, 400, experience, result, count);
    }

    public AlchemyMaterialRecipeBuilder requires(ItemLike item) {
        return this.requires(Ingredient.of(item));
    }

    public AlchemyMaterialRecipeBuilder requires(Ingredient ingredient) {
        this.ingredient = ingredient;
        return this;
    }

    public AlchemyMaterialRecipeBuilder withBlazeBurner(TagKey<Item> tag) {
        return this.withBlazeBurner(Ingredient.of(tag));
    }

    public AlchemyMaterialRecipeBuilder withBlazeBurner(ItemLike burner) {
        return this.withBlazeBurner(Ingredient.of(burner));
    }

    public AlchemyMaterialRecipeBuilder withBlazeBurner(Ingredient burner) {
        this.blazeBurner = burner;
        return this;
    }

    public AlchemyMaterialRecipeBuilder withContainer(TagKey<Item> tag) {
        return this.withContainer(Ingredient.of(tag));
    }

    public AlchemyMaterialRecipeBuilder withContainer(ItemLike container) {
        return this.withContainer(Ingredient.of(container));
    }

    public AlchemyMaterialRecipeBuilder withContainer(Ingredient container) {
        this.container = container;
        return this;
    }

    /**
     * 允许使用多个炼金材料作为基础
     */
    public AlchemyMaterialRecipeBuilder allowMultipleMaterials(boolean allow) {
        this.allowMultipleMaterials = allow;
        return this;
    }

    /**
     * 是否应用元素对抗系统
     */
    public AlchemyMaterialRecipeBuilder applyAntagonism(boolean apply) {
        this.applyAntagonism = apply;
        return this;
    }

    /**
     * 设置基础材料的损失率（0-1）
     */
    public AlchemyMaterialRecipeBuilder baseMaterialLoss(float loss) {
        this.baseMaterialLoss = Math.max(0, Math.min(1, loss));
        return this;
    }

    @Override
    public AlchemyMaterialRecipeBuilder unlockedBy(String criterion, CriterionTriggerInstance trigger) {
        this.advancement.addCriterion(criterion, trigger);
        return this;
    }

    @Override
    public AlchemyMaterialRecipeBuilder group(@Nullable String group) {
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
                this.ingredient,
                this.blazeBurner,
                this.container,
                this.allowMultipleMaterials,
                this.applyAntagonism,
                this.baseMaterialLoss,
                this.advancement,
                identifier.withPrefix("recipes/work_hub")
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
        private final Ingredient ingredient;
        private final Ingredient blazeBurner;
        private final Ingredient container;
        private final boolean allowMultipleMaterials;
        private final boolean applyAntagonism;
        private final float baseMaterialLoss;
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
                Ingredient ingredient,
                Ingredient blazeBurner,
                Ingredient container,
                boolean allowMultipleMaterials,
                boolean applyAntagonism,
                float baseMaterialLoss,
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
            this.ingredient = ingredient;
            this.blazeBurner = blazeBurner;
            this.container = container;
            this.allowMultipleMaterials = allowMultipleMaterials;
            this.applyAntagonism = applyAntagonism;
            this.baseMaterialLoss = baseMaterialLoss;
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
            if (!this.blazeBurner.isEmpty()) {
                json.add("blaze_burner", this.blazeBurner.toJson());
            }
            if (!this.container.isEmpty()) {
                json.add("container", this.container.toJson());
            }
            json.add("ingredient", this.ingredient.toJson());

            // 新增配置选项
            if (this.allowMultipleMaterials) {
                json.addProperty("allow_multiple_materials", true);
            }
            if (!this.applyAntagonism) {
                json.addProperty("apply_antagonism", false);
            }
            if (this.baseMaterialLoss != 0.1f) {
                json.addProperty("base_material_loss", this.baseMaterialLoss);
            }

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
            return MATSerializers.ALCHEMY_MATERIAL_SERIALIZER.get();
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
