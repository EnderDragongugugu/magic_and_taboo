package enderdragon.magic_and_taboo.init;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.function.Consumer;

public enum MATArmorMaterials implements ArmorMaterial {
    LIMITE("limite", 21, 15, SoundEvents.ARMOR_EQUIP_GENERIC, 0.5F, () -> Ingredient.of(MATItems.LIMITE_LEGGINGS.get()), map -> {
        map.put(ArmorItem.Type.BOOTS, 3);
        map.put(ArmorItem.Type.LEGGINGS, 6);
        map.put(ArmorItem.Type.CHESTPLATE, 7);
        map.put(ArmorItem.Type.HELMET, 2);
    });

    public final String name;
    public final int durability;
    public final int enchantability;
    public final SoundEvent equipSound;
    public final float toughness;
    public final Supplier<Ingredient> repairMaterial;
    private final EnumMap<ArmorItem.Type, Integer> protection;

    MATArmorMaterials(String name, int durability, int enchantability, SoundEvent sound, float toughness, Supplier<Ingredient> repairMaterial, Consumer<EnumMap<ArmorItem.Type, Integer>> protection) {
        this.name = name;
        this.durability = durability;
        this.enchantability = enchantability;
        this.equipSound = sound;
        this.toughness = toughness;
        this.repairMaterial = Suppliers.memoize(repairMaterial);
        var map = new EnumMap<ArmorItem.Type, Integer>(ArmorItem.Type.class);
        protection.accept(map);
        this.protection = map;
    }

    @Override
    public @NotNull String getName() {
        return this.name;
    }

    public int getDurabilityForType(ArmorItem.Type type) {
        return this.durability * switch (type) {
            case HELMET -> 11;
            case CHESTPLATE -> 16;
            case LEGGINGS -> 15;
            case BOOTS -> 13;
        };
    }

    public int getDefenseForType(ArmorItem.Type type) {
        return this.protection.get(type);
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantability;
    }

    @Override
    public @NotNull SoundEvent getEquipSound() {
        return this.equipSound;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public @NotNull Ingredient getRepairIngredient() {
        return this.repairMaterial.get();
    }

    @Override
    public float getKnockbackResistance() {
        return 0.0F; // 666
    }
}

