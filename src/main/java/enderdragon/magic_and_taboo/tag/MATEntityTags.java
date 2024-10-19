package enderdragon.magic_and_taboo.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

import static enderdragon.magic_and_taboo.MagicAndTabooMod.makeId;

public class MATEntityTags {
    public static final TagKey<EntityType<?>> ARBOREAL = TagKey.create(Registries.ENTITY_TYPE, makeId("arboreal"));
}
