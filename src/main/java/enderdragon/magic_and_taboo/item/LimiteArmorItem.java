package enderdragon.magic_and_taboo.item;

import enderdragon.magic_and_taboo.client.model.MoonApprenticeArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

import static enderdragon.magic_and_taboo.MagicAndTabooMod.MOD_ID;

public class LimiteArmorItem extends ArmorItem {
    public static final String OUTER_TEXTURE = MOD_ID + ":textures/model/armor/moon_apprentice_outer.png";
    public static final String INNER_TEXTURE = MOD_ID + ":textures/model/armor/moon_apprentice_inner.png";

    public LimiteArmorItem(ArmorMaterial material, Type type, Properties props) {
        super(material, type, props);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return slot == EquipmentSlot.LEGS ? INNER_TEXTURE : OUTER_TEXTURE;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(ArmorRender.INSTANCE);
    }

    public static final class ArmorRender implements IClientItemExtensions {
        public static final ArmorRender INSTANCE = new ArmorRender();

        public final MoonApprenticeArmorModel.Holder outer = new MoonApprenticeArmorModel.Holder(MoonApprenticeArmorModel.OUTER_MODEL);
        public final MoonApprenticeArmorModel.Holder inner = new MoonApprenticeArmorModel.Holder(MoonApprenticeArmorModel.INNER_MODEL);

        @Override
        public HumanoidModel<?> getHumanoidArmorModel(LivingEntity entity, ItemStack itemStack, EquipmentSlot slot, HumanoidModel<?> original) {
            var model = (slot == EquipmentSlot.LEGS ? this.inner : this.outer).get();
            model.leftBack.xRot = original.leftLeg.xRot + MoonApprenticeArmorModel.EXTRA_BACK_ROT;
            model.rightBack.xRot = original.rightLeg.xRot + MoonApprenticeArmorModel.EXTRA_BACK_ROT;
            return model;
        }

        private ArmorRender() {}
    }
}
