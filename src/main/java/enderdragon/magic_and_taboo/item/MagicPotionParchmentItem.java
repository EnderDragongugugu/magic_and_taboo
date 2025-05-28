package enderdragon.magic_and_taboo.item;


import enderdragon.magic_and_taboo.client.ClientUtil;
import enderdragon.magic_and_taboo.init.MATItems;
import enderdragon.magic_and_taboo.util.ContainerUtil;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;

public class MagicPotionParchmentItem extends Item {
    public MagicPotionParchmentItem(Properties props) {
        super(props);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (player.isLocalPlayer()) {
            var itemStack = player.getItemInHand(hand);
            var tag = itemStack.getOrCreateTag();
            if (tag.contains("potion_recipe")) {
                var nbt = tag.getCompound("potion_recipe");
                ClientUtil.openMagicPotionParchmentScreen(nbt);
            }
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    public static void writeRecipe(Player player, ItemStack parchment, NonNullList<ItemStack> stacks, int temperature, Fluid fluid) {
        var tag = new CompoundTag();
        var nbt = new CompoundTag();
        ContainerHelper.saveAllItems(nbt, stacks, true);
        nbt.putInt("Temperature", temperature);
        nbt.putString("Fluid", ForgeRegistries.FLUIDS.getKey(fluid).toString());
        tag.put("potion_recipe", nbt);
        var itemStack = new ItemStack(MATItems.MAGIC_POTION_PARCHMENT.get());
        itemStack.setTag(tag);
        ContainerUtil.addItem(player, itemStack);
        parchment.shrink(1);
    }
}
