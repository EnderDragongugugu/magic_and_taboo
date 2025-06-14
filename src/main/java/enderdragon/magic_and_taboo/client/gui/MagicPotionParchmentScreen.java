package enderdragon.magic_and_taboo.client.gui;

import enderdragon.magic_and_taboo.MagicAndTabooMod;
import enderdragon.magic_and_taboo.client.ClientUtil;
import enderdragon.magic_and_taboo.init.MATItems;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;

import static enderdragon.magic_and_taboo.block.entity.EnchantedCrucibleBlockEntity.MAX_RECIPE_SIZE;

public class MagicPotionParchmentScreen extends Screen {
    public static final ResourceLocation PARCHMENT = MagicAndTabooMod.makeId("textures/gui/book/parchment.png");
    public static final float SIZE = 1.4F;

    public static final int W = (int) (121 * SIZE);

    public static final int H = (int) (169 * SIZE);
    public static final int ITEM_COLUMNS = 8;
    public static final int ICON_SIZE = 16;
    protected final ObjectArrayList<Widget> widgets = new ObjectArrayList<>();
    public final NonNullList<ItemStack> stacks = NonNullList.withSize(MAX_RECIPE_SIZE, ItemStack.EMPTY);
    private final Component temperature;
    public final Fluid fluid;
    private int top;
    private int left;

    public MagicPotionParchmentScreen(CompoundTag tag) {
        super(CommonComponents.EMPTY);
        ContainerHelper.loadAllItems(tag, stacks);
        this.fluid = ForgeRegistries.FLUIDS.getValue(ResourceLocation.tryParse(tag.getString("Fluid")));
        this.temperature = Component.translatable("text.magic_and_taboo.enchanted_crucible_temperature", tag.getInt("Temperature"));
    }

    @Override
    public void init() {
        super.init();
        int x = this.left = (this.width - W) / 2;
        int y = this.top = (this.height - H) / 2;
        var widgets = this.widgets;
        widgets.clear();
        TooltipRenderer renderer = GuiGraphics::renderTooltip;
        int left = x + 58, top = y + 88, columns = 0;
        for (var stack : this.stacks) {
            widgets.add(new Widget(left, top, stack, renderer));
            left += ICON_SIZE + 2;
            if (++columns >= ITEM_COLUMNS) {
                columns = 0;
                top += ICON_SIZE + 2;
                left = x + 58;
            }
        }
        if (this.fluid != null) {
            var tooltip = Component.translatable("tooltip.magic_and_taboo.magic_potion.solvent", this.fluid.getFluidType().getDescription());
            widgets.add(new Widget(
                    x + 58,
                    y + 28,
                    new ItemStack(this.fluid.getBucket()),
                    (graphics, font, stack, mouseX, mouseY) ->
                            graphics.renderTooltip(font, tooltip, mouseX, mouseY)
            ));
        }
        widgets.add(new Widget(
                x + 58,
                y + 60,
                new ItemStack(MATItems.BLAZE_BURNER.get()),
                (graphics, font, stack, mouseX, mouseY) ->
                        graphics.renderTooltip(font, this.temperature, mouseX, mouseY)
        ));
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTicks);

        graphics.blit(PARCHMENT, this.left, this.top, 0, 0, W, H, W, H);

        boolean tooltip = true;
        for (var widget : this.widgets) {
            var stack = widget.stack;
            if (stack.isEmpty()) continue;
            graphics.renderItem(stack, widget.left, widget.top);
            if (tooltip && ClientUtil.isMouseOver(mouseX, mouseY, widget.left, widget.top, 16, 16)) {
                tooltip = false;
                widget.tooltip.render(graphics, this.font, stack, mouseX, mouseY);
            }
        }
    }

    public interface TooltipRenderer {
        void render(GuiGraphics graphics, Font font, ItemStack stack, int mouseX, int mouseY);
    }

    public record Widget(int left, int top, ItemStack stack, TooltipRenderer tooltip) {
    }
}
