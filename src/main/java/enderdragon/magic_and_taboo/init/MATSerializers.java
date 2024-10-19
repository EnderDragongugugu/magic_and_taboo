package enderdragon.magic_and_taboo.init;

import enderdragon.magic_and_taboo.MagicAndTabooMod;
import enderdragon.magic_and_taboo.crafting.WorkHubRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MATSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MagicAndTabooMod.MOD_ID);
    public static final RegistryObject<RecipeSerializer<?>> WORK_HUB_RECIPE_SERIALIZER = REGISTRY.register("work_hub", WorkHubRecipe.Serializer::new);
}
