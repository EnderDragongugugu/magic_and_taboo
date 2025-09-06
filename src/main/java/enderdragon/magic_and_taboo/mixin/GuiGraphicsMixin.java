package enderdragon.magic_and_taboo.mixin;


import enderdragon.magic_and_taboo.tag.MATItemTags;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static enderdragon.magic_and_taboo.client.MATClient.UNFINISHED_MASK;

@Debug(export = true)
@Mixin(GuiGraphics.class)
public abstract class GuiGraphicsMixin {
    @Shadow
    public abstract void blit(ResourceLocation texture, int x, int y, int z, float u, float v, int width, int height, int textureWidth, int textureHeight);

    @Inject(
            method = "renderItemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;isBarVisible()Z")
    )
    public void renderUnfinishedMask(Font font, ItemStack stack, int x, int y, @Nullable String text, CallbackInfo info) {
        if(stack.is(MATItemTags.UNFINISHED)){
            this.blit(UNFINISHED_MASK, x, y, 200, 0, 0, 16, 16, 16, 16);
        }
    }
}
