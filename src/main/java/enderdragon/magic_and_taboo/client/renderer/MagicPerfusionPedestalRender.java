package enderdragon.magic_and_taboo.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import enderdragon.magic_and_taboo.block.entity.MagicPerfusionPedestalBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;

public class MagicPerfusionPedestalRender implements BlockEntityRenderer<MagicPerfusionPedestalBlockEntity> {
    protected final ItemRenderer itemRenderer;

    public MagicPerfusionPedestalRender(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(MagicPerfusionPedestalBlockEntity pedestal, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int pPackedLight, int pPackedOverlay) {
        var stack = pedestal.getItem();
        if (stack.isEmpty()) return;
        int tick = pedestal.tick;
        poseStack.pushPose();
        poseStack.translate(0.5F, 1.25F + Mth.sin((tick + 2.5F) / 15.0F) * 0.075F, 0.5F);
        float rotation = (tick + partialTicks) * 2.0f;
        poseStack.mulPose(Axis.YP.rotationDegrees(rotation));
        float scale = 0.35F;
        poseStack.scale(scale, scale, scale);
        this.itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, pPackedLight, pPackedOverlay, poseStack, buffer, pedestal.getLevel(), 0);
        poseStack.popPose();
    }
}
