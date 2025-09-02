package enderdragon.magic_and_taboo.mixin;


import com.mojang.blaze3d.vertex.PoseStack;
import enderdragon.magic_and_taboo.MagicAndTabooMod;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiGraphics.class)
public abstract class GuiGraphicsMixin {
    private static final ResourceLocation RESOURCE_LOCATION = MagicAndTabooMod.makeId("textures/gui/unfinished.png");
    @Shadow public abstract void blit(ResourceLocation pAtlasLocation, int pX, int pY, float pUOffset, float pVOffset, int pWidth, int pHeight, int pTextureWidth, int pTextureHeight);

    @Shadow @Final private PoseStack pose;

    @Inject(
            method = "renderItemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V",
            at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;pushPose()V")
    )
    private void renderItemDecorations(Font pFont, ItemStack pStack, int pX, int pY, @Nullable String pText, CallbackInfo ci) {
        this.pose.pushPose();
        this.pose.translate(0.0F, 0.0F, 200.0F);
        this.blit(RESOURCE_LOCATION , pX, pY , 0, 0, 16, 16, 16, 16);
        this.pose.popPose();
    }
}
