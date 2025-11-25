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
import it.unimi.dsi.fastutil.objects.Reference2FloatOpenHashMap;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
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

    public AlchemyMaterialRecipe(
            Ingredient ingredient,
            ResourceLocation id,
            Ingredient container,
            Ingredient burner,
            ItemStack output,
            String group,
            float experience,
            int workTime,
            boolean requireMortar) {
        super(id, ImmutableList.of(), container, burner, output, group, experience, workTime, requireMortar);
        this.ingredient = ingredient;
    }

    @Override
    public boolean matches(WorkHubBlockEntity hub, Level level) {
        if (this.requireMortar != hub.getStackInSlot(0).is(MATItemTags.MORTARS)) return false;
        if (this.burner.isEmpty() && this.burner.isEmpty() != this.burner.test(hub.getStackInSlot(1))) {
            return false;
        } else if (!this.burner.test(hub.getStackInSlot(1))) {
            return false;
        }
        ItemStack ingredient = ItemStack.EMPTY;
        ItemStack baseStack = ItemStack.EMPTY;
        for (int i = 2; i <= 7; i++) {
            ItemStack stack = hub.getItem(i);
            if (!stack.isEmpty()) {
                if (ingredient.isEmpty() && this.ingredient.test(stack)) {
                    ingredient = stack;
                } else if (stack.is(MATItemTags.IS_ALCHEMY_MATERIALS)) {
                    baseStack = stack;
                } else {
                    return false;
                }
            }
        }
        return !ingredient.isEmpty() && !baseStack.isEmpty();
    }

    @Override
    public @NotNull ItemStack assemble(WorkHubBlockEntity hub, RegistryAccess access) {
        var concentrations = new Reference2FloatOpenHashMap<Element>();
        var output = this.output.copy();
        var storage = CapabilityUtil.getCapability(output, MATCapabilities.ELEMENT_STORAGE);
        if (storage == null) return output;
        for (int i = 2; i <= 7; i++) {
            ItemStack stack = hub.getItem(i);
            if (!stack.isEmpty()) {
                var instance = AlchemyElement.fromStack(access, stack);
                if (instance == null) continue;
                for (var entry : instance.concentrations().object2FloatEntrySet()) {
                    concentrations.addTo(entry.getKey().value(), entry.getFloatValue());
                    LOGGER.warn(entry.getFloatValue());

                }
            }
        }
        storage.setConcentrations(concentrations);
        return output;
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
                    GsonHelper.getAsBoolean(recipe, "require_mortar", true)
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
                    buffer.readBoolean()
            );
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, AlchemyMaterialRecipe recipe) {
            recipe.ingredient.toNetwork(buffer);
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
