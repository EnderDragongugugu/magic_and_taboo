package enderdragon.magic_and_taboo.item;

import enderdragon.magic_and_taboo.init.MATItems;
import net.minecraft.Util;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.function.Supplier;

public enum ArmorMaterials implements ArmorMaterial {
    LIMITE("limite", 21, Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 3);
        map.put(ArmorItem.Type.LEGGINGS, 6);
        map.put(ArmorItem.Type.CHESTPLATE, 7);
        map.put(ArmorItem.Type.HELMET, 2);
    }), 15, SoundEvents.ARMOR_EQUIP_GENERIC, 0.5F, () -> Ingredient.of(MATItems.LIMITE_LEGGINGS.get()));

    private final String name;
    private final int durability;
    private final EnumMap<ArmorItem.Type, Integer> protectionFunctionForType;
    private final int enchantability;
    private final SoundEvent equipSound;
    private final float toughness;
    private final Supplier<Ingredient> repairMaterial;
    private static final EnumMap<ArmorItem.Type, Integer> HEALTH_FUNCTION_FOR_TYPE = Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 13);
        map.put(ArmorItem.Type.LEGGINGS, 15);
        map.put(ArmorItem.Type.CHESTPLATE, 16);
        map.put(ArmorItem.Type.HELMET, 11);
    });

    ArmorMaterials(String name, int durability, EnumMap<ArmorItem.Type, Integer> protectionFunctionForType, int enchantability, SoundEvent sound, float toughness, Supplier<Ingredient> repairMaterial) {
        this.name = name;
        this.durability = durability;
        this.protectionFunctionForType = protectionFunctionForType;
        this.enchantability = enchantability;
        this.equipSound = sound;
        this.toughness = toughness;
        this.repairMaterial = repairMaterial;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getDurabilityForType(ArmorItem.Type type) {
        return HEALTH_FUNCTION_FOR_TYPE.get(type) * this.durability;
    }

    public int getDefenseForType(ArmorItem.Type type) {
        return this.protectionFunctionForType.get(type);
    }

    @Override
    public int getEnchantmentValue() {
        return enchantability;
    }

    @Override
    public SoundEvent getEquipSound() {
        return equipSound;
    }

    @Override
    public float getToughness() {
        return toughness;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return repairMaterial.get();
    }

    @Override
    public float getKnockbackResistance() {
        return 0.0F;
    }
}

