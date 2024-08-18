package enderdragon.magicandtaboo.init;

import enderdragon.magicandtaboo.block.*;
import net.minecraft.core.Direction.Axis;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.PressurePlateBlock.Sensitivity;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.BlockBehaviour.StatePredicate;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;

import static enderdragon.magicandtaboo.MagicAndTabooMod.MOD_ID;

public class MATBlocks {
    public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    public static final BlockSetType FIR_BLOCK_SET_TYPE;
    public static final WoodType FIR_WOOD_TYPE;
    public static final RegistryObject<MercuryOreBlock> MERCURY_ORE = REGISTRY.register("mercury_ore", () -> new MercuryOreBlock(Properties.of()
            .mapColor(MapColor.STONE)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .requiresCorrectToolForDrops()
            .strength(3.0F, 3.0F),
            UniformInt.of(3, 7)
    ));
    public static final RegistryObject<HorizontalDirectionalBlock> Federation_Workstations_Block = REGISTRY.register("federation_workstations",()->new FederationWorkstationsBlock(Properties.of()
            .mapColor(MapColor.WOOD)
            .instrument(NoteBlockInstrument.BASS)
            .strength(2.5F)
            .sound(SoundType.WOOD)
            .ignitedByLava()));
    public static final RegistryObject<FlammableBlock> FIR_PLANKS;
    public static final RegistryObject<SaplingBlock> FIR_SAPLING;
    public static final RegistryObject<StrippableLogBlock> FIR_LOG;
    public static final RegistryObject<MATLogBlock> STRIPPED_FIR_LOG;
    public static final RegistryObject<StrippableLogBlock> FIR_WOOD;
    public static final RegistryObject<MATLogBlock> STRIPPED_FIR_WOOD;
    public static final RegistryObject<MATLeavesBlock> FIR_LEAVES;
    public static final RegistryObject<StandingSignBlock> FIR_SIGN;
    public static final RegistryObject<WallSignBlock> FIR_WALL_SIGN;
    public static final RegistryObject<CeilingHangingSignBlock> FIR_HANGING_SIGN;
    public static final RegistryObject<WallHangingSignBlock> FIR_WALL_HANGING_SIGN;
    public static final RegistryObject<PressurePlateBlock> FIR_PRESSURE_PLATE;
    public static final RegistryObject<TrapDoorBlock> FIR_TRAPDOOR;
    public static final RegistryObject<MATStairBlock> FIR_STAIRS;
    public static final RegistryObject<FlowerPotBlock> POTTED_FIR_SAPLING;
    public static final RegistryObject<ButtonBlock> FIR_BUTTON;
    public static final RegistryObject<MATSlabBlock> FIR_SLAB;
    public static final RegistryObject<MATFenceGateBlock> FIR_FENCE_GATE;
    public static final RegistryObject<MATFenceBlock> FIR_FENCE;
    public static final RegistryObject<DoorBlock> FIR_DOOR;


    static Properties firPlanks(Function<BlockState, MapColor> color) {
        return Properties.of().mapColor(color).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava();
    }

    static Properties firLog(Function<BlockState, MapColor> color) {
        return Properties.of().mapColor(color).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava();
    }

    static Properties firSign(Function<BlockState, MapColor> color) {
        return Properties.of().mapColor(color).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava();
    }

