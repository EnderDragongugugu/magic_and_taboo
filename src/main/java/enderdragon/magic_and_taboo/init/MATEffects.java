package enderdragon.magic_and_taboo.init;


import enderdragon.magic_and_taboo.MagicAndTabooMod;
import enderdragon.magic_and_taboo.effect.MercuryToxinsEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MATEffects {
    public static final DeferredRegister<MobEffect> REGISTRY = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MagicAndTabooMod.MOD_ID);
    public static RegistryObject<MobEffect> MERCURY_TOXINS = REGISTRY.register("mercury_toxins", MercuryToxinsEffect::new);
}
