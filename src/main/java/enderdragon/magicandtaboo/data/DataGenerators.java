package enderdragon.magicandtaboo.data;

import enderdragon.magicandtaboo.MagicAndTabooMod;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MagicAndTabooMod.MODID,bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        Boolean includeClint = event.includeClient();
        Boolean includeServer = event.includeServer();
//        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper helper = event.getExistingFileHelper();
        BlockStates blockStates = new BlockStates(output, helper);
        MagicAndTabooMod.LOGGER.debug(event.includeClient());
        generator.addProvider(includeClint, blockStates);
        generator.addProvider(includeClint, new ItemModels(output,blockStates.models().existingFileHelper));
    }
}