    static {
        String typeId = MOD_ID + ":fir";
        FIR_BLOCK_SET_TYPE = BlockSetType.register(new BlockSetType(typeId));
        FIR_WOOD_TYPE = WoodType.register(new WoodType(typeId, FIR_BLOCK_SET_TYPE));
        final Function<BlockState, MapColor> plantColor = state -> MapColor.PLANT;
        final Function<BlockState, MapColor> planksColor = state -> MapColor.PODZOL;
        final StatePredicate never = (state, level, pos) -> false;
        FIR_PLANKS = REGISTRY.register("fir_planks", () -> new FlammableBlock(20, 5, firPlanks(planksColor)));
        FIR_SAPLING = REGISTRY.register("fir_sapling", () -> new SaplingBlock(FirTreeGrower.INSTANCE, Properties.of()
                .mapColor(plantColor)
                .noCollission()
                .randomTicks()
                .instabreak()
                .sound(SoundType.GRASS)
                .pushReaction(PushReaction.DESTROY)
        ));
        STRIPPED_FIR_LOG = REGISTRY.register("stripped_fir_log", () -> new MATLogBlock(5, 5, firLog(planksColor)));
        STRIPPED_FIR_WOOD = REGISTRY.register("stripped_fir_wood", () -> new MATLogBlock(5, 5, firLog(state -> MapColor.COLOR_BROWN)));
        FIR_LOG = REGISTRY.register("fir_log", () -> new StrippableLogBlock(STRIPPED_FIR_LOG, 5, 5, firLog(
                state -> state.getValue(BlockStateProperties.AXIS) == Axis.Y ? MapColor.PODZOL : MapColor.COLOR_BROWN)
        ));
        FIR_WOOD = REGISTRY.register("fir_wood", () -> new StrippableLogBlock(STRIPPED_FIR_WOOD, 5, 5, firLog(planksColor)));
        FIR_LEAVES = REGISTRY.register("fir_leaves", () -> new MATLeavesBlock(60, 30, Properties.of()
                .mapColor(plantColor)
                .strength(0.2F)
                .randomTicks()
                .sound(SoundType.GRASS)
                .noOcclusion()
                .isSuffocating(never)
                .isViewBlocking(never)
                .ignitedByLava()
                .pushReaction(PushReaction.DESTROY)
                .isRedstoneConductor(never)
        ));
        FIR_SIGN = REGISTRY.register("fir_sign", () -> new StandingSignBlock(firSign(planksColor), FIR_WOOD_TYPE));
        FIR_WALL_SIGN = REGISTRY.register("fir_wall_sign", () -> new WallSignBlock(firSign(planksColor).lootFrom(FIR_SIGN), FIR_WOOD_TYPE));
        FIR_HANGING_SIGN = REGISTRY.register("fir_hanging_sign", () -> new CeilingHangingSignBlock(firSign(planksColor), FIR_WOOD_TYPE));
        FIR_WALL_HANGING_SIGN = REGISTRY.register("fir_wall_hanging_sign", () -> new WallHangingSignBlock(firSign(planksColor).lootFrom(FIR_HANGING_SIGN), FIR_WOOD_TYPE));
        FIR_PRESSURE_PLATE = REGISTRY.register("fir_pressure_plate", () -> new PressurePlateBlock(Sensitivity.EVERYTHING, Properties.of()
                .mapColor(planksColor)
                .forceSolidOn()
                .instrument(NoteBlockInstrument.BASS)
                .noCollission()
                .strength(0.5F).
                ignitedByLava()
                .pushReaction(PushReaction.DESTROY),
                FIR_BLOCK_SET_TYPE
        ));
        FIR_TRAPDOOR = REGISTRY.register("fir_trapdoor", () -> new TrapDoorBlock(Properties.of()
                .mapColor(planksColor)
                .instrument(NoteBlockInstrument.BASS)
                .strength(3.0F)
                .noOcclusion()
                .isValidSpawn((state, level, pos, type) -> false)
                .ignitedByLava(),
                FIR_BLOCK_SET_TYPE
        ));
        FIR_STAIRS = REGISTRY.register("fir_stairs", () -> new MATStairBlock(20, 5, () -> FIR_PLANKS.get().defaultBlockState(), firPlanks(planksColor)));
        var emptyPot = (FlowerPotBlock) Blocks.FLOWER_POT;
        POTTED_FIR_SAPLING = REGISTRY.register("potted_fir_sapling", () -> new FlowerPotBlock(() -> emptyPot, FIR_SAPLING, Properties.of()
                .instabreak()
                .noOcclusion()
                .pushReaction(PushReaction.DESTROY)
        ));
        emptyPot.addPlant(FIR_SAPLING.getId(), POTTED_FIR_SAPLING);
        FIR_BUTTON = REGISTRY.register("fir_button", () -> new ButtonBlock(Properties.of()
                .noCollission()
                .strength(0.5F)
                .pushReaction(PushReaction.DESTROY),
                FIR_BLOCK_SET_TYPE,
                30,
                true
        ));
        FIR_SLAB = REGISTRY.register("fir_slab", () -> new MATSlabBlock(20, 5, firPlanks(planksColor)));
        FIR_FENCE_GATE = REGISTRY.register("fir_fence_gate", () -> new MATFenceGateBlock(20, 5, firPlanks(planksColor).forceSolidOn(), FIR_WOOD_TYPE));
        FIR_FENCE = REGISTRY.register("fir_fence", () -> new MATFenceBlock(20, 5, firPlanks(planksColor).forceSolidOn()));
        FIR_DOOR = REGISTRY.register("fir_door", () -> new DoorBlock(Properties.of()
                .mapColor(planksColor)
                .instrument(NoteBlockInstrument.BASS)
                .strength(3.0F)
                .noOcclusion()
                .ignitedByLava()
                .pushReaction(PushReaction.DESTROY),
                FIR_BLOCK_SET_TYPE
        ));
    }
}
