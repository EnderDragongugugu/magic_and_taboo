package enderdragon.magicandtaboo.init;

import com.mojang.blaze3d.platform.ScreenManager;
import enderdragon.magicandtaboo.MagicAndTabooMod;
import enderdragon.magicandtaboo.inventory.menu.FederationWorkstationMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.rmi.registry.Registry;

public class MATMenuTypes {
    public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MagicAndTabooMod.MOD_ID);
    public static final RegistryObject<MenuType<FederationWorkstationMenu>> FWMENU = REGISTRY.register(
            "federation_workstation_menu",
            ()-> IForgeMenuType.create(FederationWorkstationMenu::new)
    );
}
