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

public record Element(
        MobEffect effect,
        String translationKey,
        ResourceLocation icon,
        FloatRange concentration,
        FloatRange temperature
) {
    public static final ResourceKey<Registry<Element>> RESOURCE_KEY = ResourceKey.createRegistryKey(MagicAndTabooMod.makeId("element"));
    public static final Codec<Element> CODEC = RecordCodecBuilder.create(elementInstance -> elementInstance.group(
            ForgeRegistries.MOB_EFFECTS.getCodec().fieldOf("effect").forGetter(Element::effect),
            Codec.STRING.fieldOf("name").forGetter(Element::translationKey),
            ResourceLocation.CODEC.fieldOf("icon").forGetter(Element::icon),
            FloatRange.CODEC.fieldOf("blood_drug_concentration").forGetter(Element::concentration),
            FloatRange.CODEC.fieldOf("temperature_range").forGetter(Element::temperature)
    ).apply(elementInstance, Element::new));
}
