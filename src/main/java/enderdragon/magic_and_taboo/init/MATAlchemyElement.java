package enderdragon.magic_and_taboo.init;

import enderdragon.magic_and_taboo.registry.AlchemyElement;
import enderdragon.magic_and_taboo.registry.Element;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;

public class MATAlchemyElement {
    public static void bootstrap(BootstapContext<AlchemyElement> context) {
        var registry = context.lookup(Element.RESOURCE_KEY);
        var mercury = registry.getOrThrow(MATElements.MERCURY);
        var confusion = registry.getOrThrow(MATElements.CONFUSION);
        var night_vision = registry.getOrThrow(MATElements.NIGHT_VISION);
        context.register(
                ResourceKey.create(AlchemyElement.RESOURCE_KEY, MATItems.GROUND_MEAT.getId()),
                new AlchemyElement.Builder()
                        .put(mercury, 1.0F)
                        .put(confusion, 10.5F)
                        .put(night_vision, 0.5F)
                        .build(300)
        );
        //noinspection DataFlowIssue
        context.register(
                ResourceKey.create(AlchemyElement.RESOURCE_KEY, ForgeRegistries.ITEMS.getKey(Items.ARROW)),
                new AlchemyElement.Builder()
                        .put(mercury, 1.0F)
                        .build(300)
        );
    }
}
