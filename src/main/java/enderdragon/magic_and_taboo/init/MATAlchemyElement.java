package enderdragon.magic_and_taboo.init;

import enderdragon.magic_and_taboo.registry.AlchemyElement;
import enderdragon.magic_and_taboo.registry.Element;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.item.Items;

public class MATAlchemyElement {
    public static void bootstrap(BootstapContext<AlchemyElement> context) {
        var registry = context.lookup(Element.RESOURCE_KEY);
        new AlchemyElement(
                new AlchemyElement.AlchemyElementMap.Builder()
                        .put(registry.getOrThrow(MATElements.MERCURY), 1.0F)
                        .put(registry.getOrThrow(MATElements.CONFUSION), 10.5F)
                        .put(registry.getOrThrow(MATElements.NIGHT_VISION), 0.5F)
                        .build(),
                300
        ).register(context, MATItems.GROUND_MEAT.get());
        new AlchemyElement(
                new AlchemyElement.AlchemyElementMap.Builder()
                        .put(registry.getOrThrow(MATElements.MERCURY), 1.0F)
                        .put(registry.getOrThrow(MATElements.CONFUSION), 10.5F)
                        .put(registry.getOrThrow(MATElements.NIGHT_VISION), 0.5F)
                        .build(),
                300
        ).register(context, Items.ARROW);
//        new AlchemyElement.Builder()
//                .put(registry.getOrThrow(MATElements.MERCURY), 1.0F)
//                .put(registry.getOrThrow(MATElements.CONFUSION), 10.5F)
//                .put(registry.getOrThrow(MATElements.NIGHT_VISION), 0.5F)
//                .register(context, MATItems.GROUND_MEAT.get());
//        new AlchemyElement.Builder()
//                .put(registry.getOrThrow(MATElements.MERCURY), 1.0F)
//                .register(context, Items.ARROW);
    }
}
