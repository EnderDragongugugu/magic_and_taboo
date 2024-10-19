package enderdragon.magic_and_taboo.block.grower;

import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractMegaTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.NotNull;

import static enderdragon.magic_and_taboo.MagicAndTabooMod.makeKey;
import static net.minecraft.core.registries.Registries.CONFIGURED_FEATURE;

public class FirTreeGrower extends AbstractMegaTreeGrower {
    public static final ResourceKey<ConfiguredFeature<?, ?>> FIR_TREE = makeKey(CONFIGURED_FEATURE, "fir_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MEGA_FIR = makeKey(CONFIGURED_FEATURE, "mega_fir");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FIR_PINE = makeKey(CONFIGURED_FEATURE, "fir_pine");
    public static final FirTreeGrower INSTANCE = new FirTreeGrower();

    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(@NotNull RandomSource random, boolean hasFlowers) {
        return FIR_TREE;
    }

    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredMegaFeature(RandomSource random) {
        return random.nextBoolean() ? MEGA_FIR : FIR_PINE;
    }
}
