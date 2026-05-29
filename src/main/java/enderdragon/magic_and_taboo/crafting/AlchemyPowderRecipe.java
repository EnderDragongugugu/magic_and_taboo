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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AlchemyPowderRecipe extends WorkHubRecipe {
    private final boolean applyAntagonism;
    private final Ingredient catalyst;

    public AlchemyPowderRecipe(
            ResourceLocation id,
            Ingredient container,
            Ingredient burner,
            ItemStack output,
            String group,
            float experience,
            int workTime,
            boolean requireMortar,
            boolean applyAntagonism,
            Ingredient catalyst) {
        super(ImmutableList.of(), id, group, output, container, burner, experience, workTime, requireMortar);
        this.applyAntagonism = applyAntagonism;
        this.catalyst = catalyst;
    }

    @Override
    public boolean matches(WorkHubBlockEntity hub, Level level) {
        if (this.requireMortar != hub.getStackInSlot(0).is(MATItemTags.MORTARS))
            return false;
        if (!this.burner.isEmpty() && !this.burner.test(hub.getStackInSlot(1))) {
            return false;
        }
        boolean hasValidIngredient = false;
        int catalystCount = 0;
        var access = level.registryAccess();
        for (int i = 2; i <= 7; i++) {
            ItemStack stack = hub.getItem(i);
            if (stack.isEmpty())
                continue;
            if (!this.catalyst.isEmpty() && this.catalyst.test(stack)) {
                catalystCount++;
                if (catalystCount > 1) {
                    return false;
                }
                continue;
            }

            // 排除已加工的炼金材料（粉末、溶液、糊等）
            if (stack.is(MATItemTags.IS_ALCHEMY)) {
                return false;
            }

            var element = AlchemyElement.fromStack(access, stack);
            if (element != null && !element.concentrations().isEmpty()) {
                hasValidIngredient = true;
            } else {
                return false;
            }
        }

        return hasValidIngredient;
    }

    @Override
    public @NotNull ItemStack assemble(WorkHubBlockEntity hub, RegistryAccess access) {
        var concentrations = new Reference2FloatOpenHashMap<Element>();
        var output = this.output.copy();
        var storage = CapabilityUtil.getCapability(output, MATCapabilities.ELEMENT_STORAGE);
        if (storage == null)
            return output;

        // 检查是否有催化剂
        boolean hasCatalyst = false;
        for (int i = 2; i <= 7; i++) {
            ItemStack stack = hub.getItem(i);
            if (!stack.isEmpty() && !this.catalyst.isEmpty() && this.catalyst.test(stack)) {
                hasCatalyst = true;
                break;
            }
        }

        // 从输入物品中读取元素信息并应用修正
        for (int i = 2; i <= 7; i++) {
            ItemStack stack = hub.getItem(i);
            if (stack.isEmpty())
                continue;

            // 跳过催化剂
            if (!this.catalyst.isEmpty() && this.catalyst.test(stack)) {
                continue;
            }

            var instance = AlchemyElement.fromStack(access, stack);
            if (instance != null && !instance.concentrations().isEmpty()) {
                var modifier = getConcentrationModifier(stack);
                for (var entry : instance.concentrations().object2FloatEntrySet()) {
                    float concentration = modifier.apply(entry.getFloatValue());
                    // 催化剂效果：浓度增加 50%
                    if (hasCatalyst) {
                        concentration *= 1.5f;
                    }
                    concentrations.addTo(entry.getKey().value(), concentration);
                }
            }
        }

        // 应用元素对抗（如果启用）
        if (applyAntagonism && !concentrations.isEmpty()) {
            Element.antagonize(concentrations);
        }

        if (!concentrations.isEmpty()) {
            storage.setConcentrations(concentrations);
        }
        return output;
    }

    @Override
    public int workTime() {
        // 催化剂减少工作时长
        return catalyst.isEmpty() ? super.workTime() : super.workTime() * 2 / 3;
    }

    /**
     * 获取催化剂物品
     */
    public Ingredient catalyst() {
        return catalyst;
    }

    /**
     * 获取物品的浓度修正器
     * 根据物品的标签返回不同的修正器
     */
    private ConcentrationModifier getConcentrationModifier(ItemStack item) {
        // 高浓度剂：1.5倍 + 自定义修正
        if (item.is(MATItemTags.ELEMENT_CONCENTRATED)) {
            return new ConcentrationModifier(1.5f, 0, v -> v * 1.2f);
        }

        // 元素增强剂：1.25倍浓度
        if (item.is(MATItemTags.ELEMENT_ENHANCERS)) {
            // 红石有额外效果
            if (item.is(Items.REDSTONE)) {
                return new ConcentrationModifier(1.1f, 5.0f, null);
            }
            return new ConcentrationModifier(1.25f, 0, null);
        }

        // 稀释剂：0.9倍 + 自定义修正
        if (item.is(MATItemTags.ELEMENT_DILUTED)) {
            return new ConcentrationModifier(0.9f, 0, v -> v * 0.8f + 2.0f);
        }

        // 元素稀释剂：0.75倍浓度
        if (item.is(MATItemTags.ELEMENT_Diluents)) {
            return new ConcentrationModifier(0.75f, 0, null);
        }

        // 稳定增强剂：1.15倍 + 自定义修正
        if (item.is(MATItemTags.ELEMENT_STABILIZED)) {
            return new ConcentrationModifier(1.15f, 0, v -> v * 1.1f + 3.0f);
        }

        // 元素稳定剂：固定增量
        if (item.is(MATItemTags.ELEMENT_STABILIZERS)) {
            // 粘液球有额外固定增量
            if (item.is(Items.SLIME_BALL)) {
                return new ConcentrationModifier(1.0f, 10.0f, null);
            }
            // 闪烁的西瓜片在 ELEMENT_STABILIZED 中处理
            // 金胡萝卜在 ELEMENT_STABILIZED 中处理
            return new ConcentrationModifier(1.0f, 5.0f, null);
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
        return MATSerializers.ALCHEMY_POWDER_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return MATRecipeTypes.ALCHEMY_POWDER_RECIPE_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<AlchemyPowderRecipe> {
        @Override
        public AlchemyPowderRecipe fromJson(ResourceLocation id, JsonObject recipe) {
            var container = IngredientUtil.parse(recipe, "container");
            var burner = IngredientUtil.parse(recipe, "blaze_burner");
            var output = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(recipe, "result"), true);
            var group = GsonHelper.getAsString(recipe, "group", "");
            var experience = GsonHelper.getAsFloat(recipe, "experience", 0.0F);
            var workTime = GsonHelper.getAsInt(recipe, "work_time", 200);
            var requireMortar = GsonHelper.getAsBoolean(recipe, "require_mortar", true);
            var applyAntagonism = GsonHelper.getAsBoolean(recipe, "apply_antagonism", true);
            var catalyst = IngredientUtil.parse(recipe, "catalyst");

            return new AlchemyPowderRecipe(
                    id,
                    container,
                    burner,
                    output,
                    group,
                    experience,
                    workTime,
                    requireMortar,
                    applyAntagonism,
                    catalyst);
        }

        @Override
        @Nullable
        public AlchemyPowderRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            return new AlchemyPowderRecipe(
                    id,
                    Ingredient.fromNetwork(buffer),
                    Ingredient.fromNetwork(buffer),
                    buffer.readItem(),
                    buffer.readUtf(),
                    buffer.readFloat(),
                    buffer.readVarInt(),
                    buffer.readBoolean(),
                    buffer.readBoolean(),
                    Ingredient.fromNetwork(buffer));
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, AlchemyPowderRecipe recipe) {
            recipe.container.toNetwork(buffer);
            recipe.burner.toNetwork(buffer);
            buffer.writeItem(recipe.output);
            buffer.writeUtf(recipe.group);
            buffer.writeFloat(recipe.experience);
            buffer.writeVarInt(recipe.workTime);
            buffer.writeBoolean(recipe.requireMortar);
            buffer.writeBoolean(recipe.applyAntagonism);
            recipe.catalyst.toNetwork(buffer);
        }
    }
}
