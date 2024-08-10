package enderdragon.magicandtaboo;

import enderdragon.magicandtaboo.block.MATBlock;
import enderdragon.magicandtaboo.client.MATClient;
import enderdragon.magicandtaboo.effect.MATEffect;
import enderdragon.magicandtaboo.enchants.MATEnchants;
import enderdragon.magicandtaboo.item.MATItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(MagicAndTabooMod.MODID)
public class MagicAndTabooMod {

    public static final String MODID = "magicandtaboo";
    public static final Logger LOGGER = LogManager.getLogger();


    public MagicAndTabooMod() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        if(FMLEnvironment.dist.isClient()){
            eventBus.addListener(MATClient::init);
        }
        MATEnchants.ENCHATMENT.register(eventBus);
        MATEffect.EFFECT.register(eventBus);
        MATBlock.BLOCKS.register(eventBus);
        MATItem.ITEMS.register(eventBus);
    }
}
