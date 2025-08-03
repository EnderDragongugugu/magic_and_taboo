package enderdragon.magic_and_taboo.item;

import enderdragon.magic_and_taboo.MagicAndTabooMod;
import enderdragon.magic_and_taboo.client.model.MoonApprenticeChestModel;
import enderdragon.magic_and_taboo.client.model.MoonApprenticeHeadModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ArmorStandArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class LimiteArmorItem extends ArmorItem {
    public LimiteArmorItem(ArmorMaterial pMaterial, Type pType, Properties pProperties) {
        super(pMaterial, pType, pProperties);
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        String name = "moon_apprentice_" + slot.getName() + ".png";
        return MagicAndTabooMod.makeId("textures/model/armor/" + name).toString();
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(ArmorRender.INSTANCE);
    }

    private static final class ArmorRender implements IClientItemExtensions {
        private static final ArmorRender INSTANCE = new ArmorRender();

        private HumanoidModel<?> model;

        @Override
        public HumanoidModel<?> getHumanoidArmorModel(LivingEntity entity, ItemStack itemStack, EquipmentSlot slot, HumanoidModel<?> original) {

            var models = Minecraft.getInstance().getEntityModels();
            var root = models.bakeLayer(MoonApprenticeHeadModel.LAYER_LOCATION);
            switch (slot.getName()) {
                case "head" -> root = models.bakeLayer(MoonApprenticeHeadModel.LAYER_LOCATION);
                case "chest" -> root = models.bakeLayer(MoonApprenticeChestModel.LAYER_LOCATION);
            }
            model = new ArmorStandArmorModel(root);
            return model;
        }

    }
}
