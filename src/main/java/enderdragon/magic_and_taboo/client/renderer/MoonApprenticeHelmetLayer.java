package enderdragon.magic_and_taboo.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import enderdragon.magic_and_taboo.client.model.MoonApprenticeHeadModel;
import enderdragon.magic_and_taboo.item.LimiteArmorItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EquipmentSlot;

import static enderdragon.magic_and_taboo.MagicAndTabooMod.makeId;

public class MoonApprenticeHelmetLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    public static final RenderType TEXTURE = RenderType.entityCutout(makeId("textures/model/moon_apprentice_head.png"));

    private final MoonApprenticeHeadModel<AbstractClientPlayer> armor;

    public MoonApprenticeHelmetLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> pRenderer) {
        super(pRenderer);
        ModelPart part = Minecraft.getInstance()
                .getEntityModels()
                .bakeLayer(MoonApprenticeHeadModel.LAYER_LOCATION);
        this.armor = new MoonApprenticeHeadModel<>(part);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, AbstractClientPlayer player, float pLimbSwing, float pLimbSwingAmount, float pPartialTick, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        var head = player.getItemBySlot(EquipmentSlot.HEAD);
        if (!head.isEmpty() && head.getItem() instanceof LimiteArmorItem) {
            poseStack.pushPose();
            this.getParentModel().body.visible = true;
//            this.getParentModel().head.translateAndRotate(poseStack);
//            VertexConsumer vertex = bufferSource.getBuffer(TEXTURE);
//            armor.renderToBuffer(poseStack, vertex, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            poseStack.popPose();
        }
    }
}
