package enderdragon.magic_and_taboo.data;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Set;
import java.util.function.BiConsumer;

import static enderdragon.magic_and_taboo.init.MATBlocks.*;

public class MATBlockLootProvider extends BlockLootSubProvider {
    public MATBlockLootProvider() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), new HashMap<>());
    }

    @Override
    protected void generate() {
        this.dropSelf(MERCURY_ORE.get());
        this.dropSelf(ENCHANTED_CRUCIBLE.get());
        this.dropSelf(FIR_PLANKS.get());
        this.dropSelf(FIR_SAPLING.get());
        this.dropSelf(FIR_LOG.get());
        this.dropSelf(STRIPPED_FIR_LOG.get());
        this.dropSelf(FIR_WOOD.get());
        this.dropSelf(STRIPPED_FIR_WOOD.get());
        this.add(FIR_LEAVES.get(), leaves -> this.createLeavesDrops(leaves, FIR_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
        this.dropSelf(FIR_SIGN.get());
        this.dropSelf(FIR_HANGING_SIGN.get());
        this.dropSelf(FIR_PRESSURE_PLATE.get());
        this.dropSelf(FIR_TRAPDOOR.get());
        this.dropSelf(FIR_STAIRS.get());
        this.dropSelf(FIR_BUTTON.get());
        this.dropSelf(FIR_SLAB.get());
        this.dropSelf(FIR_FENCE_GATE.get());
        this.dropSelf(FIR_FENCE.get());
        this.add(FIR_DOOR.get(), this::createDoorTable);
        this.dropPottedContents(POTTED_FIR_SAPLING.get());
        this.dropSelf(WORK_HUB.get());
    }

    @Override
    public void generate(@NotNull BiConsumer<ResourceLocation, LootTable.Builder> output) {
        this.generate();
        this.map.forEach(output);
    }
}
