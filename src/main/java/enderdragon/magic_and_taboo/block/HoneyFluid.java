package enderdragon.magic_and_taboo.block;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import enderdragon.magic_and_taboo.init.MATBlocks;
import enderdragon.magic_and_taboo.init.MATItems;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.function.Consumer;

import static enderdragon.magic_and_taboo.init.MATFluids.*;

public abstract class HoneyFluid extends ForgeFlowingFluid {
    public static final Properties PROPERTIES = new Properties(HONEY_FLUID_TYPE, HONEY, FLOWING_HONEY)
            .block(MATBlocks.HONEY)
            .bucket(MATItems.HONEY_BUCKET)
            .explosionResistance(100.0F)
            .slopeFindDistance(2)
            .levelDecreasePerBlock(2)
            .tickRate(30);

    protected HoneyFluid(Properties props) {
        super(props);
    }

    @Nullable
    public ParticleOptions getDripParticle() {
        return ParticleTypes.DRIPPING_HONEY;
    }

    public static class Flowing extends HoneyFluid {
        public Flowing(Properties props) {
            super(props);
        }

        @Override
        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getAmount(FluidState state) {
            return state.getValue(LEVEL);
        }

        @Override
        public boolean isSource(FluidState state) {
            return false;
        }
    }

    public static class Source extends HoneyFluid {
        public Source(Properties props) {
            super(props);
        }

        @Override
        public int getAmount(FluidState state) {
            return 8;
        }

        @Override
        public boolean isSource(FluidState state) {
            return true;
        }

    }

    public static class Type extends FluidType {
        public Type(Properties props) {
            super(props);
        }

        @Override
        public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
            consumer.accept(new IClientFluidTypeExtensions() {

                private static final ResourceLocation TEXTURE = new ResourceLocation("block/honey_block_side");
                private static final ResourceLocation OVERLAY = new ResourceLocation("block/honey_block_bottom");

                @Override
                public ResourceLocation getStillTexture() {
                    return OVERLAY;
                }

                @Override
                public ResourceLocation getFlowingTexture() {
                    return TEXTURE;
                }

                @Override
                public ResourceLocation getRenderOverlayTexture(Minecraft client) {
                    return OVERLAY;
                }

                @Override
                public @NotNull Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector3f color) {
                    if (darkenWorldAmount <= 0.0F) return new Vector3f(0.75F, 0.46875F, 0.0625F);
                    float majorFactor = 1.0F - darkenWorldAmount;
                    float minorFactor = majorFactor + 0.6F * darkenWorldAmount;
                    return new Vector3f(
                            0.75F * (majorFactor + 0.7F * darkenWorldAmount),
                            0.46875F * minorFactor,
                            0.0625F * minorFactor
                    );
                }

                @Override
                public void modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTick, float nearDistance, float farDistance, FogShape shape) {
                    if (camera.getEntity().isSpectator()) {
                        RenderSystem.setShaderFogStart(-8.0F);
                        RenderSystem.setShaderFogEnd(renderDistance * 0.5F);
                    } else {
                        RenderSystem.setShaderFogStart(0.5F);
                        RenderSystem.setShaderFogEnd(2.0F);
                    }
                    RenderSystem.setShaderFogShape(FogShape.SPHERE);
                }
            });
        }
    }
}
