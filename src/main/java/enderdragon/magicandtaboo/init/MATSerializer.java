package enderdragon.magicandtaboo.init;

import enderdragon.magicandtaboo.MagicAndTabooMod;
import enderdragon.magicandtaboo.crafting.WorkHubRecipeType;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MATSerializer {
    public static final DeferredRegister<RecipeSerializer<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MagicAndTabooMod.MOD_ID);
    public static final RegistryObject<RecipeSerializer<?>> WORK_HUB_RECIPE_SERIALIZERS = REGISTRY.register("work_hub", WorkHubRecipeType.Serializer::new);
}
