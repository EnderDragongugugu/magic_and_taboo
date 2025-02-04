package enderdragon.magic_and_taboo.data;

import enderdragon.magic_and_taboo.MagicAndTabooMod;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Set;

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
        var registryDataGenerator = new RegistryDataGenerator(output, registry);
        generator.addProvider(true, new MATItemTagProvider(output, registry, blockTags.contentsGetter(), helper));
        generator.addProvider(true, new MATEntityTagProvider(output, registry, helper));
        generator.addProvider(true, new MATRecipeProvider(output));
        generator.addProvider(true, new LootTableProvider(output, Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(MATBlockLootProvider::new, LootContextParamSets.BLOCK)
        )));
        generator.addProvider(true, new MATAdvancementProvider(output, registry, helper));
        generator.addProvider(true, registryDataGenerator);
    }
}
