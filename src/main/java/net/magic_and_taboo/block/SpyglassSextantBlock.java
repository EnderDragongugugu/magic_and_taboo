package net.magic_and_taboo.block;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.listener.GameEventListener;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class SpyglassSextantBlock extends BlockWithEntity  {
//    private static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    private static final VoxelShape BASE_SHAPE = Block.createCuboidShape(0.0D,0.0D,0.0D,16.0D,16.0D,16.0D);
    protected SpyglassSextantBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return null;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return super.getTicker(world, state, type);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> GameEventListener getGameEventListener(World world, T blockEntity) {
        return super.getGameEventListener(world, blockEntity);
    }

//    @Override
//    public boolean canFillWithFluid(BlockView world, BlockPos pos, BlockState state, Fluid fluid) {
//        return Waterloggable.super.canFillWithFluid(world, pos, state, fluid);
//    }

//    @Override
//    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
//        return Waterloggable.super.tryFillWithFluid(world, pos, state, fluidState);
//    }

//    @Override
//    public ItemStack tryDrainFluid(WorldAccess world, BlockPos pos, BlockState state) {
//        return Waterloggable.super.tryDrainFluid(world, pos, state);
//    }

//    @Override
//    public Optional<SoundEvent> getBucketFillSound() {
//        return Waterloggable.super.getBucketFillSound();
//    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return BASE_SHAPE;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return BASE_SHAPE;
    }
}
