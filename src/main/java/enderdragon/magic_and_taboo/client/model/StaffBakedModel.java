package enderdragon.magic_and_taboo.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.extensions.IForgeBakedModel;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class StaffBakedModel implements BakedModel, IForgeBakedModel {

    private final Map<String, BakedModel> bakedParts;

    public StaffBakedModel(Map<String, BakedModel> bakedParts) {
        this.bakedParts = bakedParts != null ? bakedParts : Collections.emptyMap();
    }

    @Override
    public BakedModel applyTransform(ItemDisplayContext transformType, PoseStack poseStack, boolean leftHand) {
        BakedModel swordModel = Minecraft.getInstance().getItemRenderer()
                .getModel(new ItemStack(Items.DIAMOND_SWORD), null, null, 0);
        IForgeBakedModel forgeModel = (IForgeBakedModel) swordModel;
        forgeModel.applyTransform(transformType, poseStack, leftHand);
        return this;
    }


    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource rand, ModelData data, @Nullable RenderType renderType) {
        List<BakedQuad> combined = new ArrayList<>();
        for (BakedModel part : bakedParts.values()) {
            if (part != null) {
                combined.addAll(part.getQuads(
                        (part.isGui3d() ? null : state),
                        (part.isGui3d() ? null : side),
                        rand, data, renderType
                ));
            }
        }
        return combined;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource rand) {
        return getQuads(state, side, rand, ModelData.EMPTY, null);
    }

    @Override
    public boolean useAmbientOcclusion() {
        return true;
    }

    @Override
    public boolean isGui3d() {
        return true;
    }

    @Override
    public boolean usesBlockLight() {
        return false;
    }

    @Override
    public boolean isCustomRenderer() {
        return true;
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        BakedModel handle = bakedParts.get("handle_default");
        if (handle != null) return handle.getParticleIcon(ModelData.EMPTY);
        return null;
    }

    @Override
    public TextureAtlasSprite getParticleIcon(ModelData data) {
        BakedModel handle = bakedParts.get("handle_default");
        if (handle != null) return handle.getParticleIcon(data);
        return null;
    }

    @Override
    public ItemOverrides getOverrides() {
        return ItemOverrides.EMPTY;
    }
}
