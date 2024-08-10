//package enderdragon.magicandtaboo.mixin;
//
//import com.mojang.blaze3d.vertex.PoseStack;
//import com.mojang.math.Axis;
//import enderdragon.magicandtaboo.MagicAndTabooMod;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.renderer.GameRenderer;
//import net.minecraft.util.Mth;
//import org.joml.Vector3f;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Shadow;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
//
//@Mixin(GameRenderer.class)
//public abstract class MercuryToxinsRender {
//    @Inject(
//            at=@At(
//                    "HEAD"
//            ),
//            method = "renderLevel",
//            locals = LocalCapture.CAPTURE_FAILHARD,
//            cancellable = true
//    )
//    private void test(float p_109090_, long p_109091_, PoseStack p_109092_, CallbackInfo ci){
//        PoseStack posestack = new PoseStack();
//        float f = minecraft.options.screenEffectScale().get().floatValue();
//        float f1 = Mth.lerp(10.0F, minecraft.player.oSpinningEffectIntensity, minecraft.player.spinningEffectIntensity) * f * f;
//        if (f1 > 0.0F) {
//            int i = 7 ;
//            float f2 = 5.0F / (f1 * f1 + 5.0F) - f1 * 0.04F;
//            f2 *= f2;
//            Axis axis = Axis.of(new Vector3f(0.0F, Mth.SQRT_OF_TWO / 2.0F, Mth.SQRT_OF_TWO / 2.0F));
//            posestack.mulPose(axis.rotationDegrees((tick + 0.1F) * (float)i));
//            posestack.scale(1.0F / f2, 10.0F, 10.0F);
//            float f3 = -(this.tick + 1.0F) * (float)i;
//            posestack.mulPose(axis.rotationDegrees(f3));
//        }
//    }
//}
