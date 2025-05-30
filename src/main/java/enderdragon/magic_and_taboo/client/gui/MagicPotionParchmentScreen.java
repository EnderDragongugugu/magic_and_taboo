package enderdragon.magic_and_taboo.client.gui;

import enderdragon.magic_and_taboo.MagicAndTabooMod;
import enderdragon.magic_and_taboo.client.ClientUtil;
import enderdragon.magic_and_taboo.init.MATItems;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

import static enderdragon.magic_and_taboo.block.entity.EnchantedCrucibleBlockEntity.MAX_RECIPE_SIZE;

public class MagicPotionParchmentScreen extends Screen {
    public static final int ITEM_COLUMNS = 8;
    public static final int ICON_SIZE = 16;
    public final NonNullList<ItemStack> stacks = NonNullList.withSize(MAX_RECIPE_SIZE, ItemStack.EMPTY);

    protected final ObjectArrayList<MagicPotionParchmentScreen.ItemStackData> itemStackDataList = new ObjectArrayList<>();
    public static final ResourceLocation PARCHMENT = MagicAndTabooMod.makeId("textures/item/parchment.png");
    public static final int SIZE = 16 * 14;
    public CompoundTag tag;

//    public final int x = (this.width - SIZE) / 2;
//    public final int y = (this.height - SIZE) / 2;

    public MagicPotionParchmentScreen(CompoundTag tag) {
        super(CommonComponents.EMPTY);
        this.tag = tag;
        init();
//        int x = (this.width - SIZE) / 2;
//        int y = (this.height - SIZE) / 2;

    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTicks);
        int x = (this.width - SIZE) / 2;
        int y = (this.height - SIZE) / 2;

        graphics.blit(PARCHMENT, x, y, 0, 0, SIZE, SIZE, SIZE, SIZE);

        renderItemStack(graphics, mouseX, mouseY);

        renderBucket(graphics, mouseX, mouseY, x + 58, y + 28);

        renderTemperature(graphics, mouseX, mouseY, x + 58, y + 60);
    }

    public void init() {
        int x = (this.width - SIZE) / 2;
        int y = (this.height - SIZE) / 2;
        ContainerHelper.loadAllItems(tag, stacks);

        itemStackSerialization(stacks, x + 58, y + 88);

    }

    public void itemStackSerialization(List<ItemStack> stacks, int x, int y) {
        itemStackDataList.clear();
        int left = x, top = y, columns = 0;
        for (var itemStack : stacks) {
            this.itemStackDataList.add(new ItemStackData(itemStack, left, top));
            left += ICON_SIZE + 2;
            if (++columns >= ITEM_COLUMNS) {
                columns = 0;
                top += ICON_SIZE + 2;
                left = x;
            }
        }
    }

    public void renderItemStack(GuiGraphics graphics, int mouseX, int mouseY) {
        ItemStack itemStack = null;
        for (var data : itemStackDataList) {
            if (!data.itemStack.isEmpty()) {
                graphics.renderItem(data.itemStack, data.left, data.top);
                if (itemStack == null && ClientUtil.isMouseOver(mouseX, mouseY, data.left, data.top, 16, 16)) {
                    itemStack = data.itemStack;
                }
            }
        }
        if (itemStack != null) {
            graphics.renderTooltip(font, itemStack, mouseX, mouseY);
        }
    }

    public void renderBucket(GuiGraphics graphics, int mouseX, int mouseY, int x, int y) {
        Component tooltip = null;
        var fluid = ForgeRegistries.FLUIDS.getValue(new ResourceLocation(tag.getString("Fluid")));
        graphics.renderItem(new ItemStack(fluid.getBucket()), x, y);
        if (tooltip == null && ClientUtil.isMouseOver(mouseX, mouseY, x, y, 16, 16)) {
            tooltip = Component.translatable("tooltip.magic_and_taboo.magic_potion.solvent", fluid.getFluidType().getDescription());
        }
        if (tooltip != null) {
            graphics.renderTooltip(font, tooltip, mouseX, mouseY);
        }
    }

    public void renderTemperature(GuiGraphics graphics, int mouseX, int mouseY, int x, int y) {
        Component tooltip = null;
        graphics.renderItem(new ItemStack(MATItems.BLAZE_BURNER.get()), x, y);
        if (tooltip == null && ClientUtil.isMouseOver(mouseX, mouseY, x, y, 16, 16)) {
            tooltip = Component.translatable("text.magic_and_taboo.enchanted_crucible_temperature", tag.getInt("Temperature"));
        }
        if (tooltip != null) {
            graphics.renderTooltip(font, tooltip, mouseX, mouseY);
        }
    }

    public record ItemStackData(ItemStack itemStack, int left, int top) {
    }
}
