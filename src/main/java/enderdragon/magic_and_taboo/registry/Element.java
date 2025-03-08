package enderdragon.magic_and_taboo.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import enderdragon.magic_and_taboo.MagicAndTabooMod;
import enderdragon.magic_and_taboo.util.FloatRange;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

public class Element {
    public static final ResourceKey<Registry<Element>> RESOURCE_KEY = ResourceKey.createRegistryKey(MagicAndTabooMod.makeId("element"));
    public static final Codec<Element> CODEC = RecordCodecBuilder.create(elementInstance -> elementInstance.group(
            ResourceLocation.CODEC.fieldOf("effect_id").forGetter(Element::getEffectId),
            Codec.STRING.fieldOf("element_name").forGetter(Element::getName),
            ResourceLocation.CODEC.fieldOf("effect_icon").forGetter(Element::getIcon),
            FloatRange.CODEC.fieldOf("blood_drug_concentration").forGetter(Element::getConcentration),
            FloatRange.CODEC.fieldOf("temperature_range").forGetter(Element::getTemperatureRange)
    ).apply(elementInstance, Element::new));
    public final ResourceLocation id;
    public final String name;
    public final FloatRange concentration;
    private final FloatRange temperatureRange;
    private ResourceLocation icon;

    public Element(ResourceLocation effectId, String name, @Nullable ResourceLocation icon, FloatRange concentration, FloatRange temperatureRange) {
        this.id = effectId;
        this.name = name;
        this.icon = icon;
        this.concentration = concentration;
        this.temperatureRange = temperatureRange;
    }

    public ResourceLocation getEffectId() {
        return id;
    }

    public String getName() {
        return this.name;
    }

    public ResourceLocation getIcon() {
        if (icon == null) {
            icon = id.withPath("textures/mob_effect/" + this.id.getPath() + ".png");
        }
        return icon;
    }

    public FloatRange getConcentration() {
        return concentration;
    }

    public FloatRange getTemperatureRange() {
        return temperatureRange;
    }

    public MobEffect getMobEffect() {
        return ForgeRegistries.MOB_EFFECTS.getValue(id);
    }
}
