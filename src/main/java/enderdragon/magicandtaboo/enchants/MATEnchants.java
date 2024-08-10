package enderdragon.magicandtaboo.enchants;

import enderdragon.magicandtaboo.MagicAndTabooMod;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class MATEnchants {
    public static final DeferredRegister<Enchantment> ENCHATMENT = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, MagicAndTabooMod.MODID);
    public static RegistryObject<Enchantment> mercuryToxins = ENCHATMENT.register("mercury_toxins",()->new MercuryToxinsEnchant(Enchantment.Rarity.UNCOMMON, EnchantmentCategory.WEAPON, EquipmentSlot.values()));
}
