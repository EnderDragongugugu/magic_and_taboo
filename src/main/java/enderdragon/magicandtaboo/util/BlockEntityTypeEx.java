package enderdragon.magicandtaboo.util;

import net.minecraft.world.level.block.Block;

public interface BlockEntityTypeEx {
    default void magic_and_taboo$acceptBlocks(Block... blocks) {}
}
