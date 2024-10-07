package enderdragon.magicandtaboo.init;

import enderdragon.magicandtaboo.MagicAndTabooMod;
import enderdragon.magicandtaboo.inventory.WorkHubMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MATMenuTypes {
    public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MagicAndTabooMod.MOD_ID);
    public static final RegistryObject<MenuType<WorkHubMenu>> WORK_HUB = REGISTRY.register(
            "work_hub",
            () -> IForgeMenuType.create(WorkHubMenu::formPacket)
    );
}
