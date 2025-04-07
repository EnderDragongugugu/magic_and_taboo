package enderdragon.magic_and_taboo.init;

import enderdragon.magic_and_taboo.MagicAndTabooMod;
import enderdragon.magic_and_taboo.block.HoneyFluid;
import enderdragon.magic_and_taboo.block.PlantExtractFluid;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MATFluids {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, MagicAndTabooMod.MOD_ID);
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, MagicAndTabooMod.MOD_ID);
    public static final RegistryObject<HoneyFluid.Type> HONEY_FLUID_TYPE = FLUID_TYPES.register(
            "honey_fluid",
            () -> new HoneyFluid.Type(FluidType.Properties.create()
                    .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                    .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
                    .fallDistanceModifier(0.0F)
                    .density(3000)
                    .viscosity(6000)
                    .motionScale(0.01)
            )
    );

    public static final RegistryObject<PlantExtractFluid.Type> PLANT_EXTRACT_FLUID_TYPE = FLUID_TYPES.register(
            "plant_extract_fluid",
            () -> new PlantExtractFluid.Type(FluidType.Properties.create()
                    .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                    .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
                    .fallDistanceModifier(0.0F)
                    .density(300)
                    .viscosity(600)
                    .motionScale(0.01)
            )
    );

    public static final RegistryObject<HoneyFluid.Source> HONEY = FLUIDS.register("honey", () ->
            new HoneyFluid.Source(HoneyFluid.PROPERTIES)
    );

    public static final RegistryObject<HoneyFluid.Flowing> FLOWING_HONEY = FLUIDS.register("flowing_honey", () ->
            new HoneyFluid.Flowing(HoneyFluid.PROPERTIES)
    );

    public static final RegistryObject<PlantExtractFluid.Source> PLANT_EXTRACT = FLUIDS.register("plant_extract", () ->
            new PlantExtractFluid.Source(PlantExtractFluid.PROPERTIES)
    );

    public static final RegistryObject<PlantExtractFluid.Flowing> FLOWING_PLANT_EXTRACT = FLUIDS.register("flowing_plant_extract", () ->
            new PlantExtractFluid.Flowing(PlantExtractFluid.PROPERTIES)
    );

}
