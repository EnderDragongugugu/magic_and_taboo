package enderdragon.magic_and_taboo.init;

import enderdragon.magic_and_taboo.registry.AlchemyElement;
import enderdragon.magic_and_taboo.registry.Element;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatMaps;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;

public class MATAlchemyElement {
    public static void bootstrap(BootstapContext<AlchemyElement> context) {
        var registry = context.lookup(Element.RESOURCE_KEY);
        var mercury = registry.getOrThrow(MATElements.MERCURY);
        var confusion = registry.getOrThrow(MATElements.NAUSEA);
        var night_vision = registry.getOrThrow(MATElements.NIGHT_VISION);
        var glowing = registry.getOrThrow(MATElements.GLOWING);
        var poison = registry.getOrThrow(MATElements.POISON);
        new Builder()
                .put(confusion, 8.5F)
                .register(context, MATItems.GROUND_MEAT.getId(), 300);
        new Builder()
                .put(mercury, 10.0F)
                .register(context, MATItems.MERCURY_SLAG.getId(), 300);
        new Builder()
                .put(glowing, 5.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.GLOW_BERRIES), 300);
        new Builder()
                .put(night_vision, 4.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.GOLDEN_CARROT), 300);
        new Builder()
                .put(mercury, 5.0F)
                .put(poison, 1.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(MATItems.MERCURY_ORE.get()), 600);
        new Builder()
                .put(glowing, 10.0F)
                .put(poison, 4.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.GLOWSTONE), 600);
        new Builder()
                .put(glowing, 12.0F)
                .put(poison, 1.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.GLOWSTONE_DUST), 300);
    }

    public static class Builder {
        public final Object2FloatMap<Holder<Element>> elements = new Object2FloatOpenHashMap<>();

        public Builder put(Holder<Element> element, float count) {
            this.elements.put(element, count);
            return this;
        }

        public AlchemyElement build(int time) {
            return new AlchemyElement(Object2FloatMaps.unmodifiable(this.elements), time);
        }

        public void register(BootstapContext<AlchemyElement> context, ResourceLocation identifier, int time) {
            context.register(ResourceKey.create(AlchemyElement.RESOURCE_KEY, identifier), this.build(time));
        }
    }
}
