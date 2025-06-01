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
    public static void writeRecipe(Player player, ItemStack parchment, NonNullList<ItemStack> stacks, int temperature, Fluid fluid) {
        var root = new CompoundTag();
        var recipe = new CompoundTag();
        ContainerHelper.saveAllItems(recipe, stacks, true);
        recipe.putInt("Temperature", temperature);
        recipe.putString("Fluid", ForgeRegistries.FLUIDS.getKey(fluid).toString());
        root.put("PotionRecipe", recipe);
        var stack = new ItemStack(MATItems.MAGIC_POTION_PARCHMENT.get());
        stack.setTag(root);
        ContainerUtil.addItem(player, stack);
        parchment.shrink(1);
    }

    public MagicPotionParchmentItem(Properties props) {
        super(props);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (player.isLocalPlayer()) {
            var tag = player.getItemInHand(hand).getTag();
            if (tag != null && tag.contains("PotionRecipe")) {
                ClientUtil.openMagicPotionParchmentScreen(tag.getCompound("PotionRecipe"));
            }
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }
}
