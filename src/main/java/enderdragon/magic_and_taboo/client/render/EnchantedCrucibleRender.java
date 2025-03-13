package enderdragon.magic_and_taboo.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import enderdragon.magic_and_taboo.block.entity.EnchantedCrucibleBlockEntity;
import enderdragon.magic_and_taboo.registry.AlchemyElement;
import enderdragon.magic_and_taboo.registry.Element;
import enderdragon.magic_and_taboo.util.RegistryAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class EnchantedCrucibleRender implements BlockEntityRenderer<EnchantedCrucibleBlockEntity> {
    private static final RenderType RENDER_TYPE = RenderType.itemEntityTranslucentCull(new ResourceLocation("textures/item/barrier.png"));
    protected final ItemRenderer itemRenderer;
    protected final EntityRenderDispatcher entityRenderer;
    protected final Font font;

    public EnchantedCrucibleRender(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
        this.entityRenderer = context.getEntityRenderer();
        this.font = context.getFont();
    }

    @Override
    public void render(EnchantedCrucibleBlockEntity crucible, float partialTick, PoseStack matrices, MultiBufferSource buffers, int light, int overlay) {
        var info = crucible.getRenderingInfo();
        if (info.fluid.isEmpty()) return;
        var height = info.fluid.getAmount();
        this.renderItemStacks(crucible, height, matrices, buffers, light, overlay);
        matrices.pushPose();
        matrices.translate(0.0F, 0.375F + 0.00036F * height, 0.0F);
        this.renderFluidCube(info.fluid, matrices, buffers.getBuffer(RenderType.solid()), info.fluidColor, light, overlay);
        matrices.popPose();
        if (!(Minecraft.getInstance().hitResult instanceof BlockHitResult hit && hit.getBlockPos().equals(crucible.getBlockPos())))
            return;
        var orientation = this.entityRenderer.cameraOrientation();
//        String text = "笨龙";
//        matrices.pushPose();
//        matrices.translate(0.5F, 1.25F, 0.5F);
//        matrices.mulPose(orientation);
//        matrices.scale(-0.025F, -0.025F, 0.025F);
//        var font = this.font;
//        float left = (float) (-font.width(text) / 2);
//        {
//            var matrix = matrices.last().pose();
//            font.drawInBatch(text, left, 0, 0x20FFFFFF, false, matrix, buffers, Font.DisplayMode.SEE_THROUGH, (int) (Minecraft.getInstance().options.getBackgroundOpacity(0.25F) * 255.0F) << 24, light);
//            font.drawInBatch(text, left, 0, -1, false, matrix, buffers, Font.DisplayMode.NORMAL, 0, light);
//        }
//        matrices.popPose();
        matrices.pushPose();
        matrices.translate(0.5F, 1.5F, 0.5F);
        matrices.mulPose(orientation);
        matrices.mulPose(Axis.YP.rotationDegrees(180.0F));
        matrices.scale(-0.2F, -0.2F, 0.2F);
        var data = Element.fromStacks(RegistryAccessor.access(), crucible.getStacks(), crucible.getTemperature());
        var i = 0;
        for (var entry : data.object2FloatEntrySet()) {
            matrices.pushPose();
            matrices.translate(-(data.size() - 1.0F) * 0.5F + i, 0.0F, 0.0F);
            {
                var type = RenderType.itemEntityTranslucentCull(entry.getKey().icon());
                var buffer = buffers.getBuffer(type);
                var pose = matrices.last();
                var matrix = pose.pose();
                var normal = pose.normal();
                vertex(buffer, matrix, normal, -0.5F, -0.25F, 0, 0, light);
                vertex(buffer, matrix, normal, 0.5F, -0.25F, 1, 0, light);
                vertex(buffer, matrix, normal, 0.5F, 0.75F, 1, 1, light);
                vertex(buffer, matrix, normal, -0.5F, 0.75F, 0, 1, light);
            }
            matrices.popPose();
            i++;
        }

        matrices.popPose();
    }

    private void renderFluidCube(FluidStack stack, PoseStack matrices, VertexConsumer buffer, int color, int light, int overlay) {
        var matrix = matrices.last().pose();
        var fluid = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(IClientFluidTypeExtensions.of(stack.getFluid()).getStillTexture(stack));
        float size = 0.18F;
        float maxV = (fluid.getV1() - fluid.getV0()) * size;
        float minV = (fluid.getV1() - fluid.getV0()) * (1 - size);
        var r = FastColor.ARGB32.red(color);
        var g = FastColor.ARGB32.green(color);
        var b = FastColor.ARGB32.blue(color);
        var a = 255;
        buffer.vertex(matrix, size, 0, 1 - size).color(r, g, b, a).uv(fluid.getU0(), fluid.getV0() + maxV).uv2(light).overlayCoords(overlay).normal(1, 1, 1).endVertex();
        buffer.vertex(matrix, 1 - size, 0, 1 - size).color(r, g, b, a).uv(fluid.getU1(), fluid.getV0() + maxV).uv2(light).overlayCoords(overlay).normal(1, 1, 1).endVertex();
        buffer.vertex(matrix, 1 - size, 0, size).color(r, g, b, a).uv(fluid.getU1(), fluid.getV0() + minV).uv2(light).overlayCoords(overlay).normal(1, 1, 1).endVertex();
        buffer.vertex(matrix, size, 0, size).color(r, g, b, a).uv(fluid.getU0(), fluid.getV0() + minV).uv2(light).overlayCoords(overlay).normal(1, 1, 1).endVertex();
    }

    private void renderItemStacks(EnchantedCrucibleBlockEntity crucible, float height, PoseStack matrices, MultiBufferSource buffers, int pPackedLight, int pPackedOverlay) {
        var level = Minecraft.getInstance().level;
        if (level == null) return;
        var stacks = crucible.getStacks();
        var list = crucible.getCookingTime();
        var registry = level.registryAccess().registryOrThrow(AlchemyElement.RESOURCE_KEY);
        int tick = crucible.tick;
        float extraRot = tick % 360.0F;
        float extraOffset = 2.0F + 0.00225F * height;
        for (int i = 0; i < stacks.size(); i++) {
            var stack = stacks.get(i);
            if (stack.isEmpty()) continue;
            int time = registry.getOptional(ForgeRegistries.ITEMS.getKey(stack.getItem())).map(AlchemyElement::time).orElse(300);
            if (list[i] < time) {
                matrices.pushPose();
                matrices.translate(0.5F, 0.0F, 0.5F);
                matrices.mulPose(Axis.YP.rotationDegrees(45.0F * i + extraRot));
                matrices.scale(0.175F, 0.175F, 0.175F);
                matrices.translate(0.7F, extraOffset + Mth.sin((tick + i * 5) / 10.0F) * 0.1F, 0.9F);
                this.itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, pPackedLight, pPackedOverlay, matrices, buffers, level, 0);
                matrices.popPose();
            }
        }
    }

    private static void vertex(VertexConsumer buffer, Matrix4f matrix, Matrix3f normal, float x, float y, float u, float v, int light) {
        buffer.vertex(matrix, x, y, 0.0F)
                .color(255, 255, 255, 255)
                .uv(u, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(light)
                .normal(normal, 0.0F, 1.0F, 0.0F)
                .endVertex();
    }
}
