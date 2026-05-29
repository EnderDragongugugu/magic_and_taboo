package enderdragon.magic_and_taboo.data;

import com.google.gson.JsonObject;
import enderdragon.magic_and_taboo.client.particle.PedestalParticleType;
import enderdragon.magic_and_taboo.data.recipe.AlchemyMaterialRecipeBuilder;
import enderdragon.magic_and_taboo.data.recipe.PedestalRecipeBuilder;
import enderdragon.magic_and_taboo.data.recipe.WorkHubRecipeBuilder;
import enderdragon.magic_and_taboo.init.MATBlocks;
import enderdragon.magic_and_taboo.init.MATItems;
import enderdragon.magic_and_taboo.init.MATSerializers;
import enderdragon.magic_and_taboo.tag.MATItemTags;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

import static net.minecraft.data.recipes.RecipeBuilder.getDefaultRecipeId;

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
                .getFamily());
        generateRecipes(writer, new BlockFamily.Builder(MATBlocks.GILDED_MARBLE.get())
                .wall(MATBlocks.GILDED_MARBLE_WALL.get())
                .stairs(MATBlocks.GILDED_MARBLE_STAIRS.get())
                .slab(MATBlocks.GILDED_MARBLE_SLAB.get())
                .polished(MATBlocks.POLISHED_GILDED_MARBLE.get())
                .chiseled(MATBlocks.CHISELED_GILDED_MARBLE.get())
                .getFamily());
        generateRecipes(writer, new BlockFamily.Builder(MATBlocks.POLISHED_GILDED_MARBLE.get())
                .wall(MATBlocks.POLISHED_GILDED_MARBLE_WALL.get())
                .pressurePlate(MATBlocks.POLISHED_GILDED_MARBLE_PRESSURE_PLATE.get())
                .button(MATBlocks.POLISHED_GILDED_MARBLE_BUTTON.get())
                .stairs(MATBlocks.POLISHED_GILDED_MARBLE_STAIRS.get())
                .slab(MATBlocks.POLISHED_GILDED_MARBLE_SLAB.get())
                .getFamily());
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MATBlocks.ENCHANTED_CRUCIBLE.get(), 1)
                .define('X', Items.AMETHYST_SHARD)
                .define('Y', Blocks.CAULDRON)
                .pattern(" X ")
                .pattern("XYX")
                .pattern(" X ")
                .unlockedBy("has_material", has(Items.AMETHYST_SHARD))
                .save(writer, getSimpleRecipeName(MATBlocks.ENCHANTED_CRUCIBLE.get()));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MATItems.SACRIFICIAL_DAGGER.get(), 1)
                .define('X', MATItems.GROUND_MEAT.get())
                .define('Y', Items.IRON_SWORD)
                .define('Z', Items.GLASS_BOTTLE)
                .pattern("  X")
                .pattern("ZYX")
                .pattern("X  ")
                .unlockedBy("has_material", has(Items.IRON_SWORD))
                .save(writer, getSimpleRecipeName(MATItems.SACRIFICIAL_DAGGER.get()));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MATBlocks.WORK_HUB.get(), 1)
                .define('X', Items.IRON_INGOT)
                .define('Y', Blocks.CRAFTING_TABLE)
                .define('Z', MATBlocks.FIR_PLANKS.get())
                .pattern(" X ")
                .pattern("ZYZ")
                .pattern("Z Z")
                .unlockedBy("has_material", has(Blocks.CRAFTING_TABLE))
                .save(writer, getSimpleRecipeName(MATBlocks.WORK_HUB.get()));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MATBlocks.FIR_SAPLING.get(), 1)
                .define('Y', Blocks.SPRUCE_SAPLING)
                .define('Z', Items.WATER_BUCKET)
                .pattern("ZYZ")
                .unlockedBy("has_material", has(Blocks.SPRUCE_SAPLING))
                .save(writer, getSimpleRecipeName(MATBlocks.FIR_SAPLING.get()));
        stonecutterResultFromBase(writer, RecipeCategory.BUILDING_BLOCKS, MATBlocks.GILDED_MARBLE_SLAB.get(),
                MATBlocks.GILDED_MARBLE.get(), 2);
        stonecutterResultFromBase(writer, RecipeCategory.BUILDING_BLOCKS, MATBlocks.GILDED_MARBLE_STAIRS.get(),
                MATBlocks.GILDED_MARBLE.get());
        stonecutterResultFromBase(writer, RecipeCategory.DECORATIONS, MATBlocks.GILDED_MARBLE_WALL.get(),
                MATBlocks.GILDED_MARBLE.get());
        stonecutterResultFromBase(writer, RecipeCategory.BUILDING_BLOCKS,
                MATBlocks.POLISHED_GILDED_MARBLE.get(), MATBlocks.GILDED_MARBLE.get());
        stonecutterResultFromBase(writer, RecipeCategory.DECORATIONS,
                MATBlocks.POLISHED_GILDED_MARBLE_WALL.get(), MATBlocks.GILDED_MARBLE.get());
        stonecutterResultFromBase(writer, RecipeCategory.BUILDING_BLOCKS,
                MATBlocks.POLISHED_GILDED_MARBLE_SLAB.get(), MATBlocks.GILDED_MARBLE.get(), 2);
        stonecutterResultFromBase(writer, RecipeCategory.BUILDING_BLOCKS,
                MATBlocks.POLISHED_GILDED_MARBLE_STAIRS.get(), MATBlocks.GILDED_MARBLE.get());
        stonecutterResultFromBase(writer, RecipeCategory.BUILDING_BLOCKS,
                MATBlocks.CHISELED_GILDED_MARBLE.get(), MATBlocks.GILDED_MARBLE.get());
        stonecutterResultFromBase(writer, RecipeCategory.BUILDING_BLOCKS,
                MATBlocks.POLISHED_GILDED_MARBLE_SLAB.get(), MATBlocks.POLISHED_GILDED_MARBLE.get(), 2);
        stonecutterResultFromBase(writer, RecipeCategory.BUILDING_BLOCKS,
                MATBlocks.POLISHED_GILDED_MARBLE_STAIRS.get(), MATBlocks.POLISHED_GILDED_MARBLE.get());
        stonecutterResultFromBase(writer, RecipeCategory.DECORATIONS,
                MATBlocks.POLISHED_GILDED_MARBLE_WALL.get(), MATBlocks.POLISHED_GILDED_MARBLE.get());
        stonecutterResultFromBase(writer, RecipeCategory.BUILDING_BLOCKS,
                MATBlocks.CHISELED_GILDED_MARBLE.get(), MATBlocks.POLISHED_GILDED_MARBLE.get());
        // work hub
        // test
        new WorkHubRecipeBuilder(true, 100, 10, MATItems.FIR_SAPLING.get(), 1)
                .requires(Blocks.ICE)
                .requires(Blocks.ICE)
                .requires(Blocks.ICE)
                .requires(Blocks.ICE)
                .requires(Blocks.ICE)
                .requires(Blocks.ICE)
                .withBlazeBurner(MATItems.BLAZE_BLAST_BURNER.get())
                .withContainer(Blocks.SPRUCE_SAPLING)
                .unlockedBy("has_material", has(Blocks.SPRUCE_SAPLING))
                .save(writer);
        // 烈焰粉
        new WorkHubRecipeBuilder(true, 100, 10, Items.BLAZE_POWDER, 1)
                .requires(Items.MAGMA_BLOCK)
                .requires(Items.GUNPOWDER)
                .requires(Items.SUGAR)
                .requires(Items.SUGAR)
                .unlockedBy("has_material", has(Items.MAGMA_BLOCK))
                .save(writer);
        // 水银碎块
        new WorkHubRecipeBuilder(true, 400, 10, MATItems.MERCURY_SLAG.get(), 2)
                .requires(MATItems.MERCURY_ORE.get())
                .unlockedBy("has_material", has(MATItems.MERCURY_ORE.get()))
                .save(writer);
        // 碎肉
        new WorkHubRecipeBuilder(true, 100, 10, MATItems.GROUND_MEAT.get(), 1)
                .requires(MATItemTags.RAW_MEAT)
                .unlockedBy("has_material", has(MATItemTags.RAW_MEAT))
                .save(writer);
        // 研钵
        new WorkHubRecipeBuilder(false, 100, 10, MATItems.MORTAR.get(), 1)
                .requires(Items.BOWL)
                .requires(Items.STICK)
                .unlockedBy("has_material", has(Items.BOWL))
                .save(writer);
        // 烈焰灯
        new WorkHubRecipeBuilder(false, 100, 10, MATItems.BLAZE_BURNER.get(), 1)
                .requires(Items.BLAZE_POWDER)
                .withContainer(Items.GLASS_BOTTLE)
                .unlockedBy("has_material", has(Items.BLAZE_POWDER))
                .save(writer);
        // 烈焰喷灯
        new WorkHubRecipeBuilder(false, 100, 10, MATItems.BLAZE_BLAST_BURNER.get(), 1)
                .requires(Items.BLAZE_POWDER)
                .requires(Items.BLAZE_POWDER)
                .withContainer(MATItems.BLAZE_BURNER.get())
                .unlockedBy("has_material", has(MATItems.BLAZE_BURNER.get()))
                .save(writer);
        // 药剂瓶
        new WorkHubRecipeBuilder(false, 300, 10, MATItems.GLASS_POTION_BOTTLE.get(), 1)
                .requires(Blocks.GLASS)
                .requires(Blocks.GLASS)
                .requires(Blocks.GLASS)
                .withBlazeBurner(MATItems.BLAZE_BURNER.get())
                .unlockedBy("has_material", has(Items.GLASS))
                .save(writer);
        // glow 药剂瓶
        new WorkHubRecipeBuilder(false, 300, 10, MATItems.GLASS_POTION_BOTTLE_GLOW.get(), 1)
                .requires(Blocks.GLOWSTONE)
                .requires(Items.GLOWSTONE_DUST)
                .requires(Items.GLOWSTONE_DUST)
                .withBlazeBurner(MATItems.BLAZE_BURNER.get())
                .withContainer(MATItems.GLASS_POTION_BOTTLE.get())
                .unlockedBy("has_material", has(MATItems.GLASS_POTION_BOTTLE_GLOW.get()))
                .save(writer);
        // red 药剂瓶
        new WorkHubRecipeBuilder(false, 300, 10, MATItems.GLASS_POTION_BOTTLE_RED.get(), 1)
                .requires(Blocks.REDSTONE_BLOCK)
                .requires(Items.REDSTONE)
                .requires(Items.REDSTONE)
                .withBlazeBurner(MATItems.BLAZE_BURNER.get())
                .withContainer(MATItems.GLASS_POTION_BOTTLE.get())
                .unlockedBy("has_material", has(MATItems.GLASS_POTION_BOTTLE_RED.get()))
                .save(writer);
        // 蜂蜜桶
        new WorkHubRecipeBuilder(true, 100, 10, MATItems.HONEY_BUCKET.get(), 1)
                .requires(Blocks.HONEY_BLOCK)
                .withBlazeBurner(MATItems.BLAZE_BURNER.get())
                .withContainer(MATItems.GLASS_POTION_BOTTLE.get())
                .unlockedBy("has_material", has(MATItems.HONEY_BUCKET.get()))
                .save(writer);
        // 溶液-水
        new AlchemyMaterialRecipeBuilder(true, 100, 10, MATItems.ALCHEMY_SOLUTION.get(), 1)
                .requires(Items.WATER_BUCKET)
                .withBlazeBurner(MATItems.BLAZE_BURNER.get())
                .withContainer(Items.GLASS_BOTTLE)
                .unlockedBy("has_material", has(MATItems.ALCHEMY_SOLUTION.get()))
                .save(writer, getDefaultRecipeId(MATItems.ALCHEMY_SOLUTION.get()) + "_0");

        // 溶液-植物
        new AlchemyMaterialRecipeBuilder(true, 100, 10, MATItems.ALCHEMY_SOLUTION.get(), 1)
                .requires(MATItems.PLANT_EXTRACT_BUCKET.get())
                .withBlazeBurner(MATItems.BLAZE_BURNER.get())
                .withContainer(Items.GLASS_BOTTLE)
                .unlockedBy("has_material", has(MATItems.ALCHEMY_SOLUTION.get()))
                .save(writer, getDefaultRecipeId(MATItems.ALCHEMY_SOLUTION.get()) + "_1");

        // 溶液-哈机密
        new AlchemyMaterialRecipeBuilder(true, 100, 10, MATItems.ALCHEMY_SOLUTION.get(), 1)
                .requires(MATItems.HONEY_BUCKET.get())
                .withBlazeBurner(MATItems.BLAZE_BURNER.get())
                .withContainer(Items.BOWL)
                .unlockedBy("has_material", has(MATItems.ALCHEMY_SOLUTION.get()))
                .save(writer, getDefaultRecipeId(MATItems.ALCHEMY_SOLUTION.get()) + "_2");
        // 糊
        new AlchemyMaterialRecipeBuilder(true, 100, 10, MATItems.ALCHEMY_PASTE.get(), 1)
                .requires(Items.SLIME_BALL)
                .withBlazeBurner(MATItems.BLAZE_BURNER.get())
                .withContainer(Items.BOWL)
                .unlockedBy("has_material", has(MATItems.ALCHEMY_PASTE.get()))
                .save(writer);
        // Magic Perfusion Pedestal Recipes
        // 金苹果合成示例 - 螺旋粒子效果
        new PedestalRecipeBuilder(600, 50, Items.ENCHANTED_GOLDEN_APPLE, 1)
                .requires(Items.GOLD_BLOCK)
                .requires(Items.GOLD_BLOCK)
                .requires(Items.GOLD_BLOCK)
                .requires(Items.GOLD_BLOCK)
                .requires(Items.GOLD_BLOCK)
                .requires(Items.GOLD_BLOCK)
                .requires(Items.GOLD_BLOCK)
                .requires(Items.GOLD_BLOCK)
                .withCore(Items.GOLDEN_APPLE)
                .withParticle(PedestalParticleType.SPIRAL)
                .unlockedBy("has_material", has(Items.GOLDEN_APPLE))
                .save(writer);

        // 钻石合成示例 - 上升粒子效果
        new PedestalRecipeBuilder(400, 30, Items.DIAMOND, 1)
                .requires(Items.COAL)
                .requires(Items.COAL)
                .requires(Items.COAL)
                .requires(Items.COAL)
                .withCore(Items.COAL_BLOCK)
                .withParticle(PedestalParticleType.RISE)
                .unlockedBy("has_material", has(Items.COAL))
                .save(writer);

        // 通用炼金粉末配方 - 自动匹配所有有 alchemy_element 定义的物品
        createUniversalPowderRecipe(writer);
    }

    /**
     * 创建通用炼金粉末配方
     * 这个配方会自动匹配任何注册了 alchemy_element 的物品
     * 不需要为每个物品单独创建配方
     */
    private void createUniversalPowderRecipe(Consumer<FinishedRecipe> writer) {
        var recipeId = new ResourceLocation("magic_and_taboo", "alchemy_powder_universal");
        var result = new FinishedRecipe() {
            @Override
            public void serializeRecipeData(JsonObject json) {
                json.addProperty("type", "magic_and_taboo:alchemy_powder");
                json.addProperty("experience", 10);
                json.addProperty("require_mortar", true);
                json.addProperty("work_time", 100);
                json.addProperty("apply_antagonism", true);

                // 催化剂（可选，支持元素增强剂标签）
                JsonObject catalyst = new JsonObject();
                catalyst.addProperty("tag", "magic_and_taboo:element_enhancers");
                json.add("catalyst", catalyst);

                // 烈焰燃烧器
                JsonObject burner = new JsonObject();
                burner.addProperty("tag", "magic_and_taboo:blaze_burners");
                json.add("blaze_burner", burner);

                // 输出
                JsonObject result = new JsonObject();
                result.addProperty("item", "magic_and_taboo:alchemy_powder");
                json.add("result", result);
            }

            @Override
            public ResourceLocation getId() {
                return recipeId;
            }

            @Override
            public RecipeSerializer<?> getType() {
                return MATSerializers.ALCHEMY_POWDER_SERIALIZER.get();
            }

            @org.jetbrains.annotations.Nullable
            @Override
            public JsonObject serializeAdvancement() {
                return null;
            }

            @org.jetbrains.annotations.Nullable
            @Override
            public ResourceLocation getAdvancementId() {
                return null;
            }
        };

        writer.accept(result);
    }
}
