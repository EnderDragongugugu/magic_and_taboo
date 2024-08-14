package enderdragon.magicandtaboo.enchantment;

import enderdragon.magicandtaboo.MagicAndTabooMod;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MATEnchantments {
    public static final DeferredRegister<Enchantment> REGISTRY = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, MagicAndTabooMod.MOD_ID);
    public static RegistryObject<Enchantment> MERCURY_TOXINS = REGISTRY.register("mercury_toxins", () -> new MercuryToxinsEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentCategory.WEAPON, EquipmentSlot.values()));
}
