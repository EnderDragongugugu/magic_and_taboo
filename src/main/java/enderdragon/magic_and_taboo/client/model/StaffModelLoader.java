package enderdragon.magic_and_taboo.client.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.geometry.IGeometryBakingContext;
import net.minecraftforge.client.model.geometry.IGeometryLoader;
import net.minecraftforge.client.model.geometry.IUnbakedGeometry;
import org.apache.logging.log4j.LogManager;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class StaffModelLoader implements IGeometryLoader<StaffModelLoader.Geometry> {
    @Override
    public Geometry read(JsonObject jsonObject, JsonDeserializationContext context) {
        Map<String, ResourceLocation> partModels = new HashMap<>();
        JsonObject parts = jsonObject.getAsJsonObject("parts");
        for (String key : parts.keySet()) {
            partModels.put(key, new ResourceLocation(parts.get(key).getAsString()));
        }
        return new Geometry(partModels);
    }

    public static class Geometry implements IUnbakedGeometry<Geometry> {
        private final Map<String, ResourceLocation> partModels;

        public Geometry(Map<String, ResourceLocation> partModels) {
            this.partModels = partModels;
        }

        @Override
        public BakedModel bake(IGeometryBakingContext context, ModelBaker baker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, ItemOverrides overrides, ResourceLocation modelLocation) {
            Map<String, BakedModel> bakedParts = new HashMap<>();
            for (Map.Entry<String, ResourceLocation> entry : partModels.entrySet()) {
                var baked = baker.bake(entry.getValue(), modelState, spriteGetter);
                LogManager.getLogger().info("模型路径: ");
                if (baked != null) {
                    bakedParts.put(entry.getKey(), baked);
                }
            }

            return new StaffBakedModel(bakedParts);
        }
    }
}
