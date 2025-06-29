package enderdragon.magic_and_taboo.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import enderdragon.magic_and_taboo.block.entity.WorkHubBlockEntity;
import enderdragon.magic_and_taboo.client.model.WorkHubToolModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static enderdragon.magic_and_taboo.MagicAndTabooMod.makeId;
import static net.minecraft.client.renderer.entity.EnderDragonRenderer.CRYSTAL_BEAM_LOCATION;

@OnlyIn(Dist.CLIENT)
public class WorkHubRender implements BlockEntityRenderer<WorkHubBlockEntity> {
    private static final RenderType TEXTURE = RenderType.entityCutout(makeId("textures/entity/blocks/work_hub_tool.png"));

    private static final float HALF_SQRT_3 = (float) (Math.sqrt(3.0D) / 2.0D);
    private static final RenderType BEAM = RenderType.entitySmoothCutout(CRYSTAL_BEAM_LOCATION);

    protected final ItemRenderer itemRenderer;
    protected final WorkHubToolModel model;

    public WorkHubRender(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
        this.model = new WorkHubToolModel(context.bakeLayer(WorkHubToolModel.LAYER_LOCATION));
    }

    @Override
    public void render(WorkHubBlockEntity hub, float partialTick, PoseStack matrices, MultiBufferSource buffers, int light, int overlay) {
        matrices.pushPose();
        matrices.translate(0.5F, 1.0625F, 0.5F);
        matrices.mulPose(Axis.YP.rotationDegrees(Math.abs(
                hub.getBlockState().getValue(HorizontalDirectionalBlock.FACING).getClockWise().toYRot() - 270.0F
        )));
        matrices.mulPose(Axis.ZP.rotationDegrees(180.0F));
        matrices.translate(0.0F, -0.44F, 0.0F);
        this.model.setup(hub.getStacks());
//        this.model.renderToBuffer(matrices, buffers.getBuffer(RenderType.endGateway()), light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
        this.model.renderToBuffer(matrices, buffers.getBuffer(TEXTURE), light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
        matrices.popPose();
    }
}
