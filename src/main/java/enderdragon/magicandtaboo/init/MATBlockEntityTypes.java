package enderdragon.magicandtaboo.init;

import enderdragon.magicandtaboo.MagicAndTabooMod;
import enderdragon.magicandtaboo.block.entity.FederationWorkstationBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MATBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> BLOCKENTITYTYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MagicAndTabooMod.MOD_ID);
    public static final RegistryObject<BlockEntityType<FederationWorkstationBlockEntity>> FederationWorkstationBlockEntity = BLOCKENTITYTYPES.register("federation_workstation_block_entity_type", ()-> BlockEntityType.Builder.of(FederationWorkstationBlockEntity::new,MATBlocks.FEDERATION_WORKSTATION.get()).build(null));
}
