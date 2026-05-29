package enderdragon.magic_and_taboo.crafting;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;
import enderdragon.magic_and_taboo.block.entity.WorkHubBlockEntity;
import enderdragon.magic_and_taboo.init.MATCapabilities;
import enderdragon.magic_and_taboo.init.MATRecipeTypes;
import enderdragon.magic_and_taboo.init.MATSerializers;
import enderdragon.magic_and_taboo.registry.AlchemyElement;
import enderdragon.magic_and_taboo.registry.Element;
import enderdragon.magic_and_taboo.tag.MATItemTags;
import enderdragon.magic_and_taboo.util.CapabilityUtil;
import enderdragon.magic_and_taboo.util.IngredientUtil;
import it.unimi.dsi.fastutil.floats.FloatUnaryOperator;
import it.unimi.dsi.fastutil.objects.Reference2FloatOpenHashMap;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public class AlchemyMaterialRecipe extends WorkHubRecipe {
    private static final Logger LOGGER = LogManager.getLogger();

    Ingredient ingredient;
    boolean allowMultipleMaterials;
    boolean applyAntagonism;
    float baseMaterialLoss;

    public AlchemyMaterialRecipe(
            Ingredient ingredient,
            ResourceLocation id,
            Ingredient container,
            Ingredient burner,
            ItemStack output,
            String group,
            float experience,
            int workTime,
            boolean requireMortar,
            boolean allowMultipleMaterials,
            boolean applyAntagonism,
            float baseMaterialLoss) {
        super(id, ImmutableList.of(), container, burner, output, group, experience, workTime, requireMortar);
        this.ingredient = ingredient;
        this.allowMultipleMaterials = allowMultipleMaterials;
        this.applyAntagonism = applyAntagonism;
        this.baseMaterialLoss = baseMaterialLoss;
    }

    @Override
    public boolean matches(WorkHubBlockEntity hub, Level level) {
        // 检查工具
        if (this.requireMortar != hub.getStackInSlot(0).is(MATItemTags.MORTARS)) return false;
        if (this.burner.isEmpty() && this.burner.isEmpty() != this.burner.test(hub.getStackInSlot(1))) {
            return false;
        } else if (!this.burner.test(hub.getStackInSlot(1))) {
            return false;
        }

        int alchemyMaterialCount = 0;
        boolean hasValidIngredient = false;

        for (int i = 2; i <= 7; i++) {
            ItemStack stack = hub.getItem(i);
            if (stack.isEmpty()) continue;

            if (stack.is(MATItemTags.IS_ALCHEMY_MATERIALS)) {
                alchemyMaterialCount++;
                // 如果不允许多个材料，且已有炼金材料，则失败
                if (!allowMultipleMaterials && alchemyMaterialCount > 1) {
                    return false;
                }
            } else if (this.ingredient.test(stack)) {
                hasValidIngredient = true;
            } else {
                // 无效材料
                return false;
            }
        }

        // 至少需要一个炼金材料作为基础，以及至少一个有效成分
        return alchemyMaterialCount >= 1 && hasValidIngredient;
    }

    @Override
    public @NotNull ItemStack assemble(WorkHubBlockEntity hub, RegistryAccess access) {
        var concentrations = new Reference2FloatOpenHashMap<Element>();
        var output = this.output.copy();
        var storage = CapabilityUtil.getCapability(output, MATCapabilities.ELEMENT_STORAGE);
        if (storage == null) return output;

        // 第一步：收集基础炼金材料中的元素（继承，带损耗）
        for (int i = 2; i <= 7; i++) {
            ItemStack stack = hub.getItem(i);
            if (!stack.isEmpty() && stack.is(MATItemTags.IS_ALCHEMY_MATERIALS)) {
                var source = CapabilityUtil.getCapability(stack, MATCapabilities.ELEMENT_SOURCE);
                if (source != null) {
                    float retentionRate = 1.0f - baseMaterialLoss;
                    for (var entry : source.getConcentrations().reference2FloatEntrySet()) {
                        concentrations.addTo(entry.getKey(), entry.getFloatValue() * retentionRate);
                    }
                }
            }
        }

        // 第二步：添加新材料的元素（带修正）
        for (int i = 2; i <= 7; i++) {
            ItemStack stack = hub.getItem(i);
            if (!stack.isEmpty() && !stack.is(MATItemTags.IS_ALCHEMY_MATERIALS)) {
                var instance = AlchemyElement.fromStack(access, stack);
                if (instance != null) {
                    var modifier = getConcentrationModifier(stack);
                    for (var entry : instance.concentrations().object2FloatEntrySet()) {
                        concentrations.addTo(entry.getKey().value(),
                            modifier.apply(entry.getFloatValue()));
                    }
                }
            }
        }

        // 第三步：元素对抗（如果启用）
        if (applyAntagonism && !concentrations.isEmpty()) {
            Element.antagonize(concentrations);
        }

        storage.setConcentrations(concentrations);
        return output;
    }

    /**
     * 获取物品的浓度修正器
     * 根据物品的标签或类型返回不同的修正器
     */
    private ConcentrationModifier getConcentrationModifier(ItemStack item) {
        // 元素增强剂：增加浓度
        if (item.is(MATItemTags.ELEMENT_ENHANCERS)) {
            return new ConcentrationModifier(1.25f, 0, null);
        }
        // 元素稀释剂：减少浓度
        if (item.is(MATItemTags.ELEMENT_Diluents)) {
            return new ConcentrationModifier(0.75f, 0, null);
        }
        // 元素稳定剂：固定增量
        if (item.is(MATItemTags.ELEMENT_STABILIZERS)) {
            return new ConcentrationModifier(1.0f, 5.0f, null);
        }

        // 特殊物品处理
        if (item.is(Items.FLINT)) {
            return new ConcentrationModifier(1.25f, 0, null);
        }
        if (item.is(Items.SLIME_BALL)) {
            return new ConcentrationModifier(1.0f, 10.0f, null);
        }
        if (item.is(Items.SUGAR)) {
            return new ConcentrationModifier(0.9f, 0, v -> v * 0.8f + 2.0f);
        }
        if (item.is(Items.REDSTONE)) {
            return new ConcentrationModifier(1.1f, 0, v -> v + 5.0f);
        }
        if (item.is(Items.GUNPOWDER)) {
            return new ConcentrationModifier(1.5f, 0, v -> v * 1.2f);
        }
        if (item.is(Items.GLISTERING_MELON_SLICE)) {
            return new ConcentrationModifier(1.0f, 15.0f, null);
        }
        if (item.is(Items.GOLDEN_CARROT)) {
            return new ConcentrationModifier(1.15f, 0, v -> v * 1.1f + 3.0f);
        }

        // 默认：无修正
        return ConcentrationModifier.NONE;
    }

    /**
     * 浓度修正器
     * 支持倍率、固定增量和自定义函数
     */
    private static class ConcentrationModifier {
        public static final ConcentrationModifier NONE = new ConcentrationModifier(1.0f, 0, null);

        private final float multiplier;
        private final float additive;
        private final FloatUnaryOperator custom;

        public ConcentrationModifier(float multiplier, float additive, FloatUnaryOperator custom) {
            this.multiplier = multiplier;
            this.additive = additive;
            this.custom = custom;
        }

        public float apply(float base) {
            float result = base * multiplier + additive;
            return custom != null ? custom.apply(result) : result;
        }
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return MATSerializers.ALCHEMY_MATERIAL_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return MATRecipeTypes.ALCHEMY_MATERIAL_RECIPE_TYPE.get();
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public String getGroup() {
        return this.group;
    }


    public static class Serializer implements RecipeSerializer<AlchemyMaterialRecipe> {
        @Override
        public AlchemyMaterialRecipe fromJson(ResourceLocation id, JsonObject recipe) {
            var ingredient = Ingredient.fromJson(recipe.get("ingredient"));
            var allowMultipleMaterials = GsonHelper.getAsBoolean(recipe, "allow_multiple_materials", false);
            var applyAntagonism = GsonHelper.getAsBoolean(recipe, "apply_antagonism", true);
            var baseMaterialLoss = GsonHelper.getAsFloat(recipe, "base_material_loss", 0.1f);

            return new AlchemyMaterialRecipe(
                    ingredient,
                    id,
                    IngredientUtil.parse(recipe, "container"),
                    IngredientUtil.parse(recipe, "blaze_burner"),
                    GsonHelper.isValidNode(recipe, "result")
                            ? CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(recipe, "result"), true)
                            : ItemStack.EMPTY,
                    GsonHelper.getAsString(recipe, "group", ""),
                    GsonHelper.getAsFloat(recipe, "experience", 0.0F),
                    GsonHelper.getAsInt(recipe, "work_time", 200),
                    GsonHelper.getAsBoolean(recipe, "require_mortar", true),
                    allowMultipleMaterials,
                    applyAntagonism,
                    baseMaterialLoss
            );
        }

        @Override
        public AlchemyMaterialRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            return new AlchemyMaterialRecipe(
                    Ingredient.fromNetwork(buffer),
                    id,
                    Ingredient.fromNetwork(buffer),
                    Ingredient.fromNetwork(buffer),
                    buffer.readItem(),
                    buffer.readUtf(),
                    buffer.readFloat(),
                    buffer.readVarInt(),
                    buffer.readBoolean(),
                    buffer.readBoolean(),
                    buffer.readBoolean(),
                    buffer.readFloat()
            );
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, AlchemyMaterialRecipe recipe) {
            recipe.ingredient.toNetwork(buffer);
            recipe.container.toNetwork(buffer);
            recipe.burner.toNetwork(buffer);
            buffer.writeItem(recipe.output);
            buffer.writeUtf(recipe.group);
            buffer.writeFloat(recipe.experience);
            buffer.writeVarInt(recipe.workTime);
            buffer.writeBoolean(recipe.requireMortar);
            buffer.writeBoolean(recipe.allowMultipleMaterials);
            buffer.writeBoolean(recipe.applyAntagonism);
            buffer.writeFloat(recipe.baseMaterialLoss);
        }
    }
}
