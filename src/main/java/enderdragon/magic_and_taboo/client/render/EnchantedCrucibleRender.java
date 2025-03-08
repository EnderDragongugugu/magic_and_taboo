package enderdragon.magic_and_taboo.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import enderdragon.magic_and_taboo.block.entity.EnchantedCrucibleBlockEntity;
import enderdragon.magic_and_taboo.registry.AlchemyElement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.FastColor;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.joml.Matrix4f;

public class EnchantedCrucibleRender implements BlockEntityRenderer<EnchantedCrucibleBlockEntity> {
    protected ItemRenderer itemRenderer;

    public EnchantedCrucibleRender(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(EnchantedCrucibleBlockEntity crucible, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        var level = crucible.getLevel();
        if (level == null) return;
        FluidStack fluid = crucible.getFluids().getFluid();
        if (fluid.isEmpty()) return;
        var height = fluid.getAmount();
        renderItemStack(crucible, height, pPoseStack, pBuffer, pPackedLight, pPackedOverlay);
        pPoseStack.pushPose();
        pPoseStack.translate(0.0F, 0.375F + 0.00036F * height, 0.0F);
        var biome = level.getBiome(crucible.getBlockPos()).get();
        VertexConsumer vertexConsumer = pBuffer.getBuffer(RenderType.solid());
        var color = fluid.getFluid().isSame(Fluids.WATER) ? biome.getWaterColor() : 0xffffff;
        renderFluidCube(fluid, pPoseStack, vertexConsumer, color, pPackedLight, pPackedOverlay);
        pPoseStack.popPose();
    }

    private void renderFluidCube(FluidStack fluidStack, PoseStack poseStack, VertexConsumer vertexConsumer, int color, int packedLight, int packedOverlay) {
        Matrix4f matrix = poseStack.last().pose();
        IClientFluidTypeExtensions attributes = IClientFluidTypeExtensions.of(fluidStack.getFluid());
        TextureAtlasSprite fluid = Minecraft.getInstance().getModelManager().getAtlas(InventoryMenu.BLOCK_ATLAS).getSprite(attributes.getStillTexture(fluidStack));
        float size = 0.18F;
        float maxV = (fluid.getV1() - fluid.getV0()) * size;
        float minV = (fluid.getV1() - fluid.getV0()) * (1 - size);
        var r = FastColor.ARGB32.red(color);
        var g = FastColor.ARGB32.green(color);
        var b = FastColor.ARGB32.blue(color);
        var a = 255;
        vertexConsumer.vertex(matrix, size, 0, 1 - size).color(r, g, b, a).uv(fluid.getU0(), fluid.getV0() + maxV).uv2(packedLight).overlayCoords(packedOverlay).normal(1, 1, 1).endVertex();
        vertexConsumer.vertex(matrix, 1 - size, 0, 1 - size).color(r, g, b, a).uv(fluid.getU1(), fluid.getV0() + maxV).uv2(packedLight).overlayCoords(packedOverlay).normal(1, 1, 1).endVertex();
        vertexConsumer.vertex(matrix, 1 - size, 0, size).color(r, g, b, a).uv(fluid.getU1(), fluid.getV0() + minV).uv2(packedLight).overlayCoords(packedOverlay).normal(1, 1, 1).endVertex();
        vertexConsumer.vertex(matrix, size, 0, size).color(r, g, b, a).uv(fluid.getU0(), fluid.getV0() + minV).uv2(packedLight).overlayCoords(packedOverlay).normal(1, 1, 1).endVertex();
    }

    private void renderItemStack(EnchantedCrucibleBlockEntity crucible, float height, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        var tick = crucible.getLevel().getGameTime();
        var stacks = crucible.getStacks();
        var rotationAngle = tick % 360.0F;
        var list = crucible.getCookingTime();
        for (int i = 0; i < stacks.size(); i++) {
            ItemStack itemStack = stacks.get(i);
            var key = ForgeRegistries.ITEMS.getKey(itemStack.getItem());
            var alchemyElement = crucible.getLevel().registryAccess().registryOrThrow(AlchemyElement.RESOURCE_KEY).getOptional(key).orElse(null);
            var time = alchemyElement == null ? 300 : alchemyElement.time();
            if (!itemStack.isEmpty() && list[i] < time) {
                pPoseStack.pushPose();
                pPoseStack.translate(0.5F, 0.0F, 0.5F);
                pPoseStack.mulPose(Axis.YP.rotationDegrees(45.0F * (i + 1)));
                pPoseStack.mulPose(Axis.YP.rotationDegrees(rotationAngle));
                pPoseStack.scale(0.175F, 0.175F, 0.175F);
                float floatOffset = (float) Math.sin((tick + i * 5) / 10.0) * 0.1F;
                pPoseStack.translate(0.7F, 2.0F + 0.00225F * height + floatOffset, 0.9F);
                this.itemRenderer.renderStatic(itemStack, ItemDisplayContext.FIXED, pPackedLight, pPackedOverlay, pPoseStack, pBuffer, crucible.getLevel(), i);
                pPoseStack.popPose();
            }
        }
    }
}
