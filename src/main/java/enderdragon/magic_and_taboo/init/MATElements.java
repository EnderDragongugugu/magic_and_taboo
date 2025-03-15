package enderdragon.magic_and_taboo.init;

import enderdragon.magic_and_taboo.registry.Element;
import enderdragon.magic_and_taboo.util.FloatMaps;
import enderdragon.magic_and_taboo.util.FloatRange;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import static enderdragon.magic_and_taboo.MagicAndTabooMod.makeId;
import static enderdragon.magic_and_taboo.MagicAndTabooMod.makeKey;

public class MATElements {
    public static final ResourceKey<Element> MERCURY = makeKey(Element.RESOURCE_KEY, "mercury");
    public static final ResourceKey<Element> NAUSEA = makeKey(Element.RESOURCE_KEY, "nausea");
    public static final ResourceKey<Element> NIGHT_VISION = makeKey(Element.RESOURCE_KEY, "night_vision");
    public static final ResourceKey<Element> GLOWING = makeKey(Element.RESOURCE_KEY, "glowing");
    public static final ResourceKey<Element> POISON = makeKey(Element.RESOURCE_KEY, "poison");

    public static final int MAX_TIME = 20 * 60 * 6;
    public static final int MAX_LEVEL = 4;

    static ResourceLocation makeIcon(@Nullable ResourceLocation identifier) {
        assert identifier != null; // to silence the checker
        return identifier.withPath("textures/mob_effect/" + identifier.getPath() + ".png");
    }

    public static void bootstrap(BootstapContext<Element> context) {
        var registry = context.lookup(Element.RESOURCE_KEY);
        var mercury = registry.getOrThrow(MERCURY);
        var nausea = registry.getOrThrow(NAUSEA);
        var night_vision = registry.getOrThrow(NIGHT_VISION);
        var poison = registry.getOrThrow(POISON);
        context.register(MERCURY, new Element(
                MATEffects.MERCURY_TOXINS.get(),
                "element.magic_and_taboo.element_mercury",
                makeIcon(MATEffects.MERCURY_TOXINS.getId()),
                new FloatRange(1.0F, 20.0F),
                new FloatRange(0.0F, 100.0F),
                MAX_TIME,
                MAX_LEVEL,
                FloatMaps.unmodifiableCopy(null),
                new FloatMaps.Builder<Holder<Element>>()
                        .put(nausea, 1.15F)
                        .put(poison, 0.1F)
                        .build()
        ));
        context.register(NAUSEA, new Element(
                MobEffects.CONFUSION,
                "element.magic_and_taboo.element_nausea",
                makeIcon(ForgeRegistries.MOB_EFFECTS.getKey(MobEffects.CONFUSION)),
                new FloatRange(10.0F, 50.0F),
                new FloatRange(0.0F, 100.0F),
                MAX_TIME,
                MAX_LEVEL,
                FloatMaps.unmodifiableCopy(null),
                new FloatMaps.Builder<Holder<Element>>()
                        .put(nausea, 3.0F)
                        .put(poison, 0.5F)
                        .build()
        ));
        context.register(NIGHT_VISION, new Element(
                MobEffects.NIGHT_VISION,
                "element.magic_and_taboo.element_eye",
                makeId("textures/mob_effect/element_eye.png"),
                new FloatRange(30.0F, 100.0F),
                new FloatRange(0.0F, 100.0F),
                MAX_TIME,
                MAX_LEVEL,
                new FloatMaps.Builder<Holder<Element>>()
                        .put(nausea, 10.0F)
                        .build(),
                FloatMaps.unmodifiableCopy(null)
        ));
        context.register(GLOWING, new Element(
                MobEffects.GLOWING,
                "element.magic_and_taboo.glowing",
                makeIcon(ForgeRegistries.MOB_EFFECTS.getKey(MobEffects.GLOWING)),
                new FloatRange(30.0F, 100.0F),
                new FloatRange(0.0F, 100.0F),
                MAX_TIME,
                MAX_LEVEL,
                FloatMaps.unmodifiableCopy(null),
                new FloatMaps.Builder<Holder<Element>>().put(night_vision, 1.5F).build()
        ));
        context.register(POISON, new Element(
                MobEffects.POISON,
                "element.magic_and_taboo.poison",
                makeIcon(ForgeRegistries.MOB_EFFECTS.getKey(MobEffects.POISON)),
                new FloatRange(30.0F, 100.0F),
                new FloatRange(0.0F, 100.0F),
                MAX_TIME,
                MAX_LEVEL,
                FloatMaps.unmodifiableCopy(null),
                new FloatMaps.Builder<Holder<Element>>()
                        .put(nausea, 0.5F)
                        .put(mercury, 0.6F)
                        .build()
        ));
    }
}
