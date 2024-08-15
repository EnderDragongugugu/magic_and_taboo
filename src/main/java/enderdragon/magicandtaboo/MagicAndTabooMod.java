package enderdragon.magicandtaboo;

import init.MATBlocks;
import enderdragon.magicandtaboo.effect.MATEffect;
import enderdragon.magicandtaboo.enchantment.MATEnchantments;
import enderdragon.magicandtaboo.item.MATItemGroups;
import init.MATItems;
import enderdragon.magicandtaboo.item.UnknownSwordItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(MagicAndTabooMod.MOD_ID)
public class MagicAndTabooMod {
    public static final String MOD_ID = "magicandtaboo";
    private static final ResourceLocation ROOT = new ResourceLocation(MOD_ID, "root");
    private static final Logger LOGGER = LogManager.getLogger();

    public static ResourceLocation makeId(String name) {
        return ROOT.withPath(name);
    }

    public MagicAndTabooMod() {
        var modBus = FMLJavaModLoadingContext.get().getModEventBus();
        MATEnchantments.REGISTRY.register(modBus);
        MATEffect.REGISTRY.register(modBus);
        MATBlocks.REGISTRY.register(modBus);
        MATItems.REGISTRY.register(modBus);
        MATItemGroups.REGISTRY.register(modBus);
        var forgeBus = MinecraftForge.EVENT_BUS;
        forgeBus.addListener(UnknownSwordItem::onLivingDeath);
    }
}
