package enderdragon.magic_and_taboo.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import enderdragon.magic_and_taboo.MagicAndTabooMod;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class WorkHubToolModel extends Model {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(MagicAndTabooMod.MOD_ID, "work_hub_tool"), "main");
    private final ModelPart blaze_burner;
    private final ModelPart table;
    private final ModelPart mortar;
    private final ModelPart item_stack_list;
    private final ModelPart item_stack_1;
    private final ModelPart item_stack_4;
    private final ModelPart item_stack_2;
    private final ModelPart item_stack_3;
    private final ModelPart item_stack_6;
    private final ModelPart item_stack_5;
    private final ModelPart output_1;
    private final ModelPart output_2;
    private final ModelPart output_3;
    private final ModelPart container;
    private final ModelPart root;

    public WorkHubToolModel(ModelPart root) {
        super(RenderType::entitySolid);
        this.root = root;
        this.blaze_burner = root.getChild("blaze_burner");
        this.table = root.getChild("table");
        this.mortar = root.getChild("mortar");
        this.item_stack_list = root.getChild("item_stack_list");
        this.item_stack_1 = root.getChild("item_stack_1");
        this.item_stack_4 = root.getChild("item_stack_4");
        this.item_stack_2 = root.getChild("item_stack_2");
        this.item_stack_3 = root.getChild("item_stack_3");
        this.item_stack_6 = root.getChild("item_stack_6");
        this.item_stack_5 = root.getChild("item_stack_5");
        this.output_1 = root.getChild("output_1");
        this.output_2 = root.getChild("output_2");
        this.output_3 = root.getChild("output_3");
        this.container = root.getChild("container");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition blaze_burner = partdefinition.addOrReplaceChild("blaze_burner", CubeListBuilder.create(), PartPose.offsetAndRotation(3.0F, 6.0F, -2.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition fire_r1 = blaze_burner.addOrReplaceChild("fire_r1", CubeListBuilder.create().texOffs(0, 0).addBox(3.0F, -21.0F, -3.5F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(2.0F, -21.0F, -2.5F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(30, 18).addBox(1.5F, -20.0F, -4.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 18.0F, 2.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition table = partdefinition.addOrReplaceChild("table", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition tool4_r1 = table.addOrReplaceChild("tool4_r1", CubeListBuilder.create().texOffs(12, 20).addBox(0.0F, -22.0F, -5.0F, 6.0F, 0.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 20).addBox(0.0F, -18.0F, -5.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(12, 27).addBox(5.0F, -29.0F, 0.0F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition mortar = partdefinition.addOrReplaceChild("mortar", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition tool1_r1 = mortar.addOrReplaceChild("tool1_r1", CubeListBuilder.create().texOffs(0, 20).addBox(0.0F, -3.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0104F, -20.0F, 0.3535F, -0.6545F, 0.0F, 0.5236F));

        PartDefinition tool1_r2 = mortar.addOrReplaceChild("tool1_r2", CubeListBuilder.create().texOffs(24, 3).addBox(-8.0104F, -20.0F, -1.6464F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(32, 23).addBox(-7.5104F, -19.0F, -1.1464F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition item_stack_list = partdefinition.addOrReplaceChild("item_stack_list", CubeListBuilder.create(), PartPose.offsetAndRotation(1.0F, 19.0F, 0.0F, 0.0F, -0.48F, 0.0F));

        PartDefinition tool2_r1 = item_stack_list.addOrReplaceChild("tool2_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.3123F, -10.0F, -1.1895F, 9.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition tool2_r2 = item_stack_list.addOrReplaceChild("tool2_r2", CubeListBuilder.create().texOffs(0, 14).addBox(-5.3123F, -1.0F, -2.1895F, 9.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 1.0F, 0.0F, 0.3491F, 0.0F));

        PartDefinition tool2_r3 = item_stack_list.addOrReplaceChild("tool2_r3", CubeListBuilder.create().texOffs(0, 7).addBox(-5.3123F, -2.0F, -2.1895F, 9.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.0F, 0.0F, 0.0436F, 0.0F));

        PartDefinition item_stack_1 = partdefinition.addOrReplaceChild("item_stack_1", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition item_stack_1_r1 = item_stack_1.addOrReplaceChild("item_stack_1_r1", CubeListBuilder.create().texOffs(16, 27).addBox(-3.3123F, -12.0F, -0.1895F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.2555F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.48F, 0.0F));

        PartDefinition item_stack_4 = partdefinition.addOrReplaceChild("item_stack_4", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0.0F, -0.48F, 0.0F));

        PartDefinition tool_4_r1 = item_stack_4.addOrReplaceChild("tool_4_r1", CubeListBuilder.create().texOffs(8, 33).addBox(-1.3123F, -12.0F, 1.8105F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition item_stack_2 = partdefinition.addOrReplaceChild("item_stack_2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0.0F, -0.48F, 0.0F));

        PartDefinition tool_2_r1 = item_stack_2.addOrReplaceChild("tool_2_r1", CubeListBuilder.create().texOffs(16, 31).addBox(-3.3123F, -12.0F, 1.8105F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition item_stack_3 = partdefinition.addOrReplaceChild("item_stack_3", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0.0F, -0.48F, 0.0F));

        PartDefinition tool_3_r1 = item_stack_3.addOrReplaceChild("tool_3_r1", CubeListBuilder.create().texOffs(0, 14).addBox(-1.3123F, -12.0F, -0.1895F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.255F)), PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition item_stack_6 = partdefinition.addOrReplaceChild("item_stack_6", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0.0F, -0.48F, 0.0F));

        PartDefinition item_stack_6_r1 = item_stack_6.addOrReplaceChild("item_stack_6_r1", CubeListBuilder.create().texOffs(0, 7).addBox(0.6877F, -12.0F, -0.1895F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.255F)), PartPose.offsetAndRotation(2.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition item_stack_5 = partdefinition.addOrReplaceChild("item_stack_5", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0.0F, -0.48F, 0.0F));

        PartDefinition tool_5_r1 = item_stack_5.addOrReplaceChild("tool_5_r1", CubeListBuilder.create().texOffs(16, 35).addBox(0.6877F, -12.0F, 1.8105F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(2.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition output_1 = partdefinition.addOrReplaceChild("output_1", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition tool3_r1 = output_1.addOrReplaceChild("tool3_r1", CubeListBuilder.create().texOffs(23, 9).addBox(0.0F, -1.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, -18.0F, 0.0F, 0.0F, -0.3491F, 0.0F));

        PartDefinition tool3_r2 = output_1.addOrReplaceChild("tool3_r2", CubeListBuilder.create().texOffs(27, 9).addBox(-0.8F, -1.25F, -0.1F, 1.0F, 0.5F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.5F, -20.25F, 0.5F, 0.0F, -0.3491F, 0.0F));

        PartDefinition tool3_r3 = output_1.addOrReplaceChild("tool3_r3", CubeListBuilder.create().texOffs(25, 11).addBox(-1.5F, -2.0F, -1.5F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.8362F, -19.0F, 0.7354F, 0.0F, -0.3491F, 0.0F));

        PartDefinition output_2 = partdefinition.addOrReplaceChild("output_2", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition tool3_r4 = output_2.addOrReplaceChild("tool3_r4", CubeListBuilder.create().texOffs(34, 34).addBox(2.0F, -2.0F, -5.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -17.0F, 3.0F, 0.0F, -0.3491F, 0.0F));

        PartDefinition output_3 = partdefinition.addOrReplaceChild("output_3", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition tool3_r5 = output_3.addOrReplaceChild("tool3_r5", CubeListBuilder.create().texOffs(0, 33).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -19.0F, 3.0F, 0.0F, -0.0436F, 0.0F));

        PartDefinition tool3_r6 = output_3.addOrReplaceChild("tool3_r6", CubeListBuilder.create().texOffs(23, 0).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -23.5F, 3.0F, 0.0F, -0.0436F, 0.0F));

        PartDefinition tool3_r7 = output_3.addOrReplaceChild("tool3_r7", CubeListBuilder.create().texOffs(20, 26).addBox(-2.0F, -3.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0019F, -20.0F, 3.0872F, 0.0F, -0.0436F, 0.0F));

        PartDefinition container = partdefinition.addOrReplaceChild("container", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition tool4_r2 = container.addOrReplaceChild("tool4_r2", CubeListBuilder.create().texOffs(23, 3).addBox(2.5F, -24.0F, -2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(34, 9).addBox(2.0F, -26.0F, -3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 27).addBox(1.5F, -25.0F, -3.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void setOtherGroupVisible(String name, boolean bool) {
        switch (name) {
            case "blaze_burner" -> blaze_burner.visible = bool;
            case "mortar" -> mortar.visible = bool;
            case "container" -> container.visible = bool;
        }
    }

    public void setOutputGroupVisible(int count, int maxCount) {
        output_1.visible = false;
        output_2.visible = false;
        output_3.visible = false;
        if (count > 0) {
            float num = (float) count / maxCount;
            output_1.visible = true;
            if (num > 0.3) output_2.visible = true;
            if (num > 0.6) output_3.visible = true;
        }
    }

    public void setItemStackGroupVisible(int num, boolean bool) {
        switch (num) {
            case 2 -> item_stack_1.visible = bool;
            case 3 -> item_stack_2.visible = bool;
            case 4 -> item_stack_3.visible = bool;
            case 5 -> item_stack_4.visible = bool;
            case 6 -> item_stack_5.visible = bool;
            case 7 -> item_stack_6.visible = bool;
        }
    }
}
