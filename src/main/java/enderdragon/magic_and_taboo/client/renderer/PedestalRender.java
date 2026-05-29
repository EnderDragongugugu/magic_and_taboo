package enderdragon.magic_and_taboo.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import enderdragon.magic_and_taboo.block.entity.PedestalBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;

public class PedestalRender implements BlockEntityRenderer<PedestalBlockEntity> {
    protected static final float RENDER_SCALE = 0.35F;
    protected final ItemRenderer itemRenderer;

    public PedestalRender(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(PedestalBlockEntity pedestal, float partialTicks, PoseStack matrices, MultiBufferSource buffer, int light, int overlay) {
        var stack = pedestal.getStack();
        // 如果物品为空或已被视觉消耗，则不渲染
        if (stack.isEmpty() || pedestal.isVisuallyConsumed()) return;
        float time = pedestal.ticks + partialTicks;
        matrices.pushPose();
        matrices.translate(
                0.5F,
                1.4F + /* bob: */ Mth.sin((time + 2.5F) / 15.0F) * 0.075F,
                0.5F
        );
        matrices.mulPose(Axis.YP.rotationDegrees(time * 2.0F));
        matrices.scale(RENDER_SCALE, RENDER_SCALE, RENDER_SCALE);
        this.itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, light, overlay, matrices, buffer, pedestal.getLevel(), 0);
        matrices.popPose();
    }
}
