package enderdragon.magicandtaboo.init;

import enderdragon.magicandtaboo.MagicAndTabooMod;
import enderdragon.magicandtaboo.crafting.WorkHubRecipeType;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MATRecipeTypes {
    public static final DeferredRegister<RecipeType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, MagicAndTabooMod.MOD_ID);
    public static final RegistryObject<RecipeType<?>> WORK_HUB_RECIPE_TYPE = REGISTRY.register("work_hub", ()-> registry("work_hub"));
    public static <T extends Recipe<?>> RecipeType<T> registry(String name){
        return new RecipeType<>() {
            @Override
            public String toString() {
                return MagicAndTabooMod.MOD_ID + ":" + name;
            }
        };
    }
}
