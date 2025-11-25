package enderdragon.magic_and_taboo.init;

import enderdragon.magic_and_taboo.MagicAndTabooMod;
import enderdragon.magic_and_taboo.crafting.PedestalRecipe;
import enderdragon.magic_and_taboo.crafting.WorkHubRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static enderdragon.magic_and_taboo.MagicAndTabooMod.makeId;

public class MATRecipeTypes {
    public static final DeferredRegister<RecipeType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, MagicAndTabooMod.MOD_ID);
    public static final RegistryObject<RecipeType<WorkHubRecipe>> WORK_HUB_RECIPE_TYPE = REGISTRY.register("work_hub", () -> RecipeType.simple(makeId("work_hub")));
    public static final RegistryObject<RecipeType<WorkHubRecipe>> ALCHEMY_MATERIAL_RECIPE_TYPE = REGISTRY.register("alchemy_material", () -> RecipeType.simple(makeId("work_hub")));
    public static final RegistryObject<RecipeType<PedestalRecipe>> PEDESTAL_RECIPE_TYPE = REGISTRY.register("pedestal", () -> RecipeType.simple(makeId("pedestal")));
}
