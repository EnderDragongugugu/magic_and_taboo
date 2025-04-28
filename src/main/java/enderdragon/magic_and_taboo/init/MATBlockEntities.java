package enderdragon.magic_and_taboo.init;

import enderdragon.magic_and_taboo.MagicAndTabooMod;
import enderdragon.magic_and_taboo.block.entity.EnchantedCrucibleBlockEntity;
import enderdragon.magic_and_taboo.block.entity.MagicPerfusionPedestalBlockEntity;
import enderdragon.magic_and_taboo.block.entity.WorkHubBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MATBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MagicAndTabooMod.MOD_ID);
    public static final RegistryObject<BlockEntityType<WorkHubBlockEntity>> WORK_HUB = REGISTRY.register("work_hub", () ->
            BlockEntityType.Builder.of(WorkHubBlockEntity::new, MATBlocks.WORK_HUB.get()).build(null)
    );
    public static final RegistryObject<BlockEntityType<EnchantedCrucibleBlockEntity>> ENCHANTED_CRUCIBLE = REGISTRY.register("enchanted_crucible", () ->
            BlockEntityType.Builder.of(EnchantedCrucibleBlockEntity::new, MATBlocks.ENCHANTED_CRUCIBLE.get()).build(null)
    );
    public static final RegistryObject<BlockEntityType<MagicPerfusionPedestalBlockEntity>> MAGIC_PERFUSION_PEDESTAL = REGISTRY.register("magic_perfusion_pedestal", () ->
            BlockEntityType.Builder.of(MagicPerfusionPedestalBlockEntity::new, MATBlocks.MAGIC_PERFUSION_PEDESTAL.get()).build(null)
    );
}
