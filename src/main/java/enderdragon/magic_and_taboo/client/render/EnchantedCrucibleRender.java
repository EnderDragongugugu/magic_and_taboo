package enderdragon.magic_and_taboo.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import enderdragon.magic_and_taboo.block.entity.EnchantedCrucibleBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.LiquidBlockRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.Blocks;

public class EnchantedCrucibleRender implements BlockEntityRenderer<EnchantedCrucibleBlockEntity> {
    protected BlockRenderDispatcher waterModel;
    protected ItemRenderer itemRenderer;
    private static final ResourceLocation WATER_TEXTURE = new ResourceLocation("minecraft:block/water_still");


    public EnchantedCrucibleRender(BlockEntityRendererProvider.Context context) {
        this.waterModel = context.getBlockRenderDispatcher();
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(EnchantedCrucibleBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        pPoseStack.pushPose();
        BlockAndTintGetter level = Minecraft.getInstance().level;
        if (level == null) return;
        BlockPos pos = pBlockEntity.getBlockPos();
        pPoseStack.translate(0.0F, 0.5F, 0.0F);
        new LiquidBlockRenderer().tesselate(level, pos, pBuffer.getBuffer(RenderType.solid()), Blocks.WATER.defaultBlockState(), Blocks.WATER.defaultBlockState().getFluidState());
//        waterModel.renderLiquid(pos, level, pBuffer.getBuffer(RenderType.solid()), Blocks.WATER.defaultBlockState(), Blocks.WATER.defaultBlockState().getFluidState());
        pPoseStack.popPose();

    }
}
