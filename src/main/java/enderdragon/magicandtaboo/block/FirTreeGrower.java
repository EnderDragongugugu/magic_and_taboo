package enderdragon.magicandtaboo.block;

import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.NotNull;

public class FirTreeGrower extends AbstractTreeGrower {
    public static final FirTreeGrower INSTANCE = new FirTreeGrower();

    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(@NotNull RandomSource random, boolean hasFlowers) {
        //TODO: replace with fir tree features
        if (random.nextInt(10) == 0) {
            return hasFlowers ? TreeFeatures.FANCY_OAK_BEES_005 : TreeFeatures.FANCY_OAK;
        } else {
            return hasFlowers ? TreeFeatures.OAK_BEES_005 : TreeFeatures.OAK;
        }
    }
}
