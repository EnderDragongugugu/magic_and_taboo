package enderdragon.magic_and_taboo.client.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

import java.util.Set;
import java.util.function.Supplier;

import static enderdragon.magic_and_taboo.MagicAndTabooMod.makeId;

public class MoonApprenticeArmorModel<T extends LivingEntity> extends HumanoidModel<T> {
    public static final ResourceLocation MODEL_ID = makeId("moon_apprentice");
    public static final ModelLayerLocation INNER_MODEL = new ModelLayerLocation(MODEL_ID, "inner");
    public static final ModelLayerLocation OUTER_MODEL = new ModelLayerLocation(MODEL_ID, "outer");
    public static final float EXTRA_BACK_ROT = (float) Math.PI * 17.5F / 360.0F;
    public static final Set<Direction> IGNORE_BOTTOM = Set.of(Direction.DOWN, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST);
    public static final Set<Direction> IGNORE_SOUTH = Set.of(Direction.DOWN, Direction.UP, Direction.NORTH, Direction.EAST, Direction.WEST);

    public static LayerDefinition createBodyLayer(CubeDeformation deformation) {
        var mesh = new MeshDefinition();
        var root = mesh.getRoot();
        root.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);// just to avoid crash
        var head = root.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.ZERO);
        head.addOrReplaceChild("hat", CubeListBuilder.create()
                        .texOffs(24, 32).addBox(-10.0F, -8.0F, -10.0F, 20.0F, 0.0F, 20.0F, Set.of(Direction.DOWN))
                        .texOffs(10, 0).addBox(-5.0F, -10.0F, -5.0F, 10.0F, 2.0F, 10.0F, IGNORE_BOTTOM),
                PartPose.ZERO
        ).addOrReplaceChild("hat_r1", CubeListBuilder.create()
                        .texOffs(16, 32).addBox(-4.0F, -4.0F, -2.0F, 8.0F, 8.0F, 3.0F, IGNORE_SOUTH),
                PartPose.offsetAndRotation(0.0F, -10.0F, 0.0F, (float) Math.toRadians(-100.0), 0.0F, 0.0F)
        ).addOrReplaceChild("hat_r2", CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3.0F, -3.0F, -5.0F, 6.0F, 6.0F, 4.0F, IGNORE_SOUTH),
                PartPose.rotation((float) Math.toRadians(-5.0), 0.0F, 0.0F)
        ).addOrReplaceChild("hat_r3", CubeListBuilder.create()
                        .texOffs(48, 56).addBox(-2.0F, -8.0F, -3.0F, 4.0F, 4.0F, 4.0F, IGNORE_BOTTOM),
                PartPose.rotation((float) Math.toRadians(80.0), 0.0F, 0.0F)
        ).addOrReplaceChild("hat_r4", CubeListBuilder.create()
                        .texOffs(0, 10).addBox(-1.0F, -11F, -2.5F, 2.0F, 4.0F, 2.0F, IGNORE_BOTTOM),
                PartPose.rotation((float) Math.toRadians(-2.5), 0.0F, 0.0F)
        );
        root.addOrReplaceChild("right_arm", CubeListBuilder.create()
                        .texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, deformation)
                        .texOffs(40, 0).addBox(-5.0F, -3.0F, -2.5F, 6.0F, 4.0F, 5.0F, deformation.extend(-0.25F)),
                PartPose.offset(-5.0F, 2.0F, 0.0F)
        );
        root.addOrReplaceChild("left_arm", CubeListBuilder.create()
                        .texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, deformation)
                        .mirror()
                        .texOffs(40, 0).addBox(-1.0F, -3.0F, -2.5F, 6.0F, 4.0F, 5.0F, deformation.extend(-0.25F)),
                PartPose.offset(5.0F, 2.0F, 0.0F)
        );
        var body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, deformation), PartPose.ZERO);
        body.addOrReplaceChild("right_back", CubeListBuilder.create()
                        .texOffs(0, 48)
                        .addBox(-2.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, deformation),
                PartPose.offsetAndRotation(-1.9F, 12.0F, 0.0F, EXTRA_BACK_ROT, 0.0F, 0.0F));
        body.addOrReplaceChild("left_back", CubeListBuilder.create()
                        .texOffs(0, 32)
                        .addBox(-2.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, deformation),
                PartPose.offsetAndRotation(1.9F, 12.0F, 0.0F, EXTRA_BACK_ROT, 0.0F, 0.0F));
        root.addOrReplaceChild("right_leg", CubeListBuilder.create()
                        .texOffs(26, 43).addBox(-2.0F, 10.0F, -3.0F, 4.0F, 2.0F, 1.0F, deformation)
                        .texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, deformation),
                PartPose.offset(-1.9F, 12.0F, 0.0F)
        );
        root.addOrReplaceChild("left_leg", CubeListBuilder.create()
                        .texOffs(16, 43).addBox(-2.0F, 10.0F, -3.0F, 4.0F, 2.0F, 1.0F, deformation)
                        .texOffs(16, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, deformation),
                PartPose.offset(1.9F, 12.0F, 0.0F)
        );
        return LayerDefinition.create(mesh, 64, 64);
    }

    public final ModelPart leftBack;
    public final ModelPart rightBack;

    public MoonApprenticeArmorModel(ModelPart root) {
        super(root);
        this.leftBack = this.body.getChild("left_back");
        this.rightBack = this.body.getChild("right_back");
    }

    public static class Holder implements Supplier<MoonApprenticeArmorModel<?>> {
        public final ModelLayerLocation layer;
        private MoonApprenticeArmorModel<?> model;

        public Holder(ModelLayerLocation layer) {
            this.layer = layer;
        }

        public void invalidate() {
            this.model = null;
        }

        @Override
        public MoonApprenticeArmorModel<?> get() {
            var model = this.model;
            return model == null ? this.model = new MoonApprenticeArmorModel<>(
                    Minecraft.getInstance().getEntityModels().bakeLayer(this.layer)
            ) : model;
        }
    }
}
