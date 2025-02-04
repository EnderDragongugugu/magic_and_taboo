//package enderdragon.magic_and_taboo.init;
//
//import enderdragon.magic_and_taboo.MagicAndTabooMod;
//import net.minecraft.world.level.material.Fluid;
//import net.minecraftforge.fluids.FluidType;
//import net.minecraftforge.fluids.ForgeFlowingFluid;
//import net.minecraftforge.registries.DeferredRegister;
//import net.minecraftforge.registries.ForgeRegistries;
//import net.minecraftforge.registries.RegistryObject;
//
//public class MATFluids {
//    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, MagicAndTabooMod.MOD_ID);
//    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, MagicAndTabooMod.MOD_ID);
//    public static final RegistryObject<FluidType> HONEY_FLUID_TYPE = FLUID_TYPES.register("honey_fluid",
//            () -> new FluidType(FluidType.Properties.create()));
//
//    public static final RegistryObject<ForgeFlowingFluid.Source> HONEY_FLUID = FLUIDS.register("honey_fluid",
//            () -> new ForgeFlowingFluid.Source(MATFluids.HONEY_PROPERTIES));
//
//    public static final RegistryObject<ForgeFlowingFluid.Flowing> HONEY_FLOWING_FLUID = FLUIDS.register("honey_flowing_fluid",
//            () -> new ForgeFlowingFluid.Flowing(MATFluids.HONEY_PROPERTIES));
//
//    public static final ForgeFlowingFluid.Properties HONEY_PROPERTIES = new ForgeFlowingFluid.Properties(
//            HONEY_FLUID_TYPE, HONEY_FLUID, HONEY_FLOWING_FLUID
//    ).block(MATBlocks.HONEY_LIQUID_BLOCK).bucket(MATItems.BLAZE_BLAST_BURNER);
//}
