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
    protected final ItemRenderer itemRenderer;

    public MagicCraftsmanTableRender(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(MagicCraftsmanTableBlockEntity table, float partialTicks, PoseStack matrices, MultiBufferSource buffer, int light, int overlay) {
        int tick = table.ticks;
        float time = tick + partialTicks;
        var nonEmptyStacks = table.getStacks().stream()
                .filter(stack -> !stack.isEmpty())
                .limit(3)
                .toList();

        if (nonEmptyStacks.isEmpty()) return;

        int count = nonEmptyStacks.size();
        float baseAngle = 360.0F / count;
        float radius = 0.25F;

        for (int i = 0; i < count; i++) {
            var itemStack = nonEmptyStacks.get(i);
            matrices.pushPose();

            matrices.translate(0.5F, 1.2F, 0.5F);

            float angle = time * 4.0F + i * baseAngle;
            float rad = (float) Math.toRadians(angle);

            float offsetX = Mth.cos(rad) * radius;
            float offsetZ = Mth.sin(rad) * radius;
            matrices.translate(offsetX, 0, offsetZ);

            matrices.mulPose(Axis.YP.rotationDegrees(-angle + 90));

            float scale = 0.25F;
            matrices.scale(scale, scale, scale);

            this.itemRenderer.renderStatic(itemStack, ItemDisplayContext.FIXED, light, overlay, matrices, buffer, table.getLevel(), 0);
            matrices.popPose();
        }
    }
}
