package enderdragon.magic_and_taboo.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import enderdragon.magic_and_taboo.MagicAndTabooMod;
import enderdragon.magic_and_taboo.util.FloatMaps;
import enderdragon.magic_and_taboo.util.FloatRange;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.function.Function;

public record Element(
        MobEffect effect,
        String translationKey,
        ResourceLocation icon,
        FloatRange concentration,
        FloatRange temperature,
        int maxTime,
        int maxLevel,
        Object2FloatMap<Holder<Element>> resistanceElementMap,
        Object2FloatMap<Holder<Element>> fusionElementMap
) {
    public static final ResourceKey<Registry<Element>> RESOURCE_KEY = ResourceKey.createRegistryKey(MagicAndTabooMod.makeId("element"));
    public static final Codec<Element> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ForgeRegistries.MOB_EFFECTS.getCodec().fieldOf("effect").forGetter(Element::effect),
            Codec.STRING.fieldOf("name").forGetter(Element::translationKey),
            ResourceLocation.CODEC.fieldOf("icon").forGetter(Element::icon),
            FloatRange.CODEC.fieldOf("blood_drug_concentration").forGetter(Element::concentration),
            FloatRange.CODEC.fieldOf("temperature_range").forGetter(Element::temperature),
            Codec.INT.fieldOf("max_time").forGetter(Element::maxTime),
            Codec.INT.fieldOf("max_level").forGetter(Element::maxLevel),
            Codec.unboundedMap(RegistryFixedCodec.create(Element.RESOURCE_KEY), Codec.FLOAT)
                    .xmap(FloatMaps::unmodifiableCopy, Function.identity())
                    .fieldOf("resistance_element").forGetter(Element::resistanceElementMap),
            Codec.unboundedMap(RegistryFixedCodec.create(Element.RESOURCE_KEY), Codec.FLOAT)
                    .xmap(FloatMaps::unmodifiableCopy, Function.identity())
                    .fieldOf("fusion_element").forGetter(Element::fusionElementMap)
    ).apply(instance, Element::new));

    public static Object2FloatOpenHashMap<Element> fromStacks(RegistryAccess registry, List<ItemStack> stacks, int[] cookingTime, int temperature) {
        if (cookingTime.length < stacks.size()) throw new IndexOutOfBoundsException();
        var elements = new Object2FloatOpenHashMap<Element>();
        var iterator = stacks.listIterator();
        while (iterator.hasNext()) {
            var stack = iterator.next();
            if (stack.isEmpty()) continue;
            var instance = AlchemyElement.fromItem(registry, stack);
            if (instance == null) continue;
            for (var entry : instance.elementMap().object2FloatEntrySet()) {
                var element = entry.getKey().value();
                if (element.temperature().test(temperature) && cookingTime[iterator.previousIndex()] >= instance.time()) {
                    elements.addTo(element, entry.getFloatValue());
                }
            }
        }
        return elements;
    }

    public MobEffectInstance getEffect(float concentration, float timeFactor, int baseLevel) {
        int maxTime = this.maxTime;
        float normalized = this.concentration.normalize(concentration);
        float time;
        if (this.concentration.max() > concentration * 2) {
            time = concentration / this.concentration.max() * maxTime;
        } else {
            time = Mth.clamp(maxTime * (1.0F - 0.6F * normalized), 600, maxTime);
        }
        return new MobEffectInstance(this.effect, (int) (time * timeFactor), Math.max(0, baseLevel + Mth.ceil(normalized * this.maxLevel)));
    }

    public Component getDisplayName() {
        return Component.translatable(this.translationKey);
    }
}
