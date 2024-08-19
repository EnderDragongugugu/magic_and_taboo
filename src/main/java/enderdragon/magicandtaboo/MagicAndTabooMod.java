package enderdragon.magicandtaboo;

import enderdragon.magicandtaboo.effect.MATEffect;
import enderdragon.magicandtaboo.enchantment.MATEnchantments;
import enderdragon.magicandtaboo.init.MATBlocks;
import enderdragon.magicandtaboo.init.MATItemGroups;
import enderdragon.magicandtaboo.init.MATItems;
import enderdragon.magicandtaboo.item.equipment.SacrificialDagger;
import enderdragon.magicandtaboo.util.BlockEntityTypeEx;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

@Mod(MagicAndTabooMod.MOD_ID)
public class MagicAndTabooMod {
    public static final String MOD_ID = "magicandtaboo";
    private static final ResourceLocation ROOT = new ResourceLocation(MOD_ID, "root");
    private static final Logger LOGGER = LogManager.getLogger();

    public static <T> @NotNull ResourceKey<T> makeKey(ResourceKey<? extends Registry<T>> registry, String name) {
        return ResourceKey.create(registry, ROOT.withPath(name));
    }

    public static @NotNull ResourceLocation makeId(String name) {
        return ROOT.withPath(name);
    }

    public MagicAndTabooMod() {
        var modBus = FMLJavaModLoadingContext.get().getModEventBus();
        MATEnchantments.REGISTRY.register(modBus);
        MATEffect.REGISTRY.register(modBus);
        MATBlocks.REGISTRY.register(modBus);
        MATItems.REGISTRY.register(modBus);
        MATItemGroups.REGISTRY.register(modBus);
        modBus.addListener(MagicAndTabooMod::onComplete);
        var forgeBus = MinecraftForge.EVENT_BUS;
        forgeBus.addListener(SacrificialDagger::onLivingDeath);
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
}
