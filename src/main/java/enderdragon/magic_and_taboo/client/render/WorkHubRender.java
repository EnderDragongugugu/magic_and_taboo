package enderdragon.magic_and_taboo.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import enderdragon.magic_and_taboo.MagicAndTabooMod;
import enderdragon.magic_and_taboo.block.entity.WorkHubBlockEntity;
import enderdragon.magic_and_taboo.client.model.WorkHubToolModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WorkHubRender implements BlockEntityRenderer<WorkHubBlockEntity> {
    protected ItemRenderer itemRenderer;
    protected WorkHubToolModel workHubModel;

    public WorkHubRender(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
        this.workHubModel = new WorkHubToolModel(context.bakeLayer(WorkHubToolModel.LAYER_LOCATION));
    }

    @Override
    public void render(WorkHubBlockEntity hub, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        NonNullList<ItemStack> itemStacksList = hub.getItems();
        VertexConsumer vertexconsumer = pBuffer.getBuffer(RenderType.entitySolid(MagicAndTabooMod.makeId("textures/entity/blocks/work_hub_tool.png")));
        pPoseStack.pushPose();
        pPoseStack.translate(0.5F, 1.0625F, 0.5F);
        float f = hub.getBlockState().getValue(HorizontalDirectionalBlock.FACING).getClockWise().toYRot();
        pPoseStack.mulPose(Axis.YP.rotationDegrees(Math.abs(f - 270.0F)));
        pPoseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
        pPoseStack.translate(0.0F, -0.44F, 0.0F);
        if (!itemStacksList.isEmpty()) {
            ItemStack output = itemStacksList.get(8);
            workHubModel.setOtherGroupVisible("mortar", !itemStacksList.get(0).isEmpty());
            workHubModel.setOtherGroupVisible("blaze_burner", !itemStacksList.get(1).isEmpty());
            workHubModel.setOtherGroupVisible("container", !itemStacksList.get(9).isEmpty());
            if (itemStacksList.get(8).isEmpty()) {
                workHubModel.setOutputGroupVisible(0, 0);
            } else {
                workHubModel.setOutputGroupVisible(output.getCount(), output.getMaxStackSize());
            }
            for (int i = 2; i <= 7; i++) {
                ItemStack itemStack = itemStacksList.get(i);
                workHubModel.setItemStackGroupVisible(i, !itemStack.isEmpty());
            }
        }
        workHubModel.renderToBuffer(pPoseStack, vertexconsumer, pPackedLight, pPackedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
        pPoseStack.popPose();
    }
}
