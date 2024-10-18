package enderdragon.magicandtaboo.crafting;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import enderdragon.magicandtaboo.init.MATRecipeTypes;
import enderdragon.magicandtaboo.init.MATSerializer;
import enderdragon.magicandtaboo.inventory.WorkHubItemHandler;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class WorkHubRecipeType implements Recipe<RecipeWrapper> {
    protected ResourceLocation id;
    protected String group;
    protected NonNullList<Ingredient> ingredients;
    protected ItemStack output;
    protected ItemStack container;
    protected ItemStack mortar;
    protected ItemStack blazeBurner;
    protected float experience;
    protected int workTime;
    public WorkHubRecipeType(ResourceLocation id, String group, @Nullable NonNullList<Ingredient> ingredients, ItemStack output, ItemStack container, float experience, int workTime, ItemStack mortar, ItemStack blazeBurner){
        this.id = id;
        this.group = group;
        this.ingredients = ingredients;
        this.output = output;
        this.mortar = mortar;
        this.blazeBurner = blazeBurner;

        if (!container.isEmpty()) {
            this.container = container;
        } else if (!output.getCraftingRemainingItem().isEmpty()) {
            this.container = output.getCraftingRemainingItem();
        } else {
            this.container = ItemStack.EMPTY;
        }

        this.experience = experience;
        this.workTime = workTime;
    }

    @Override
    public boolean matches(RecipeWrapper pContainer, Level pLevel) {
        Item mortar = pContainer.getItem(0).getItem();
        Item blazeBurner = pContainer.getItem(1).getItem();
        java.util.List<ItemStack> list = new java.util.ArrayList<>();
        if(!this.mortar.isEmpty() || !this.blazeBurner.isEmpty()){
            if(!this.mortar.is(mortar) || this.blazeBurner.is(blazeBurner)) return false;
        }
        for(int i = 2; i <= 7; i++){
            ItemStack itemStack = pContainer.getItem(i);
            if(!itemStack.isEmpty()){
                list.add(itemStack);
            }
        }
        return list.size() == this.ingredients.size() && net.minecraftforge.common.util.RecipeMatcher.findMatches(list, this.ingredients) != null;
    }

    @Override
    public @NotNull ItemStack assemble(RecipeWrapper pContainer, RegistryAccess pRegistryAccess) {
        return this.output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return pWidth * pHeight >= this.ingredients.size();
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return this.output;
    }

    public ItemStack getContainer(){
        return this.container;
    }

    public ItemStack getBlazeBurner() {
        return blazeBurner;
    }

    public float getExperience() {
        return experience;
    }

    public int getWorkTime() {
        return workTime;
    }

    public ItemStack getMortar() {
        return mortar;
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
        return MATSerializer.WORK_HUB_RECIPE_SERIALIZERS.get();
    }

    @Override
    public RecipeType<?> getType() {
        return MATRecipeTypes.WORK_HUB_RECIPE_TYPE.get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkHubRecipeType that = (WorkHubRecipeType) o;
        return Float.compare(that.experience, experience) == 0 && workTime == that.workTime && id.equals(that.id) && group.equals(that.group) && ingredients.equals(that.ingredients) && output.equals(that.output) && container.equals(that.container) && mortar.equals(that.mortar) && blazeBurner.equals(that.blazeBurner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, group, ingredients, output, container, mortar, blazeBurner, experience, workTime);
    }

    public static class Serializer implements RecipeSerializer<WorkHubRecipeType>{

        @Override
        public WorkHubRecipeType fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            final String group = GsonHelper.getAsString(pSerializedRecipe, "group", "");
            final NonNullList<Ingredient> ingredient = getIngredient(GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients"));
            if (ingredient.isEmpty()){
                throw new JsonParseException("No ingredients for work hub recipe");
            }else if (ingredient.size() > WorkHubItemHandler.MAX_SLOT) {
                throw new JsonParseException("ingredients too many, the max is " + WorkHubItemHandler.MAX_SLOT);
            }else {
                final ItemStack output = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(pSerializedRecipe, "result"), true);
                ItemStack container = GsonHelper.isValidNode(pSerializedRecipe, "container") ? CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(pSerializedRecipe, "container"), true) : ItemStack.EMPTY;
                final float experience = GsonHelper.getAsFloat(pSerializedRecipe, "experience", 0.0F);
                final int workTime = GsonHelper.getAsInt(pSerializedRecipe, "work_time", 200);
                ItemStack mortar = GsonHelper.isValidNode(pSerializedRecipe, "mortar") ? CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(pSerializedRecipe, "mortar"), true) : ItemStack.EMPTY;
                ItemStack blazeBurner = GsonHelper.isValidNode(pSerializedRecipe, "blaze_burner") ? CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(pSerializedRecipe, "blaze_burner"), true) : ItemStack.EMPTY;
                return new WorkHubRecipeType(pRecipeId, group,ingredient,output,container,experience,workTime,mortar,blazeBurner);
            }
        }

        protected NonNullList<Ingredient> getIngredient(JsonArray arr){
            NonNullList<Ingredient> nonnulllist = NonNullList.create();
            for (int i = 0; i < arr.size(); ++i) {
                Ingredient ingredient = Ingredient.fromJson(arr.get(i));
                if (!ingredient.isEmpty()) {
                    nonnulllist.add(ingredient);
                }
            }
            return nonnulllist;
        }

        @Override
        public @Nullable WorkHubRecipeType fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            String groupIn = pBuffer.readUtf();
            int i = pBuffer.readVarInt();
            NonNullList<Ingredient> list = NonNullList.withSize(i, Ingredient.EMPTY);

            for (int j = 0; j < list.size(); ++j) {
                list.set(j, Ingredient.fromNetwork(pBuffer));
            }

            ItemStack output = pBuffer.readItem();
            ItemStack container = pBuffer.readItem();
            ItemStack mortar = pBuffer.readItem();
            ItemStack blazeBurner = pBuffer.readItem();
            float experience = pBuffer.readFloat();
            int workTime = pBuffer.readVarInt();
            return new WorkHubRecipeType(pRecipeId,groupIn,list,output,container,experience,workTime,mortar,blazeBurner);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, WorkHubRecipeType pRecipe) {
            pBuffer.writeUtf(pRecipe.group);
            pBuffer.writeVarInt(pRecipe.ingredients.size());
            for (Ingredient ingredient : pRecipe.ingredients) {
                ingredient.toNetwork(pBuffer);
            }
            pBuffer.writeItem(pRecipe.output);
            pBuffer.writeItem(pRecipe.container);
            pBuffer.writeItem(pRecipe.mortar);
            pBuffer.writeItem(pRecipe.blazeBurner);
            pBuffer.writeFloat(pRecipe.experience);
            pBuffer.writeVarInt(pRecipe.workTime);
        }
    }
}
