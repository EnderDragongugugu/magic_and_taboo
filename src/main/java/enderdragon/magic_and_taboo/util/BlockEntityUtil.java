package enderdragon.magic_and_taboo.util;

import net.minecraft.world.level.block.entity.BlockEntity;

public class BlockEntityUtil {
    public static void notifyChanged(BlockEntity entity) {
        var level = entity.getLevel();
        if (level == null || level.isClientSide) return;
        var state = entity.getBlockState();
        level.sendBlockUpdated(entity.getBlockPos(), state, state, 3);
    }
}
