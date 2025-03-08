package enderdragon.magic_and_taboo.init;

import enderdragon.magic_and_taboo.MagicAndTabooMod;
import enderdragon.magic_and_taboo.registry.Element;
import enderdragon.magic_and_taboo.util.CodecRange;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.registries.ForgeRegistries;

public class MATElements {
    public static ResourceKey<Element> MERCURY = MagicAndTabooMod.makeKey(Element.RESOURCE_KEY, "mercury");
    public static ResourceKey<Element> CONFUSION = MagicAndTabooMod.makeKey(Element.RESOURCE_KEY, "nausea");
    public static ResourceKey<Element> NIGHT_VISION = MagicAndTabooMod.makeKey(Element.RESOURCE_KEY, "night_vision");

    public static void bootstrap(BootstapContext<Element> context) {
        context.register(MERCURY, new Element(
                MATEffects.MERCURY_TOXINS.getId(),
                "element.magic_and_taboo.element_mercury",
                null,
                new CodecRange(1.0F, 20.0F),
                new CodecRange(0.0F, 100.0F)
        ));
        context.register(CONFUSION, new Element(
                ForgeRegistries.MOB_EFFECTS.getKey(MobEffects.CONFUSION),
                "element.magic_and_taboo.element_nausea",
                null,
                new CodecRange(10.0F, 50.0F),
                new CodecRange(0.0F, 100.0F)
        ));
        context.register(NIGHT_VISION, new Element(
                ForgeRegistries.MOB_EFFECTS.getKey(MobEffects.NIGHT_VISION),
                "element.magic_and_taboo.element_eye",
                MagicAndTabooMod.makeId("textures/mob_effect/element_eye.png"),
                new CodecRange(30.0F, 100.0F),
                new CodecRange(0.0F, 100.0F)
        ));
    }
}
