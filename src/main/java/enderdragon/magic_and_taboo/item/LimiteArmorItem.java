package enderdragon.magic_and_taboo.item;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class LimiteArmorItem extends ArmorItem {
    public LimiteArmorItem(ArmorMaterial pMaterial, Type pType, Properties pProperties) {
        super(pMaterial, pType, pProperties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(ArmorRender.INSTANCE);
    }

    private static final class ArmorRender implements IClientItemExtensions {
        private static final ArmorRender INSTANCE = new ArmorRender();


//        @Override
//        public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel<?> model) {
////            if (slot == EquipmentSlot.HEAD) {
//            ModelPart root = Minecraft.getInstance().getEntityModels().bakeLayer(MoonApprenticeHelmetModel.LAYER_LOCATION);
//            return new ArmorModel(root);
////            }
//        }
    }
}
