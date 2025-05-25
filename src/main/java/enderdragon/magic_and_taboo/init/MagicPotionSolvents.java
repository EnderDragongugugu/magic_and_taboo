package enderdragon.magic_and_taboo.init;

import com.mojang.logging.LogUtils;
import enderdragon.magic_and_taboo.util.MagicPotionSolvent;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolderRegistry;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.function.Predicate;

public class MagicPotionSolvents {
    private static final Reference2ObjectMap<FluidType, MagicPotionSolvent> STATIC_REGISTRY = new Reference2ObjectOpenHashMap<>();
    private static final Object2ObjectMap<RegistryObject<? extends FluidType>, MagicPotionSolvent> DEFERRED_REGISTRY = new Object2ObjectOpenHashMap<>();
    private static final Reference2ObjectMap<FluidType, MagicPotionSolvent> REGISTRY = new Reference2ObjectOpenHashMap<>();
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final MagicPotionSolvent WATER = () -> 0x385DC6;
    public static final MagicPotionSolvent HONEY = () -> 0xD28D2B;
    public static final MagicPotionSolvent PLANT_EXTRACT = () -> 0x044923;

    static {
        ObjectHolderRegistry.addHandler(MagicPotionSolvents::onRegistryReady);
        register(ForgeMod.WATER_TYPE, WATER);
        register(MATFluids.HONEY_FLUID_TYPE, HONEY);
        register(MATFluids.PLANT_EXTRACT_FLUID_TYPE, PLANT_EXTRACT);
    }

    public static void register(FluidType fluid, MagicPotionSolvent solvent) {
        if (STATIC_REGISTRY.put(fluid, solvent) != null) {
            logReplacementWarning(fluid.getClass().getCanonicalName(), solvent);
        }
    }

    public static void register(RegistryObject<? extends FluidType> fluid, MagicPotionSolvent solvent) {
        if (DEFERRED_REGISTRY.put(fluid, solvent) != null) {
            LOGGER.warn("Replacing solvent of deferred fluid {} with {}", fluid.getId(), solvent.getClass().getCanonicalName());
        }
        registerIfPresent(fluid, solvent);
    }

    private static void registerIfPresent(RegistryObject<? extends FluidType> fluid, MagicPotionSolvent solvent) {
        if (fluid.isPresent() && REGISTRY.put(fluid.get(), solvent) != null) {
            logReplacementWarning(fluid.getId().toString(), solvent);
        }
    }

    public static @Nullable MagicPotionSolvent findSolvent(FluidType fluid) {
        return REGISTRY.get(fluid);
    }

    public static @NotNull MagicPotionSolvent getSolvent(FluidType fluid) {
        return REGISTRY.getOrDefault(fluid, WATER);
    }

    private static void onRegistryReady(Predicate<ResourceLocation> registry) {
        if (registry.test(ForgeRegistries.Keys.FLUID_TYPES.location())) {
            REGISTRY.clear();
            REGISTRY.putAll(STATIC_REGISTRY);
            for (var entry : DEFERRED_REGISTRY.object2ObjectEntrySet()) {
                registerIfPresent(entry.getKey(), entry.getValue());
            }
        }
    }

    private static void logReplacementWarning(String fluid, MagicPotionSolvent solvent) {
        LOGGER.warn("Replacing solvent of fluid {} with {}", fluid, solvent.getClass().getCanonicalName());
    }
}
