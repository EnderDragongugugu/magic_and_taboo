package enderdragon.magic_and_taboo.init;

import enderdragon.magic_and_taboo.util.*;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class MATSolvents {

    private static final Map<String, IMagicPotionSolvent> SOLVENTS = new HashMap<>();

    static {
        register(Fluids.WATER, new WaterSolvent());
        register(MATFluids.PLANT_EXTRACT.get(), new PlantSolvent());
        register(MATFluids.HONEY.get(), new HoneySolvent());
    }

    public static void register(Fluid fluid, IMagicPotionSolvent solvent) {
        var resourceLocation = ForgeRegistries.FLUIDS.getKey(fluid);
        SOLVENTS.put(resourceLocation.toString(), solvent);
    }

    @Nullable
    public static IMagicPotionSolvent get(String typeId) {
        IMagicPotionSolvent solvent = SOLVENTS.get(typeId.toLowerCase());
        if (solvent == null) {
            return new Solvent();
        }
        return solvent;
    }
}
