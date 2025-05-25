package enderdragon.magic_and_taboo.init;

import com.mojang.logging.LogUtils;
import enderdragon.magic_and_taboo.util.MagicPotionSolvent;
import it.unimi.dsi.fastutil.objects.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
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
    private static final Object2ObjectMap<RegistryObject<FluidType>, MagicPotionSolvent> DEFERRED_REGISTRY = new Object2ObjectOpenHashMap<>();
    private static final Reference2ObjectMap<FluidType, MagicPotionSolvent> REGISTRY = new Reference2ObjectOpenHashMap<>();
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final MagicPotionSolvent DEFAULT = (elements, timeFactor, baseLevel) -> {
        var effects = new ObjectArrayList<MobEffectInstance>(elements.size());
        for (var entry : elements.object2FloatEntrySet()) {
            var element = entry.getKey();
            if (element.concentration().min() <= entry.getFloatValue()) {
                effects.add(element.getEffect(entry.getFloatValue(), timeFactor, baseLevel));
            }
        }
        return effects;
    };
    public static final MagicPotionSolvent WATER = DEFAULT;

    static {
        ObjectHolderRegistry.addHandler(MagicPotionSolvents::onRegistryReady);
        register(ForgeMod.WATER_TYPE, WATER);
    }

    public static void register(FluidType fluid, MagicPotionSolvent solvent) {
        if (STATIC_REGISTRY.put(fluid, solvent) != null) {
            logReplacementWarning(fluid.getClass().getCanonicalName(), solvent);
        }
    }

    public static void register(RegistryObject<FluidType> fluid, MagicPotionSolvent solvent) {
        if (DEFERRED_REGISTRY.put(fluid, solvent) != null) {
            LOGGER.warn("Replacing solvent of deferred fluid {} with {}", fluid.getId(), solvent.getClass().getCanonicalName());
        }
        registerIfPresent(fluid, solvent);
    }

    private static void registerIfPresent(RegistryObject<FluidType> fluid, MagicPotionSolvent solvent) {
        if (fluid.isPresent() && REGISTRY.put(fluid.get(), solvent) != null) {
            logReplacementWarning(fluid.getId().toString(), solvent);
        }
    }

    public static @Nullable MagicPotionSolvent getSolventOrNull(FluidType fluid) {
        return REGISTRY.get(fluid);
    }

    public static @NotNull MagicPotionSolvent getSolvent(FluidType fluid) {
        return REGISTRY.getOrDefault(fluid, DEFAULT);
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
