package enderdragon.magic_and_taboo.init;

import enderdragon.magic_and_taboo.MagicAndTabooMod;
import enderdragon.magic_and_taboo.block.entity.EnchantedCrucibleBlockEntity;
import enderdragon.magic_and_taboo.block.entity.MagicPerfusionPedestalBlockEntity;
import enderdragon.magic_and_taboo.block.entity.PedestalBlockEntity;
import enderdragon.magic_and_taboo.block.entity.WorkHubBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MATBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MagicAndTabooMod.MOD_ID);
    public static final RegistryObject<BlockEntityType<WorkHubBlockEntity>> WORK_HUB = REGISTRY.register("work_hub", () -> of(
            WorkHubBlockEntity::new,
            MATBlocks.WORK_HUB.get()
    ));
    public static final RegistryObject<BlockEntityType<EnchantedCrucibleBlockEntity>> ENCHANTED_CRUCIBLE = REGISTRY.register("enchanted_crucible", () -> of(
            EnchantedCrucibleBlockEntity::new,
            MATBlocks.ENCHANTED_CRUCIBLE.get()
    ));
    public static final RegistryObject<BlockEntityType<MagicPerfusionPedestalBlockEntity>> MAGIC_PERFUSION_PEDESTAL = REGISTRY.register("magic_perfusion_pedestal", () -> of(
            MagicPerfusionPedestalBlockEntity::new,
            MATBlocks.MAGIC_PERFUSION_PEDESTAL.get()
    ));
    public static final RegistryObject<BlockEntityType<PedestalBlockEntity>> PEDESTAL = REGISTRY.register("pedestal", () -> of(
            PedestalBlockEntity::new,
            MATBlocks.GOLD_GRAINED_MARBLE_PEDESTAL.get()
    ));

    @SuppressWarnings("DataFlowIssue")
    static <T extends BlockEntity> BlockEntityType<T> of(BlockEntityType.BlockEntitySupplier<? extends T> factory, Block... validBlocks) {
        return BlockEntityType.Builder.<T>of(factory, validBlocks).build(null);
    }
}
