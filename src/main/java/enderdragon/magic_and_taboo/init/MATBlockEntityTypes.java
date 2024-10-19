package enderdragon.magic_and_taboo.init;

import enderdragon.magic_and_taboo.MagicAndTabooMod;
import enderdragon.magic_and_taboo.block.entity.WorkHubBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MATBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MagicAndTabooMod.MOD_ID);
    public static final RegistryObject<BlockEntityType<WorkHubBlockEntity>> WORK_HUB = REGISTRY.register("work_hub", () ->
            BlockEntityType.Builder.of(WorkHubBlockEntity::new, MATBlocks.WORK_HUB.get()).build(null)
    );
}
