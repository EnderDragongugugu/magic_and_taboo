package enderdragon.magic_and_taboo.init;

import enderdragon.magic_and_taboo.registry.AlchemyElement;
import enderdragon.magic_and_taboo.registry.Element;
import enderdragon.magic_and_taboo.util.ElementUtil;
import enderdragon.magic_and_taboo.util.FloatRange;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.registries.ForgeRegistries;

import static enderdragon.magic_and_taboo.MagicAndTabooMod.makeId;
import static enderdragon.magic_and_taboo.MagicAndTabooMod.makeKey;

public class MATElements {
    public static ResourceKey<Element> MERCURY = makeKey(Element.RESOURCE_KEY, "mercury");
    public static ResourceKey<Element> CONFUSION = makeKey(Element.RESOURCE_KEY, "nausea");
    public static ResourceKey<Element> NIGHT_VISION = makeKey(Element.RESOURCE_KEY, "night_vision");

    static ResourceLocation makeIcon(ResourceLocation identifier) {
        return identifier.withPath("textures/mob_effect/" + identifier.getPath() + ".png");
    }

    public static void bootstrap(BootstapContext<Element> context) {
        var registry = context.lookup(Element.RESOURCE_KEY);
        var map = ElementUtil.getElements(registry);
        var mercury = map.get("mercury");
        var confusion = map.get("nausea");
        var night_vision = map.get("night_vision");
        context.register(MERCURY, new Element(
                MATEffects.MERCURY_TOXINS.get(),
                "element.magic_and_taboo.element_mercury",
                makeIcon(MATEffects.MERCURY_TOXINS.getId()),
                new FloatRange(1.0F, 20.0F),
                new FloatRange(0.0F, 100.0F),
                AlchemyElement.of(null),
                new ElementUtil.Builder().put(confusion, 1.15F).build()
        ));
        //noinspection DataFlowIssue
        context.register(CONFUSION, new Element(
                MobEffects.CONFUSION,
                "element.magic_and_taboo.element_nausea",
                makeIcon(ForgeRegistries.MOB_EFFECTS.getKey(MobEffects.CONFUSION)),
                new FloatRange(10.0F, 50.0F),
                new FloatRange(0.0F, 100.0F),
                AlchemyElement.of(null),
                new ElementUtil.Builder().put(confusion, 3.0F).build()
        ));
        context.register(NIGHT_VISION, new Element(
                MobEffects.NIGHT_VISION,
                "element.magic_and_taboo.element_eye",
                makeId("textures/mob_effect/element_eye.png"),
                new FloatRange(30.0F, 100.0F),
                new FloatRange(0.0F, 100.0F),
                AlchemyElement.of(null),
                AlchemyElement.of(null)
        ));
    }
}
