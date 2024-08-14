package enderdragon.magicandtaboo.effect;


import enderdragon.magicandtaboo.MagicAndTabooMod;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MATEffect {
    public static final DeferredRegister<MobEffect> REGISTRY = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MagicAndTabooMod.MOD_ID);
    public static RegistryObject<MobEffect> MERCURY_TOXINS = REGISTRY.register("mercury_toxins", MercuryToxinsEffect::new);
}
