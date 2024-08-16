package enderdragon.magicandtaboo.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

import static enderdragon.magicandtaboo.MagicAndTabooMod.makeId;

public class MATEntityTags {
    public static final TagKey<EntityType<?>> ARBOREAL = TagKey.create(Registries.ENTITY_TYPE, makeId("arboreal"));
}
