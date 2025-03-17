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

public class MATAlchemyElements {
    public static void bootstrap(BootstapContext<AlchemyElement> context) {
        var registry = context.lookup(Element.RESOURCE_KEY);
        var mercury = registry.getOrThrow(MATElements.MERCURY);
        var confusion = registry.getOrThrow(MATElements.NAUSEA);
        var night_vision = registry.getOrThrow(MATElements.NIGHT_VISION);
        var glowing = registry.getOrThrow(MATElements.GLOWING);
        var poison = registry.getOrThrow(MATElements.POISON);
        var speed = registry.getOrThrow(MATElements.SPEED);
        var slowness = registry.getOrThrow(MATElements.SLOWNESS);
        var haste = registry.getOrThrow(MATElements.HASTE);
        var mining_fatigue = registry.getOrThrow(MATElements.MINING_FATIGUE);
        var strength = registry.getOrThrow(MATElements.STRENGTH);
        var jump_boost = registry.getOrThrow(MATElements.JUMP_BOOST);
        var regeneration = registry.getOrThrow(MATElements.REGENERATION);
        var resistance = registry.getOrThrow(MATElements.RESISTANCE);
        var fire_resistance = registry.getOrThrow(MATElements.FIRE_RESISTANCE);
        var water_breathing = registry.getOrThrow(MATElements.WATER_BREATHING);
        var invisibility = registry.getOrThrow(MATElements.INVISIBILITY);
        var blindness = registry.getOrThrow(MATElements.BLINDNESS);
        var hunger = registry.getOrThrow(MATElements.HUNGER);
        var weakness = registry.getOrThrow(MATElements.WEAKNESS);
        var wither = registry.getOrThrow(MATElements.WITHER);
        var health_boost = registry.getOrThrow(MATElements.HEALTH_BOOST);
        var absorption = registry.getOrThrow(MATElements.ABSORPTION);
        var levitation = registry.getOrThrow(MATElements.LEVITATION);
        var luck = registry.getOrThrow(MATElements.LUCK);
        var unluck = registry.getOrThrow(MATElements.UNLUCK);
        var slow_falling = registry.getOrThrow(MATElements.SLOW_FALLING);
        var conduit_power = registry.getOrThrow(MATElements.CONDUIT_POWER);
        var dolphins_grace = registry.getOrThrow(MATElements.DOLPHINS_GRACE);
        var bad_omen = registry.getOrThrow(MATElements.BAD_OMEN);
        var hero_of_the_village = registry.getOrThrow(MATElements.HERO_OF_THE_VILLAGE);
        var darkness = registry.getOrThrow(MATElements.DARKNESS);
        new Builder()
                .put(confusion, 3.5F)
                .put(hunger, 1.5F)
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
                .register(context, MATItems.MERCURY_ORE.getId(), 600);
        new Builder()
                .put(glowing, 10.0F)
                .put(poison, 4.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.GLOWSTONE), 600);
        new Builder()
                .put(glowing, 12.0F)
                .put(poison, 1.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.GLOWSTONE_DUST), 300);
        new Builder()
                .put(blindness, 5.5F)
                .put(confusion, 0.5F)
                .put(poison, 15.5F)
                .put(hunger, 4.5F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.SPIDER_EYE), 300);
        new Builder()
                .put(blindness, 0.5F)
                .put(poison, 3.5F)
                .put(hunger, 1.5F)
                .put(slowness, 5.5F)
                .put(weakness, 6.5F)
                .put(invisibility, 4.5F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.FERMENTED_SPIDER_EYE), 300);
        new Builder()
                .put(speed, 4.5F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.SUGAR), 150);
        new Builder()
                .put(speed, 1.5F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.SUGAR_CANE), 300);
        new Builder()
                .put(speed, 0.5F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.HONEYCOMB), 300);
        new Builder()
                .put(speed, 2.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.HONEY_BLOCK), 300);
        new Builder()
                .put(strength, 5.0F)
                .put(fire_resistance, 1.5F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.BLAZE_POWDER), 300);
        new Builder()
                .put(strength, 2.0F)
                .put(fire_resistance, 2.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.BLAZE_ROD), 600);
        new Builder()
                .put(regeneration, 1.0F)
                .put(health_boost, 0.1F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.MELON), 150);
        new Builder()
                .put(regeneration, 1.5F)
                .put(health_boost, 0.5F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.MELON_SLICE), 150);
        new Builder()
                .put(regeneration, 4.5F)
                .put(health_boost, 3.0F)
                .put(absorption, 1.5F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.GLISTERING_MELON_SLICE), 150);
        new Builder()
                .put(poison, 1.5F)
                .put(confusion, 2.0F)
                .put(weakness, 1.5F)
                .put(slowness, 2.5F)
                .put(blindness, 1.5F)
                .put(darkness, 0.5F)
                .put(mining_fatigue, 3.5F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.BROWN_MUSHROOM), 150);
        new Builder()
                .put(poison, 1.0F)
                .put(confusion, 2.0F)
                .put(weakness, 1.5F)
                .put(slowness, 1.5F)
                .put(blindness, 1.5F)
                .put(mining_fatigue, 2.5F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.BROWN_MUSHROOM_BLOCK), 300);
        new Builder()
                .put(regeneration, 1.0F)
                .put(night_vision, 1.0F)
                .put(strength, 0.5F)
                .put(speed, 1.0F)
                .put(jump_boost, 1.0F)
                .put(haste, 1.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.RED_MUSHROOM), 150);
        new Builder()
                .put(regeneration, 0.5F)
                .put(night_vision, 0.5F)
                .put(speed, 0.5F)
                .put(jump_boost, 0.5F)
                .put(haste, 0.5F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.RED_MUSHROOM_BLOCK), 300);
        new Builder()
                .put(resistance, 1.0F)
                .put(levitation, 2.5F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.SHULKER_SHELL), 450);
        new Builder()
                .put(resistance, 1.0F)
                .put(dolphins_grace, 10.5F)
                .put(conduit_power, 10.5F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.HEART_OF_THE_SEA), 600);
        new Builder()
                .put(blindness, 2.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.INK_SAC), 300);
        new Builder()
                .put(blindness, 1.0F)
                .put(glowing, 1.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.GLOW_INK_SAC), 300);
        new Builder()
                .put(resistance, 1.0F)
                .put(absorption, 1.5F)
                .put(slowness, 2.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.SCUTE), 300);
        new Builder()
                .put(resistance, 4.5F)
                .put(absorption, 0.5F)
                .put(slowness, 3.5F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.TURTLE_HELMET), 300);
        new Builder()
                .put(confusion, 1.5F)
                .put(poison, 4.5F)
                .put(slowness, 0.5F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.POISONOUS_POTATO), 150);
        new Builder()
                .put(regeneration, 0.5F)
                .put(absorption, 0.2F)
                .put(resistance, 0.1F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.APPLE), 150);
        new Builder()
                .put(regeneration, 1.5F)
                .put(absorption, 0.5F)
                .put(resistance, 0.2F)
                .put(fire_resistance, 0.1F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.GOLDEN_APPLE), 150);
        new Builder()
                .put(regeneration, 2.5F)
                .put(absorption, 1.5F)
                .put(resistance, 0.5F)
                .put(fire_resistance, 2.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.ENCHANTED_GOLDEN_APPLE), 150);
        new Builder()
                .put(levitation, 0.5F)
                .put(confusion, 1.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.CHORUS_FRUIT), 150);
        new Builder()
                .put(poison, 0.5F)
                .put(hunger, 2.5F)
                .put(confusion, 2.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.ROTTEN_FLESH), 150);
        new Builder()
                .put(regeneration, 4.5F)
                .put(confusion, 2.0F)
                .put(health_boost, 3.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.GHAST_TEAR), 50);
        new Builder()
                .put(regeneration, 0.5F)
                .put(jump_boost, 5.0F)
                .put(luck, 7.5F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.RABBIT_FOOT), 150);
        new Builder()
                .put(slow_falling, 3.5F)
                .put(hunger, 1.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.PHANTOM_MEMBRANE), 450);
        new Builder()
                .put(strength, 1.0F)
                .put(fire_resistance, 5.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.MAGMA_CREAM), 600);
        new Builder()
                .put(night_vision, 1.0F)
                .put(regeneration, 0.5F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.CARROT), 150);
        new Builder()
                .put(wither, 2.0F)
                .put(weakness, 1.5F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.WITHER_ROSE), 150);
        new Builder()
                .put(water_breathing, 10.0F)
                .put(weakness, 1.5F)
                .put(poison, 4.5F)
                .put(confusion, 6.5F)
                .put(hunger, 7.5F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.PUFFERFISH), 150);
//        test
        new Builder()
                .put(speed, 1.0F)
                .put(slowness, 1.0F)
                .put(haste, 1.0F)
                .put(mining_fatigue, 1.0F)
                .put(strength, 1.0F)
                .put(jump_boost, 1.0F)
                .put(confusion, 1.0F)
                .put(regeneration, 1.0F)
                .put(resistance, 1.0F)
                .put(fire_resistance, 1.0F)
                .put(water_breathing, 1.0F)
                .put(invisibility, 1.0F)
                .put(blindness, 1.0F)
                .put(night_vision, 1.0F)
                .put(hunger, 1.0F)
                .put(weakness, 1.0F)
                .put(poison, 1.0F)
                .put(health_boost, 1.0F)
                .put(absorption, 1.0F)
                .put(glowing, 1.0F)
                .put(levitation, 1.0F)
                .put(luck, 1.0F)
                .put(unluck, 1.0F)
                .put(slow_falling, 1.0F)
                .put(conduit_power, 1.0F)
                .put(dolphins_grace, 1.0F)
                .put(bad_omen, 1.0F)
                .put(hero_of_the_village, 1.0F)
                .put(darkness, 1.0F)
                .put(mercury, 1.0F)
                .put(wither, 1.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.BARRIER), 600);

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
