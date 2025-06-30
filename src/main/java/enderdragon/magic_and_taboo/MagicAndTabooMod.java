package enderdragon.magic_and_taboo;

import enderdragon.magic_and_taboo.capability.PlayerMagicPointImpl;
import enderdragon.magic_and_taboo.effect.HemolysisEffect;
import enderdragon.magic_and_taboo.init.*;
import enderdragon.magic_and_taboo.item.OccultCodexItem;
import enderdragon.magic_and_taboo.item.SacrificialDaggerItem;
import enderdragon.magic_and_taboo.network.NetworkHandler;
import enderdragon.magic_and_taboo.registry.AlchemyElement;
import enderdragon.magic_and_taboo.registry.Element;
import enderdragon.magic_and_taboo.util.BlockEntityTypeEx;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DataPackRegistryEvent;
import org.jetbrains.annotations.NotNull;

@Mod(MagicAndTabooMod.MOD_ID)
public class MagicAndTabooMod {
    public static final String MOD_ID = "magic_and_taboo";
    private static final ResourceLocation ROOT = new ResourceLocation(MOD_ID, "root");

    public static <T> @NotNull ResourceKey<T> makeKey(ResourceKey<? extends Registry<T>> registry, String name) {
        return ResourceKey.create(registry, ROOT.withPath(name));
    }

    public static @NotNull ResourceLocation makeId(String name) {
        return ROOT.withPath(name);
    }

    public MagicAndTabooMod() {
        var modBus = FMLJavaModLoadingContext.get().getModEventBus();
        MATEnchantments.REGISTRY.register(modBus);
        MATEffects.REGISTRY.register(modBus);
        MATBlocks.REGISTRY.register(modBus);
        MATItems.REGISTRY.register(modBus);
        MATItemGroups.REGISTRY.register(modBus);
        MATBlockEntities.REGISTRY.register(modBus);
        MATMenuTypes.REGISTRY.register(modBus);
        MATRecipeTypes.REGISTRY.register(modBus);
        MATSerializers.REGISTRY.register(modBus);
        MATFluids.FLUIDS.register(modBus);
        MATFluids.FLUID_TYPES.register(modBus);
        modBus.addListener(MagicAndTabooMod::onComplete);
        modBus.addListener(MagicAndTabooMod::onCommonSetup);
        modBus.addListener(MagicAndTabooMod::registerDataPackRegistry);
        var forgeBus = MinecraftForge.EVENT_BUS;
        forgeBus.addListener(PlayerMagicPointImpl::tick);
        forgeBus.addListener(SacrificialDaggerItem::onLivingDeath);
        forgeBus.addListener(HemolysisEffect::livingHurtEvent);
        forgeBus.addListener(OccultCodexItem::onPlayerLogin);
        forgeBus.addGenericListener(Entity.class, MATCapabilities::attachCapabilitiesEvent);

    }

    public static void onComplete(FMLLoadCompleteEvent event) {
        event.enqueueWork(() -> {
            ((BlockEntityTypeEx) BlockEntityType.SIGN).magic_and_taboo$acceptBlocks(
                    MATBlocks.FIR_SIGN.get(),
                    MATBlocks.FIR_WALL_SIGN.get()
            );
            ((BlockEntityTypeEx) BlockEntityType.HANGING_SIGN).magic_and_taboo$acceptBlocks(
                    MATBlocks.FIR_HANGING_SIGN.get(),
                    MATBlocks.FIR_WALL_HANGING_SIGN.get()
            );
        });
    }

    public static void onCommonSetup(FMLCommonSetupEvent event) {
        NetworkHandler.register();
    }

    public static void registerDataPackRegistry(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(AlchemyElement.RESOURCE_KEY, AlchemyElement.CODEC, AlchemyElement.CODEC);
        event.dataPackRegistry(Element.RESOURCE_KEY, Element.CODEC, Element.CODEC);
    }
}
