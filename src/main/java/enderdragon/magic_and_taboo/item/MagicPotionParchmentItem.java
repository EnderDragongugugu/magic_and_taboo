package enderdragon.magic_and_taboo.item;


import enderdragon.magic_and_taboo.capability.MagicPotion;
import enderdragon.magic_and_taboo.client.ClientUtil;
import enderdragon.magic_and_taboo.init.MATCapabilities;
import enderdragon.magic_and_taboo.init.MATItems;
import enderdragon.magic_and_taboo.registry.Element;
import enderdragon.magic_and_taboo.util.ContainerUtil;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

import static enderdragon.magic_and_taboo.block.entity.EnchantedCrucibleBlockEntity.MAX_RECIPE_SIZE;
import static enderdragon.magic_and_taboo.util.RegistryAccessor.getRegistries;

public class MagicPotionParchmentItem extends Item {
    public static void writeRecipe(Player player, ItemStack parchment, NonNullList<ItemStack> stacks, int temperature, Fluid fluid, Object2FloatOpenHashMap<Element> element) {
        var root = new CompoundTag();
        var recipe = new CompoundTag();
        ContainerHelper.saveAllItems(recipe, stacks, true);
        recipe.putInt("Temperature", temperature);
        recipe.putString("Fluid", ForgeRegistries.FLUIDS.getKey(fluid).toString());
        var stack = new ItemStack(MATItems.MAGIC_POTION_PARCHMENT.get());
        var elements = new CompoundTag();
        var lookup = getRegistries(player.level()).registryOrThrow(Element.RESOURCE_KEY);
        for (var entry : element.object2FloatEntrySet()) {
            elements.putFloat(lookup.getKey(entry.getKey()).toString(), entry.getFloatValue());
        }
        recipe.put("Elements", elements);
        root.put("PotionRecipe", recipe);
        stack.setTag(root);

        ContainerUtil.addItem(player, stack);
        parchment.shrink(1);
    }

    public static void setRecipe(Player player, GlassMagicPotionBottleItem itemStack, ItemStack parchment) {
        var nbt = parchment.getOrCreateTag();
        var bottle = new ItemStack(itemStack.getFilled());
        if (nbt.isEmpty() || !nbt.contains("PotionRecipe")) return;
        var tag = nbt.getCompound("PotionRecipe");
        var container = player.getInventory();
        NonNullList<ItemStack> stacks = NonNullList.withSize(MAX_RECIPE_SIZE, ItemStack.EMPTY);
        Object2FloatOpenHashMap<Element> EMap = new Object2FloatOpenHashMap<>();

        if (!tag.contains("Fluid")) return;
        var fluid = FluidStack.loadFluidStackFromNBT(tag.getCompound("Fluid"));
        var bucket = ContainerUtil.find(container, fluid.getFluid().getBucket());
        if (bucket <= -1) {
            player.displayClientMessage(Component.translatable("text.warn.magic_potion_parchment.fluid"), true);
            return;
        }

        if (tag.contains("Elements")) {
            var elements = tag.getCompound("Elements");
            var lookup = getRegistries(player.level()).registryOrThrow(Element.RESOURCE_KEY);
            for (var entry : lookup.entrySet()) {
                float value = elements.getFloat(entry.getKey().location().toString());
                if (value <= 0.0F) continue;
                EMap.put(entry.getValue(), value);
            }
        }

        if (!tag.contains("Items")) return;
        ContainerHelper.loadAllItems(tag, stacks);
        if (ContainerUtil.findAllStack(container, stacks)) {
            for (var stack : stacks) {
                int index = ContainerUtil.find(container, stack.getItem());
                container.removeItem(index, 1);
            }
            var potion = bottle.getCapability(MATCapabilities.MAGIC_POTION).orElse(MagicPotion.EMPTY);
            potion.setContent(fluid.getFluid().getFluidType(), EMap);
            ContainerUtil.addItem(player, bottle);
        } else {
            player.displayClientMessage(Component.translatable("text.warn.magic_potion_parchment.item"), true);
        }
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
