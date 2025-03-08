package enderdragon.magic_and_taboo.init;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import enderdragon.magic_and_taboo.MagicAndTabooMod;
import enderdragon.magic_and_taboo.block.HoneyFluid;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.function.Consumer;

public class MATFluids {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, MagicAndTabooMod.MOD_ID);
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, MagicAndTabooMod.MOD_ID);
    public static final RegistryObject<FluidType> HONEY_FLUID_TYPE = FLUID_TYPES.register("honey_fluid", () -> new FluidType(FluidType.Properties.create()
            .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
            .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
            .fallDistanceModifier(0.0F)
            .density(3000)
            .viscosity(6000)
            .motionScale(0.01)
    ) {
        @Override
        public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
            consumer.accept(new IClientFluidTypeExtensions() {
                private static final ResourceLocation TEXTURE = new ResourceLocation("block/honey_block_side");
                private static final ResourceLocation OVERLAY = new ResourceLocation("textures/block/honey_block_bottom.png");

                @Override
                public ResourceLocation getStillTexture() {
                    return TEXTURE;
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
    });

    public static final RegistryObject<HoneyFluid.Source> HONEY = FLUIDS.register("honey",
            () -> new HoneyFluid.Source(MATFluids.HONEY_PROPERTIES));

    public static final RegistryObject<HoneyFluid.Flowing> FLOWING_HONEY = FLUIDS.register("flowing_honey",
            () -> new HoneyFluid.Flowing(MATFluids.HONEY_PROPERTIES));

    public static final ForgeFlowingFluid.Properties HONEY_PROPERTIES = new ForgeFlowingFluid
            .Properties(HONEY_FLUID_TYPE, HONEY, FLOWING_HONEY)
            .block(MATBlocks.HONEY)
            .bucket(MATItems.HONEY_BUCKET)
            .explosionResistance(100.0F)
            .slopeFindDistance(2)
            .levelDecreasePerBlock(2)
            .tickRate(30);
}
