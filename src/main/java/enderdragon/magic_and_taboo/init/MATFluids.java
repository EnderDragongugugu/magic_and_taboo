package enderdragon.magic_and_taboo.init;

import enderdragon.magic_and_taboo.MagicAndTabooMod;
import enderdragon.magic_and_taboo.block.HoneyFluid;
import enderdragon.magic_and_taboo.block.PlantExtractFluid;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MATFluids {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, MagicAndTabooMod.MOD_ID);
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, MagicAndTabooMod.MOD_ID);
    public static final RegistryObject<HoneyFluid.HoneyType> HONEY_FLUID_TYPE = FLUID_TYPES.register("honey_fluid", () -> new HoneyFluid.HoneyType(FluidType.Properties.create()
            .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
            .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
            .fallDistanceModifier(0.0F)
            .density(3000)
            .viscosity(6000)
            .motionScale(0.01)
    ));

    public static final RegistryObject<HoneyFluid.Source> HONEY = FLUIDS.register("honey",
            () -> new HoneyFluid.Source(MATFluids.HONEY_PROPERTIES));

    public static final RegistryObject<HoneyFluid.Flowing> FLOWING_HONEY = FLUIDS.register("flowing_honey",
            () -> new HoneyFluid.Flowing(MATFluids.HONEY_PROPERTIES));

    public static final ForgeFlowingFluid.Properties HONEY_PROPERTIES = new ForgeFlowingFluid
            .Properties(HONEY_FLUID_TYPE, HONEY, FLOWING_HONEY)
            .block(MATBlocks.HONEY)
            .bucket(MATItems.HONEY_BUCKET)
            .explosionResistance(100.0F)
            .slopeFindDistance(2)
            .levelDecreasePerBlock(2)
            .tickRate(30);
    public static final RegistryObject<PlantExtractFluid.PlantExtractType> PLANT_EXTRACT_FLUID_TYPE = FLUID_TYPES.register("plant_extract_fluid", () -> new PlantExtractFluid.PlantExtractType(FluidType.Properties.create()
            .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
            .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
            .fallDistanceModifier(0.0F)
            .density(300)
            .viscosity(600)
            .motionScale(0.01)
    ));

    public static final RegistryObject<PlantExtractFluid.Source> PLANT_EXTRACT = FLUIDS.register("plant_extract",
            () -> new PlantExtractFluid.Source(MATFluids.PLANT_EXTRACT_PROPERTIES));

    public static final RegistryObject<PlantExtractFluid.Flowing> FLOWING_PLANT_EXTRACT = FLUIDS.register("flowing_plant_extract",
            () -> new PlantExtractFluid.Flowing(MATFluids.PLANT_EXTRACT_PROPERTIES));

    public static final ForgeFlowingFluid.Properties PLANT_EXTRACT_PROPERTIES = new ForgeFlowingFluid
            .Properties(PLANT_EXTRACT_FLUID_TYPE, PLANT_EXTRACT, FLOWING_PLANT_EXTRACT)
            .block(MATBlocks.PLANT_EXTRACT)
            .bucket(MATItems.PLANT_EXTRACT_BUCKET)
            .explosionResistance(100.0F)
            .slopeFindDistance(2)
            .levelDecreasePerBlock(1)
            .tickRate(10);
}
