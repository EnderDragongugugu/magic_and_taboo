package enderdragon.magic_and_taboo.client.model;// Made with Blockbench 4.12.5
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import enderdragon.magic_and_taboo.MagicAndTabooMod;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

public class MoonApprenticeHelmetModel<T extends Entity> extends EntityModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(MagicAndTabooMod.makeId("moon_apprentice_helmet"), "main");
    private final ModelPart waist;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart hat;
    private final ModelPart rightArm;
    private final ModelPart rightItem;
    private final ModelPart leftArm;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;

    public MoonApprenticeHelmetModel(ModelPart root) {
        this.waist = root.getChild("waist");
        this.body = this.waist.getChild("body");
        this.head = this.body.getChild("head");
        this.hat = this.head.getChild("hat");
        this.rightArm = this.body.getChild("rightArm");
        this.rightItem = this.rightArm.getChild("rightItem");
        this.leftArm = this.body.getChild("leftArm");
        this.rightLeg = this.body.getChild("rightLeg");
        this.leftLeg = this.body.getChild("leftLeg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition waist = partdefinition.addOrReplaceChild("waist", CubeListBuilder.create(), PartPose.offset(0.0F, 12.0F, 0.0F));

        PartDefinition body = waist.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -12.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition hat = head.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(0, 0).addBox(-10.0F, -8.0F, -10.0F, 20.0F, 0.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(0, 20).addBox(-5.0F, -10.0F, -5.0F, 10.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition hat_r1 = hat.addOrReplaceChild("hat_r1", CubeListBuilder.create().texOffs(0, 8).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -19.4278F, 2.7203F, -0.48F, 0.0F, 0.0F));

        PartDefinition hat_r2 = hat.addOrReplaceChild("hat_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -15.669F, 1.3522F, -0.4363F, 0.0F, 0.0F));

        PartDefinition hat_r3 = hat.addOrReplaceChild("hat_r3", CubeListBuilder.create().texOffs(30, 20).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -12.9392F, 0.6946F, -0.2618F, 0.0F, 0.0F));

        PartDefinition hat_r4 = hat.addOrReplaceChild("hat_r4", CubeListBuilder.create().texOffs(0, 32).addBox(-4.0F, -1.5F, -4.0F, 8.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.4943F, 0.1307F, -0.1745F, 0.0F, 0.0F));

        PartDefinition rightArm = body.addOrReplaceChild("rightArm", CubeListBuilder.create(), PartPose.offset(-5.0F, 2.0F, 0.0F));

        PartDefinition rightItem = rightArm.addOrReplaceChild("rightItem", CubeListBuilder.create(), PartPose.offset(-1.0F, 7.0F, 1.0F));

        PartDefinition leftArm = body.addOrReplaceChild("leftArm", CubeListBuilder.create(), PartPose.offset(5.0F, 2.0F, 0.0F));

        PartDefinition rightLeg = body.addOrReplaceChild("rightLeg", CubeListBuilder.create(), PartPose.offset(-1.9F, 12.0F, 0.0F));

        PartDefinition leftLeg = body.addOrReplaceChild("leftLeg", CubeListBuilder.create(), PartPose.offset(1.9F, 12.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        waist.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}