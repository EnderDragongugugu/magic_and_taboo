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
    public static final ResourceKey<Element> SPEED = makeKey(Element.RESOURCE_KEY, "speed");
    public static final ResourceKey<Element> SLOWNESS = makeKey(Element.RESOURCE_KEY, "slowness");
    public static final ResourceKey<Element> HASTE = makeKey(Element.RESOURCE_KEY, "haste");
    public static final ResourceKey<Element> MINING_FATIGUE = makeKey(Element.RESOURCE_KEY, "mining_fatigue");
    public static final ResourceKey<Element> STRENGTH = makeKey(Element.RESOURCE_KEY, "strength");
    public static final ResourceKey<Element> JUMP_BOOST = makeKey(Element.RESOURCE_KEY, "jump_boost");
    public static final ResourceKey<Element> REGENERATION = makeKey(Element.RESOURCE_KEY, "regeneration");
    public static final ResourceKey<Element> RESISTANCE = makeKey(Element.RESOURCE_KEY, "resistance");
    public static final ResourceKey<Element> FIRE_RESISTANCE = makeKey(Element.RESOURCE_KEY, "fire_resistance");
    public static final ResourceKey<Element> WATER_BREATHING = makeKey(Element.RESOURCE_KEY, "water_breathing");
    public static final ResourceKey<Element> INVISIBILITY = makeKey(Element.RESOURCE_KEY, "invisibility");
    public static final ResourceKey<Element> BLINDNESS = makeKey(Element.RESOURCE_KEY, "blindness");
    public static final ResourceKey<Element> HUNGER = makeKey(Element.RESOURCE_KEY, "hunger");
    public static final ResourceKey<Element> WEAKNESS = makeKey(Element.RESOURCE_KEY, "weakness");
    public static final ResourceKey<Element> WITHER = makeKey(Element.RESOURCE_KEY, "wither");
    public static final ResourceKey<Element> HEALTH_BOOST = makeKey(Element.RESOURCE_KEY, "health_boost");
    public static final ResourceKey<Element> ABSORPTION = makeKey(Element.RESOURCE_KEY, "absorption");
    public static final ResourceKey<Element> LEVITATION = makeKey(Element.RESOURCE_KEY, "levitation");
    public static final ResourceKey<Element> LUCK = makeKey(Element.RESOURCE_KEY, "luck");
    public static final ResourceKey<Element> UNLUCK = makeKey(Element.RESOURCE_KEY, "unluck");
    public static final ResourceKey<Element> SLOW_FALLING = makeKey(Element.RESOURCE_KEY, "slow_falling");
    public static final ResourceKey<Element> CONDUIT_POWER = makeKey(Element.RESOURCE_KEY, "conduit_power");
    public static final ResourceKey<Element> DOLPHINS_GRACE = makeKey(Element.RESOURCE_KEY, "dolphins_grace");
    public static final ResourceKey<Element> BAD_OMEN = makeKey(Element.RESOURCE_KEY, "bad_omen");
    public static final ResourceKey<Element> HERO_OF_THE_VILLAGE = makeKey(Element.RESOURCE_KEY, "hero_of_the_village");
    public static final ResourceKey<Element> DARKNESS = makeKey(Element.RESOURCE_KEY, "darkness");

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
        var speed = registry.getOrThrow(SPEED);
        var slowness = registry.getOrThrow(SLOWNESS);
        var haste = registry.getOrThrow(HASTE);
        var mining_fatigue = registry.getOrThrow(MINING_FATIGUE);
        var strength = registry.getOrThrow(STRENGTH);
        var jump_boost = registry.getOrThrow(JUMP_BOOST);
        var regeneration = registry.getOrThrow(REGENERATION);
        var resistance = registry.getOrThrow(RESISTANCE);
        var fire_resistance = registry.getOrThrow(FIRE_RESISTANCE);
        var water_breathing = registry.getOrThrow(WATER_BREATHING);
        var invisibility = registry.getOrThrow(INVISIBILITY);
        var blindness = registry.getOrThrow(BLINDNESS);
        var hunger = registry.getOrThrow(HUNGER);
        var weakness = registry.getOrThrow(WEAKNESS);
        var wither = registry.getOrThrow(WITHER);
        var health_boost = registry.getOrThrow(HEALTH_BOOST);
        var absorption = registry.getOrThrow(ABSORPTION);
        var levitation = registry.getOrThrow(LEVITATION);
        var luck = registry.getOrThrow(LUCK);
        var unluck = registry.getOrThrow(UNLUCK);
        var slow_falling = registry.getOrThrow(SLOW_FALLING);
        var conduit_power = registry.getOrThrow(CONDUIT_POWER);
        var dolphins_grace = registry.getOrThrow(DOLPHINS_GRACE);
        var bad_omen = registry.getOrThrow(BAD_OMEN);
        var hero_of_the_village = registry.getOrThrow(HERO_OF_THE_VILLAGE);
        var darkness = registry.getOrThrow(DARKNESS);
        context.register(MERCURY, new Element(
                MATEffects.MERCURY_TOXINS.get(),
                "element.magic_and_taboo.element_mercury",
                makeIcon(MATEffects.MERCURY_TOXINS.getId()),
                new FloatRange(1.0F, 50.0F),
                new FloatRange(0.0F, 75.0F),
                5 * 20 * 60,
                3,
                new FloatMaps.Builder<Holder<Element>>()
                        .put(regeneration, 20.0F)
                        .put(strength, 15.5F)
                        .put(fire_resistance, 5.5F)
                        .put(resistance, 10.5F)
                        .build(),
                new FloatMaps.Builder<Holder<Element>>()
                        .put(nausea, 1.15F)
                        .put(poison, 0.1F)
                        .build()
        ));
        context.register(NAUSEA, new Element(
                MobEffects.CONFUSION,
                "element.magic_and_taboo.element_nausea",
                makeIcon(ForgeRegistries.MOB_EFFECTS.getKey(MobEffects.CONFUSION)),
                new FloatRange(10.0F, 150.0F),
                new FloatRange(0.0F, 50.0F),
                5 * 20 * 60,
                9,
                new FloatMaps.Builder<Holder<Element>>()
                        .put(regeneration, 10.0F)
                        .put(strength, 5.5F)
                        .put(fire_resistance, 5.5F)
                        .put(resistance, 20.5F)
                        .build(),
                new FloatMaps.Builder<Holder<Element>>()
                        .put(mercury, 3.0F)
                        .put(poison, 0.5F)
                        .put(blindness, 0.2F)
                        .put(wither, 0.5F)
                        .put(weakness, 2.0F)
                        .build()
        ));
        context.register(NIGHT_VISION, new Element(
                MobEffects.NIGHT_VISION,
                "element.magic_and_taboo.element_eye",
                makeId("textures/mob_effect/element_eye.png"),
                new FloatRange(60.0F, 120.0F),
                new FloatRange(5.0F, 100.0F),
                MAX_TIME,
                0,
                new FloatMaps.Builder<Holder<Element>>()
                        .put(nausea, 10.0F)
                        .put(blindness, 30.0F)
                        .put(weakness, 10.0F)
                        .put(darkness, 30.0F)
                        .put(slowness, 5.5F)
                        .build(),
                new FloatMaps.Builder<Holder<Element>>()
                        .put(speed, 1.0F)
                        .put(jump_boost, 0.75F)
                        .put(invisibility, 1.75F)
                        .build()
        ));
        context.register(GLOWING, new Element(
                MobEffects.GLOWING,
                "element.magic_and_taboo.glowing",
                makeIcon(ForgeRegistries.MOB_EFFECTS.getKey(MobEffects.GLOWING)),
                new FloatRange(40.0F, 110.0F),
                new FloatRange(50.0F, 100.0F),
                MAX_TIME,
                0,
                new FloatMaps.Builder<Holder<Element>>()
                        .put(invisibility, 110.0F)
                        .build(),
                new FloatMaps.Builder<Holder<Element>>()
                        .put(night_vision, 1.5F)
                        .build()
        ));
        context.register(POISON, new Element(
                MobEffects.POISON,
                "element.magic_and_taboo.poison",
                makeIcon(ForgeRegistries.MOB_EFFECTS.getKey(MobEffects.POISON)),
                new FloatRange(10.0F, 150.0F),
                new FloatRange(0.0F, 60.0F),
                4 * 20 * 60,
                7,
                new FloatMaps.Builder<Holder<Element>>()
                        .put(regeneration, 20.0F)
                        .put(strength, 15.5F)
                        .put(fire_resistance, 5.5F)
                        .put(resistance, 10.5F)
                        .build(),
                new FloatMaps.Builder<Holder<Element>>()
                        .put(nausea, 0.5F)
                        .put(mercury, 0.6F)
                        .put(blindness, 0.5F)
                        .put(darkness, 0.5F)
                        .put(weakness, 1.5F)
                        .put(wither, 2.0F)
                        .put(mining_fatigue, 1.0F)
                        .build()
        ));
        context.register(SPEED, new Element(
                MobEffects.MOVEMENT_SPEED,
                "element.magic_and_taboo.speed",
                makeIcon(ForgeRegistries.MOB_EFFECTS.getKey(MobEffects.MOVEMENT_SPEED)),
                new FloatRange(30.0F, 200.0F),
                new FloatRange(30.5F, 100.0F),
                4 * 60 * 20,
                MAX_LEVEL,
                new FloatMaps.Builder<Holder<Element>>()
                        .put(nausea, 20.0F)
                        .put(mercury, 25.0F)
                        .put(blindness, 25.5F)
                        .put(darkness, 16.5F)
                        .put(weakness, 100.5F)
                        .put(wither, 200.0F)
                        .put(mining_fatigue, 30.0F)
                        .put(slowness, 150.0F)
                        .build(),
                new FloatMaps.Builder<Holder<Element>>()
                        .put(strength, 0.5F)
                        .put(jump_boost, 0.5F)
                        .put(haste, 1.5F)
                        .build()
        ));
        context.register(SLOWNESS, new Element(
                MobEffects.MOVEMENT_SLOWDOWN,
                "element.magic_and_taboo.slowness",
                makeIcon(ForgeRegistries.MOB_EFFECTS.getKey(MobEffects.MOVEMENT_SLOWDOWN)),
                new FloatRange(30.0F, 200.0F),
                new FloatRange(0.0F, 100.0F),
                4 * 60 * 20,
                MAX_LEVEL,
                new FloatMaps.Builder<Holder<Element>>()
                        .put(strength, 200.0F)
                        .put(jump_boost, 100.5F)
                        .put(health_boost, 175.5F)
                        .put(haste, 150.5F)
                        .put(speed, 150.0F)
                        .build(),
                new FloatMaps.Builder<Holder<Element>>()
                        .put(weakness, 0.5F)
                        .put(mining_fatigue, 1.5F)
                        .build()
        ));
        context.register(HASTE, new Element(
                MobEffects.DIG_SPEED,
                "element.magic_and_taboo.haste",
                makeIcon(ForgeRegistries.MOB_EFFECTS.getKey(MobEffects.DIG_SPEED)),
                new FloatRange(30.0F, 200.0F),
                new FloatRange(0.0F, 100.0F),
                MAX_TIME,
                MAX_LEVEL,
                new FloatMaps.Builder<Holder<Element>>()
                        .put(nausea, 20.0F)
                        .put(mercury, 25.0F)
                        .put(blindness, 25.5F)
                        .put(darkness, 16.5F)
                        .put(weakness, 100.5F)
                        .put(wither, 200.0F)
                        .put(mining_fatigue, 30.0F)
                        .put(slowness, 150.0F)
                        .build(),
                new FloatMaps.Builder<Holder<Element>>()
                        .put(strength, 0.5F)
                        .put(jump_boost, 0.5F)
                        .put(speed, 1.5F)
                        .build()
        ));
        context.register(MINING_FATIGUE, new Element(
                MobEffects.DIG_SLOWDOWN,
                "element.magic_and_taboo.mining_fatigue",
                makeIcon(ForgeRegistries.MOB_EFFECTS.getKey(MobEffects.DIG_SLOWDOWN)),
                new FloatRange(30.0F, 200.0F),
                new FloatRange(0.0F, 100.0F),
                MAX_TIME,
                MAX_LEVEL,
                new FloatMaps.Builder<Holder<Element>>()
                        .put(strength, 200.0F)
                        .put(jump_boost, 100.5F)
                        .put(health_boost, 175.5F)
                        .put(haste, 150.5F)
                        .put(speed, 150.0F)
                        .build(),
                new FloatMaps.Builder<Holder<Element>>()
                        .put(weakness, 0.5F)
                        .put(slowness, 1.5F)
                        .build()
        ));
        context.register(STRENGTH, new Element(
                MobEffects.DAMAGE_BOOST,
                "element.magic_and_taboo.strength",
                makeIcon(ForgeRegistries.MOB_EFFECTS.getKey(MobEffects.DAMAGE_BOOST)),
                new FloatRange(30.0F, 130.0F),
                new FloatRange(20.0F, 100.0F),
                MAX_TIME,
                MAX_LEVEL,
                new FloatMaps.Builder<Holder<Element>>()
                        .put(blindness, 20.0F)
                        .put(darkness, 25.5F)
                        .put(weakness, 50.5F)
                        .put(mining_fatigue, 40.5F)
                        .build(),
                new FloatMaps.Builder<Holder<Element>>()
                        .put(health_boost, 1.0F)
                        .put(regeneration, 1.5F)
                        .put(haste, 0.5F)
                        .build()
        ));
        context.register(JUMP_BOOST, new Element(
                MobEffects.JUMP,
                "element.magic_and_taboo.jump_boost",
                makeIcon(ForgeRegistries.MOB_EFFECTS.getKey(MobEffects.JUMP)),
                new FloatRange(50.0F, 110.0F),
                new FloatRange(0.0F, 100.0F),
                MAX_TIME,
                MAX_LEVEL,
                new FloatMaps.Builder<Holder<Element>>()
                        .put(health_boost, 1.0F)
                        .put(regeneration, 1.5F)
                        .put(speed, 0.5F)
                        .build(),
                new FloatMaps.Builder<Holder<Element>>()
                        .put(slow_falling, 50.0F)
                        .put(slowness, 25.5F)
                        .build()
        ));
        context.register(REGENERATION, new Element(
                MobEffects.REGENERATION,
                "element.magic_and_taboo.regeneration",
                makeIcon(ForgeRegistries.MOB_EFFECTS.getKey(MobEffects.REGENERATION)),
                new FloatRange(30.0F, 100.0F),
                new FloatRange(0.0F, 100.0F),
                MAX_TIME,
                MAX_LEVEL,
                FloatMaps.unmodifiableCopy(null),
                FloatMaps.unmodifiableCopy(null)
        ));
        context.register(RESISTANCE, new Element(
                MobEffects.DAMAGE_RESISTANCE,
                "element.magic_and_taboo.resistance",
                makeIcon(ForgeRegistries.MOB_EFFECTS.getKey(MobEffects.DAMAGE_RESISTANCE)),
                new FloatRange(30.0F, 100.0F),
                new FloatRange(0.0F, 100.0F),
                MAX_TIME,
                MAX_LEVEL,
                FloatMaps.unmodifiableCopy(null),
                FloatMaps.unmodifiableCopy(null)
        ));
        context.register(FIRE_RESISTANCE, new Element(
                MobEffects.FIRE_RESISTANCE,
                "element.magic_and_taboo.fire_resistance",
                makeIcon(ForgeRegistries.MOB_EFFECTS.getKey(MobEffects.FIRE_RESISTANCE)),
                new FloatRange(30.0F, 100.0F),
                new FloatRange(0.0F, 100.0F),
                MAX_TIME,
                MAX_LEVEL,
                FloatMaps.unmodifiableCopy(null),
                FloatMaps.unmodifiableCopy(null)
        ));
        context.register(WATER_BREATHING, new Element(
                MobEffects.WATER_BREATHING,
                "element.magic_and_taboo.water_breathing",
                makeIcon(ForgeRegistries.MOB_EFFECTS.getKey(MobEffects.WATER_BREATHING)),
                new FloatRange(30.0F, 100.0F),
                new FloatRange(0.0F, 100.0F),
                MAX_TIME,
                MAX_LEVEL,
                FloatMaps.unmodifiableCopy(null),
                FloatMaps.unmodifiableCopy(null)
        ));
        context.register(INVISIBILITY, new Element(
                MobEffects.INVISIBILITY,
                "element.magic_and_taboo.invisibility",
                makeIcon(ForgeRegistries.MOB_EFFECTS.getKey(MobEffects.INVISIBILITY)),
                new FloatRange(30.0F, 100.0F),
                new FloatRange(0.0F, 100.0F),
                MAX_TIME,
                MAX_LEVEL,
                FloatMaps.unmodifiableCopy(null),
                FloatMaps.unmodifiableCopy(null)
        ));
        context.register(BLINDNESS, new Element(
                MobEffects.BLINDNESS,
                "element.magic_and_taboo.blindness",
                makeIcon(ForgeRegistries.MOB_EFFECTS.getKey(MobEffects.BLINDNESS)),
                new FloatRange(30.0F, 100.0F),
                new FloatRange(0.0F, 100.0F),
                MAX_TIME,
                MAX_LEVEL,
                FloatMaps.unmodifiableCopy(null),
                FloatMaps.unmodifiableCopy(null)
        ));
        context.register(HUNGER, new Element(
                MobEffects.HUNGER,
                "element.magic_and_taboo.hunger",
                makeIcon(ForgeRegistries.MOB_EFFECTS.getKey(MobEffects.HUNGER)),
                new FloatRange(30.0F, 100.0F),
                new FloatRange(0.0F, 100.0F),
                MAX_TIME,
                MAX_LEVEL,
                FloatMaps.unmodifiableCopy(null),
                FloatMaps.unmodifiableCopy(null)
        ));
        context.register(WEAKNESS, new Element(
                MobEffects.WEAKNESS,
                "element.magic_and_taboo.weakness",
                makeIcon(ForgeRegistries.MOB_EFFECTS.getKey(MobEffects.WEAKNESS)),
                new FloatRange(30.0F, 100.0F),
                new FloatRange(0.0F, 100.0F),
                MAX_TIME,
                MAX_LEVEL,
                FloatMaps.unmodifiableCopy(null),
                FloatMaps.unmodifiableCopy(null)
        ));
        context.register(WITHER, new Element(
                MobEffects.WITHER,
                "element.magic_and_taboo.wither",
                makeIcon(ForgeRegistries.MOB_EFFECTS.getKey(MobEffects.WITHER)),
                new FloatRange(30.0F, 100.0F),
                new FloatRange(0.0F, 100.0F),
                MAX_TIME,
                MAX_LEVEL,
                FloatMaps.unmodifiableCopy(null),
                FloatMaps.unmodifiableCopy(null)
        ));
        context.register(HEALTH_BOOST, new Element(
                MobEffects.HEALTH_BOOST,
                "element.magic_and_taboo.health_boost",
                makeIcon(ForgeRegistries.MOB_EFFECTS.getKey(MobEffects.HEALTH_BOOST)),
                new FloatRange(30.0F, 100.0F),
                new FloatRange(0.0F, 100.0F),
                MAX_TIME,
                MAX_LEVEL,
                FloatMaps.unmodifiableCopy(null),
                FloatMaps.unmodifiableCopy(null)
        ));
        context.register(ABSORPTION, new Element(
                MobEffects.ABSORPTION,
                "element.magic_and_taboo.absorption",
                makeIcon(ForgeRegistries.MOB_EFFECTS.getKey(MobEffects.ABSORPTION)),
                new FloatRange(30.0F, 100.0F),
                new FloatRange(0.0F, 100.0F),
                MAX_TIME,
                MAX_LEVEL,
                FloatMaps.unmodifiableCopy(null),
                FloatMaps.unmodifiableCopy(null)
        ));
        context.register(LEVITATION, new Element(
                MobEffects.LEVITATION,
                "element.magic_and_taboo.levitation",
                makeIcon(ForgeRegistries.MOB_EFFECTS.getKey(MobEffects.LEVITATION)),
                new FloatRange(30.0F, 100.0F),
                new FloatRange(0.0F, 100.0F),
                MAX_TIME,
                MAX_LEVEL,
                FloatMaps.unmodifiableCopy(null),
                FloatMaps.unmodifiableCopy(null)
        ));
        context.register(LUCK, new Element(
                MobEffects.LUCK,
                "element.magic_and_taboo.luck",
                makeIcon(ForgeRegistries.MOB_EFFECTS.getKey(MobEffects.LUCK)),
                new FloatRange(30.0F, 100.0F),
                new FloatRange(0.0F, 100.0F),
                MAX_TIME,
                MAX_LEVEL,
                FloatMaps.unmodifiableCopy(null),
                FloatMaps.unmodifiableCopy(null)
        ));
        context.register(UNLUCK, new Element(
                MobEffects.UNLUCK,
                "element.magic_and_taboo.unluck",
                makeIcon(ForgeRegistries.MOB_EFFECTS.getKey(MobEffects.UNLUCK)),
                new FloatRange(30.0F, 100.0F),
                new FloatRange(0.0F, 100.0F),
                MAX_TIME,
                MAX_LEVEL,
                FloatMaps.unmodifiableCopy(null),
                FloatMaps.unmodifiableCopy(null)
        ));
        context.register(SLOW_FALLING, new Element(
                MobEffects.SLOW_FALLING,
                "element.magic_and_taboo.slow_falling",
                makeIcon(ForgeRegistries.MOB_EFFECTS.getKey(MobEffects.SLOW_FALLING)),
                new FloatRange(30.0F, 100.0F),
                new FloatRange(0.0F, 100.0F),
                MAX_TIME,
                MAX_LEVEL,
                FloatMaps.unmodifiableCopy(null),
                FloatMaps.unmodifiableCopy(null)
        ));
        context.register(CONDUIT_POWER, new Element(
                MobEffects.CONDUIT_POWER,
                "element.magic_and_taboo.conduit_power",
                makeIcon(ForgeRegistries.MOB_EFFECTS.getKey(MobEffects.CONDUIT_POWER)),
                new FloatRange(30.0F, 100.0F),
                new FloatRange(0.0F, 100.0F),
                MAX_TIME,
                MAX_LEVEL,
                FloatMaps.unmodifiableCopy(null),
                FloatMaps.unmodifiableCopy(null)
        ));
        context.register(DOLPHINS_GRACE, new Element(
                MobEffects.DOLPHINS_GRACE,
                "element.magic_and_taboo.dolphins_grace",
                makeIcon(ForgeRegistries.MOB_EFFECTS.getKey(MobEffects.DOLPHINS_GRACE)),
                new FloatRange(30.0F, 100.0F),
                new FloatRange(0.0F, 100.0F),
                MAX_TIME,
                MAX_LEVEL,
                FloatMaps.unmodifiableCopy(null),
                FloatMaps.unmodifiableCopy(null)
        ));
        context.register(BAD_OMEN, new Element(
                MobEffects.BAD_OMEN,
                "element.magic_and_taboo.bad_omen",
                makeIcon(ForgeRegistries.MOB_EFFECTS.getKey(MobEffects.BAD_OMEN)),
                new FloatRange(30.0F, 100.0F),
                new FloatRange(0.0F, 100.0F),
                MAX_TIME,
                MAX_LEVEL,
                FloatMaps.unmodifiableCopy(null),
                FloatMaps.unmodifiableCopy(null)
        ));
        context.register(HERO_OF_THE_VILLAGE, new Element(
                MobEffects.HERO_OF_THE_VILLAGE,
                "element.magic_and_taboo.hero_of_the_village",
                makeIcon(ForgeRegistries.MOB_EFFECTS.getKey(MobEffects.HERO_OF_THE_VILLAGE)),
                new FloatRange(30.0F, 100.0F),
                new FloatRange(0.0F, 100.0F),
                MAX_TIME,
                MAX_LEVEL,
                FloatMaps.unmodifiableCopy(null),
                FloatMaps.unmodifiableCopy(null)
        ));
        context.register(DARKNESS, new Element(
                MobEffects.DARKNESS,
                "element.magic_and_taboo.darkness",
                makeIcon(ForgeRegistries.MOB_EFFECTS.getKey(MobEffects.DARKNESS)),
                new FloatRange(30.0F, 100.0F),
                new FloatRange(0.0F, 100.0F),
                MAX_TIME,
                MAX_LEVEL,
                FloatMaps.unmodifiableCopy(null),
                FloatMaps.unmodifiableCopy(null)
        ));
    }
}
