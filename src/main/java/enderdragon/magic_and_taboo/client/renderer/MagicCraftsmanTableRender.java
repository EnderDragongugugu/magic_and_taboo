package enderdragon.magic_and_taboo.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import enderdragon.magic_and_taboo.block.entity.MagicCraftsmanTableBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;

public class MagicCraftsmanTableRender implements BlockEntityRenderer<MagicCraftsmanTableBlockEntity> {
    protected static final float RENDER_SCALE = 0.25F;
    protected static final float ITEM_RADIUS = 0.25F;
    protected final ItemRenderer itemRenderer;

    public MagicCraftsmanTableRender(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(MagicCraftsmanTableBlockEntity table, float partialTicks, PoseStack matrices, MultiBufferSource buffer, int light, int overlay) {
        float baseAngle = (table.ticks + partialTicks) * (float) Math.toRadians(4.0);
        var stacks = table.getStacks().stream()
                .filter(stack -> !stack.isEmpty())
                .limit(3)
                .toList();
        int count = stacks.size();
        if (count == 0) return;
        float unitAngle = 2.0F * (float) Math.PI / count;
        for (int i = 0; i < count; ++i) {
            var stack = stacks.get(i);
            matrices.pushPose();
            matrices.translate(0.5F, 1.2F, 0.5F);
            float angle = baseAngle + i * unitAngle;
            matrices.translate(Mth.cos(angle) * ITEM_RADIUS, 0, Mth.sin(angle) * ITEM_RADIUS);
            matrices.mulPose(Axis.YP.rotation(0.5F * (float) Math.PI - angle));
            matrices.scale(RENDER_SCALE, RENDER_SCALE, RENDER_SCALE);
            this.itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, light, overlay, matrices, buffer, table.getLevel(), 0);
            matrices.popPose();
        }
    }
}
