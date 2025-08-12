package enderdragon.magic_and_taboo.item;

import enderdragon.magic_and_taboo.client.ClientUtil;
import enderdragon.magic_and_taboo.init.MATCapabilities;
import enderdragon.magic_and_taboo.init.MATItems;
import enderdragon.magic_and_taboo.registry.Element;
import enderdragon.magic_and_taboo.util.CapabilityUtil;
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
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

import java.util.Objects;

import static enderdragon.magic_and_taboo.block.entity.EnchantedCrucibleBlockEntity.MAX_RECIPE_SIZE;
import static enderdragon.magic_and_taboo.util.CapabilityUtil.getCapability;
import static enderdragon.magic_and_taboo.util.RegistryAccessor.getRegistries;

public class MagicPotionParchmentItem extends Item {
    public static void writeRecipe(Player player, ItemStack parchment, CompoundTag info) {
        info.remove("CookingProgress");
        info.put("Items", Objects.requireNonNull(info.get("RecipeItems")));
        info.remove("RecipeItems");
        var root = new CompoundTag();
        root.put("PotionRecipe", info);
        var stack = new ItemStack(MATItems.MAGIC_POTION_PARCHMENT.get());
        stack.setTag(root);
        ContainerUtil.addItem(player, stack);
        parchment.shrink(1);
    }

    public static boolean loadRecipe(Player player, ItemStack parchment, GlassMagicPotionBottleItem bottle) {
        var root = parchment.getTag();
        if (root == null || !root.contains("PotionRecipe")) return false;
        var recipe = root.getCompound("PotionRecipe");
        var container = player.getInventory();
        var stacks = NonNullList.withSize(MAX_RECIPE_SIZE, ItemStack.EMPTY);
        var elements = new Object2FloatOpenHashMap<Element>();

        if (!recipe.contains("Fluid")) return false;
        var fluid = FluidStack.loadFluidStackFromNBT(recipe.getCompound("Fluid"));

        int slot = 0;
        IFluidHandlerItem handler = null;
        for (int n = container.getContainerSize(); slot < n; ++slot) {
            var stack = container.getItem(slot);
            if (stack.isEmpty()) continue;
            handler = getCapability(stack, ForgeCapabilities.FLUID_HANDLER_ITEM);
            if (handler == null) continue;
            fluid.setAmount(Integer.MAX_VALUE);
            if (handler.drain(fluid, IFluidHandler.FluidAction.SIMULATE).getAmount() >= 250) break;
            handler = null;
        }
        if (handler == null) {
            player.displayClientMessage(Component.translatable("text.warn.magic_potion_parchment.fluid"), true);
            return false;
        }

        if (recipe.contains("Elements")) {
            var requirement = recipe.getCompound("Elements");
            for (var entry : getRegistries(player.level()).registryOrThrow(Element.RESOURCE_KEY).entrySet()) {
                float value = requirement.getFloat(entry.getKey().location().toString());
                if (value <= 0.0F) continue;
                elements.put(entry.getValue(), value);
            }
        }

        if (!recipe.contains("Items")) return false;
        ContainerHelper.loadAllItems(recipe, stacks);
        var matches = ContainerUtil.findAllStacks(container, stacks);
        if (matches == null) {
            player.displayClientMessage(Component.translatable("text.warn.magic_potion_parchment.item"), true);
            return false;
        }
        if (!player.getAbilities().instabuild) {
            for (var match : matches) {
                container.removeItem(match.slot(), match.count());
            }
            if (handler.drain(250, IFluidHandler.FluidAction.EXECUTE).isEmpty()) {
                container.removeItem(slot, 1);
            }
        }
        var filled = new ItemStack(bottle.getFilled());
        var potion = CapabilityUtil.getCapability(filled, MATCapabilities.MAGIC_POTION);
        if (potion != null) {
            potion.setContent(fluid.getFluid().getFluidType(), elements);
        }
        ContainerUtil.addItem(player, filled);
        return true;
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
