package net.magic_and_taboo.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class LogBlock extends Block {
    public LogBlock(Settings settings) {
        super(settings);
//        setDefaultState(this.getStateManager().getDefaultState().with());
    }
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState();
    }
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {

    }
}
