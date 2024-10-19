package enderdragon.magic_and_taboo.util;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.Ingredient;

public class IngredientUtil {
    public static ImmutableList<Ingredient> parse(JsonArray array) {
        int maxSize = array.size();
        var ingredients = new Ingredient[maxSize];
        for (int i = 0, j = 0; i < maxSize; ++i) {
            var ingredient = Ingredient.fromJson(array.get(i));
            if (!ingredient.isEmpty()) {
                ingredients[j++] = ingredient;
            }
        }
        return ImmutableList.copyOf(ingredients);
    }

    public static Ingredient parse(JsonObject parent, String key) {
        return GsonHelper.isValidNode(parent, key) ? Ingredient.fromJson(GsonHelper.getAsJsonObject(parent, key)) : Ingredient.EMPTY;
    }
}
