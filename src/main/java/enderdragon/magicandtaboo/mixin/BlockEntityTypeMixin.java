package enderdragon.magicandtaboo.mixin;

import com.google.common.collect.ImmutableSet;
import enderdragon.magicandtaboo.util.BlockEntityTypeEx;
import it.unimi.dsi.fastutil.objects.ObjectIterators;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Set;

@Mixin(BlockEntityType.class)
public class BlockEntityTypeMixin implements BlockEntityTypeEx {
    @Mutable
    @Shadow
    @Final
    private Set<Block> validBlocks;

    @Override
    public void magic_and_taboo$acceptBlocks(Block @NotNull ... blocks) {
        this.validBlocks = new ImmutableSet.Builder<Block>().addAll(this.validBlocks).addAll(ObjectIterators.wrap(blocks)).build();
    }
}
