package enderdragon.magic_and_taboo.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import enderdragon.magic_and_taboo.block.EnchantedCrucibleBlock;
import enderdragon.magic_and_taboo.init.MATBlockEntities;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderLevelStageEvent;

public class EnchantedCrucibleRenderElements {
    public static void render(RenderLevelStageEvent event) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        var level = mc.level;
        PoseStack poseStack = event.getPoseStack();
        Font font = mc.font;
        if (player == null || level == null) return;
        HitResult hitResult = mc.hitResult;
        if (hitResult instanceof BlockHitResult blockHit) {
            BlockPos pos = blockHit.getBlockPos();
            BlockState blockState = level.getBlockState(pos);
            var block = blockState.getBlock();
            if (block instanceof EnchantedCrucibleBlock) {
                var crucible = level.getBlockEntity(pos, MATBlockEntities.ENCHANTED_CRUCIBLE.get()).orElse(null);
                if (crucible == null) return;
                String text = "哈!";
                Camera camera = mc.gameRenderer.getMainCamera();
                Vec3 camPos = camera.getPosition();
                double x = pos.getX() - camPos.x + 0.5F;
                double y = pos.getY() - camPos.y + 1.5F;
                double z = pos.getZ() - camPos.z + 0.5F;
                poseStack.pushPose();
                poseStack.translate(x, y, z);
                poseStack.mulPose(camera.rotation());
                poseStack.scale(-0.025F, -0.025F, 0.025F);
                int textWidth = font.width(text);
                float textX = -textWidth / 2.0f;
                MultiBufferSource.BufferSource buffer = mc.renderBuffers().bufferSource();
////                renderImage(mc);
//                // 设置图片大小
//                float size = 2.0f; // 图片大小 2x2
//                poseStack.scale(size, size, size);
//
//                // 绑定纹理
//                RenderSystem.setShader(GameRenderer::getPositionTexShader);
//                RenderSystem.setShaderTexture(0, MagicAndTabooMod.makeId("textures/items/fir_door.png"));
//
//                // 绘制矩形（即图片）
//                Tesselator tesselator = Tesselator.getInstance();
//                BufferBuilder bufferBuilder = tesselator.getBuilder();
//
////                bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
//                bufferBuilder.vertex(-0.5f, -0.5f, 0).uv(0, 0).endVertex();
//                bufferBuilder.vertex(0.5f, -0.5f, 0).uv(1, 0).endVertex();
//                bufferBuilder.vertex(0.5f, 0.5f, 0).uv(1, 1).endVertex();
//                bufferBuilder.vertex(-0.5f, 0.5f, 0).uv(0, 1).endVertex();
//                tesselator.end();

                font.drawInBatch(text, textX, 0, -1, false, poseStack.last().pose(), buffer, Font.DisplayMode.NORMAL, 0, 15728880);
                poseStack.popPose();
            }
        }
    }

    public void text() {

    }

//    public static void renderImage(MultiBufferSource buffer, PoseStack poseStack) {
////        GuiGraphics guiGraphics = new GuiGraphics(mc, mc.renderBuffers().bufferSource());
////        guiGraphics.renderItem(new ItemStack(MATItems.BLAZE_BLAST_BURNER.get()), 0, 0);
////        guiGraphics.blit(new ResourceLocation(""), -16, -16, 0, 0, 32, 32, 32, 32);
////        graphics.blit(widget.icon, x, y, 0, 0, 16, 16, 16, 16);
//        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.solid());
//        vertexConsumer.vertex()
//    }
}
