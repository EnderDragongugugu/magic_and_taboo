package enderdragon.magic_and_taboo.init;

import enderdragon.magic_and_taboo.registry.AlchemyElement;
import enderdragon.magic_and_taboo.registry.Element;
import enderdragon.magic_and_taboo.util.ElementUtil;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;

public class MATAlchemyElement {
    public static void bootstrap(BootstapContext<AlchemyElement> context) {
        var registry = context.lookup(Element.RESOURCE_KEY);
        var map = ElementUtil.getElements(registry);
        var mercury = map.get("mercury");
        var confusion = map.get("nausea");
        var night_vision = map.get("night_vision");
        context.register(
                ResourceKey.create(AlchemyElement.RESOURCE_KEY, MATItems.GROUND_MEAT.getId()),
                new ElementUtil.Builder()
                        .put(confusion, 8.5F)
                        .build(300)
        );
        context.register(
                ResourceKey.create(AlchemyElement.RESOURCE_KEY, MATItems.MERCURY_SLAG.getId()),
                new ElementUtil.Builder()
                        .put(mercury, 10.0F)
                        .build(300)
        );
        //noinspection DataFlowIssue
        context.register(
                ResourceKey.create(AlchemyElement.RESOURCE_KEY, ForgeRegistries.ITEMS.getKey(Items.ARROW)),
                new ElementUtil.Builder()
                        .put(mercury, 1.0F)
                        .build(300)
        );
    }
}
