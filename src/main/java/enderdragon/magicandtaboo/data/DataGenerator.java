package enderdragon.magicandtaboo.data;

import enderdragon.magicandtaboo.MagicAndTabooMod;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(modid = MagicAndTabooMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerator {
    private static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        var generator = event.getGenerator();
        var output = generator.getPackOutput();
        var registry = event.getLookupProvider();
        var helper = event.getExistingFileHelper();
        if (event.includeClient()) {
            generator.addProvider(true, new MATBlockStateProvider(output, helper));
            generator.addProvider(true, new MATItemModelProvider(output, helper));
        }
        var blockTags = generator.addProvider(true, new MATBlockTagProvider(output, registry, helper));
        generator.addProvider(true, new MATItemTagProvider(output, registry, blockTags.contentsGetter(), helper));
    }
}
