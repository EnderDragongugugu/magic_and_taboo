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
        var nausea = registry.getOrThrow(MATElements.NAUSEA);
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
                .put(hunger, 4.0F)
                .put(poison, 1.5F)
                .register(context, MATItems.GROUND_MEAT.getId(), 300);
        new Builder()
                .put(mercury, 8.0F)
                .register(context, MATItems.MERCURY_SLAG.getId(), 300);
        new Builder()
                .put(glowing, 4.0F)
                .put(night_vision, 0.5F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.GLOW_BERRIES), 300);
        new Builder()
                .put(night_vision, 10.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.GOLDEN_CARROT), 300);
        new Builder()
                .put(mercury, 3.0F)
                .put(poison, 2.0F)
                .register(context, MATItems.MERCURY_ORE.getId(), 600);
        new Builder()
                .put(glowing, 8.0F)
                .put(blindness, 1.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.GLOWSTONE), 600);
        new Builder()
                .put(glowing, 6.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.GLOWSTONE_DUST), 300);
        new Builder()
                .put(poison, 12.0F)
                .put(nausea, 4.0F)
                .put(hunger, 3.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.SPIDER_EYE), 300);
        new Builder()
                .put(poison, 8.0F)
                .put(weakness, 7.0F)
                .put(slowness, 6.0F)
                .put(invisibility, 5.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.FERMENTED_SPIDER_EYE), 300);
        new Builder()
                .put(speed, 6.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.SUGAR), 150);
        new Builder()
                .put(speed, 2.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.SUGAR_CANE), 300);
        new Builder()
                .put(slowness, 3.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.HONEYCOMB), 300);
        new Builder()
                .put(slowness, 5.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.HONEY_BLOCK), 300);
        new Builder()
                .put(strength, 7.0F)
                .put(fire_resistance, 5.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.BLAZE_POWDER), 300);
        new Builder()
                .put(strength, 10.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.BLAZE_ROD), 600);
        new Builder()
                .put(regeneration, 2.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.MELON), 150);
        new Builder()
                .put(regeneration, 3.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.MELON_SLICE), 150);
        new Builder()
                .put(regeneration, 15.0F)
                .put(health_boost, 5.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.GLISTERING_MELON_SLICE), 150);
        new Builder()
                .put(poison, 5.0F)
                .put(nausea, 3.0F)
                .put(mining_fatigue, 2.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.BROWN_MUSHROOM), 150);
        new Builder()
                .put(poison, 3.0F)
                .put(wither, 1.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.BROWN_MUSHROOM_BLOCK), 300);
        new Builder()
                .put(regeneration, 4.0F)
                .put(jump_boost, 3.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.RED_MUSHROOM), 150);
        new Builder()
                .put(regeneration, 2.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.RED_MUSHROOM_BLOCK), 300);
        new Builder()
                .put(resistance, 8.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.SHULKER_SHELL), 450);
        new Builder()
                .put(conduit_power, 12.0F)
                .put(dolphins_grace, 8.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.HEART_OF_THE_SEA), 600);
        new Builder()
                .put(blindness, 6.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.INK_SAC), 300);
        new Builder()
                .put(glowing, 7.0F)
                .put(blindness, 2.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.GLOW_INK_SAC), 300);
        new Builder()
                .put(resistance, 3.0F)
                .put(absorption, 2.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.SCUTE), 300);
        new Builder()
                .put(resistance, 10.0F)
                .put(slowness, 4.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.TURTLE_HELMET), 300);
        new Builder()
                .put(poison, 6.0F)
                .put(hunger, 2.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.POISONOUS_POTATO), 150);
        new Builder()
                .put(regeneration, 3.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.APPLE), 150);
        new Builder()
                .put(regeneration, 8.0F)
                .put(absorption, 5.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.GOLDEN_APPLE), 150);
        new Builder()
                .put(regeneration, 20.0F)
                .put(absorption, 15.0F)
                .put(fire_resistance, 10.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.ENCHANTED_GOLDEN_APPLE), 150);
        new Builder()
                .put(levitation, 4.0F)
                .put(nausea, 3.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.CHORUS_FRUIT), 150);
        new Builder()
                .put(hunger, 8.0F)
                .put(poison, 3.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.ROTTEN_FLESH), 150);
        new Builder()
                .put(regeneration, 18.0F)
                .put(nausea, 5.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.GHAST_TEAR), 50);
        new Builder()
                .put(jump_boost, 8.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.RABBIT_FOOT), 150);
        new Builder()
                .put(slow_falling, 12.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.PHANTOM_MEMBRANE), 450);
        new Builder()
                .put(fire_resistance, 12.0F)
                .put(strength, 3.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.MAGMA_CREAM), 600);
        new Builder()
                .put(night_vision, 3.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.CARROT), 150);
        new Builder()
                .put(wither, 10.0F)
                .put(weakness, 5.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.WITHER_ROSE), 150);
        new Builder()
                .put(water_breathing, 15.0F)
                .put(poison, 8.0F)
                .put(nausea, 7.0F)
                .put(hunger, 6.0F)
                .register(context, ForgeRegistries.ITEMS.getKey(Items.PUFFERFISH), 150);
//        test
        new Builder()
                .put(speed, 1.0F)
                .put(slowness, 1.0F)
                .put(haste, 1.0F)
                .put(mining_fatigue, 1.0F)
                .put(strength, 1.0F)
                .put(jump_boost, 1.0F)
                .put(nausea, 1.0F)
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
