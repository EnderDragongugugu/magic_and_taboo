package net.magic_and_taboo.block;
import net.minecraft.block.Block;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
public class FlammableBlock extends Block {
    public FlammableBlock(Settings settings, int burn, int spread) {
        super(settings);
        FlammableBlockRegistry.getDefaultInstance().add(this, spread, burn);
    }
}
