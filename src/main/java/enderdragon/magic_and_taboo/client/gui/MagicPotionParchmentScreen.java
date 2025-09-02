package enderdragon.magic_and_taboo.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import enderdragon.magic_and_taboo.MagicAndTabooMod;
import enderdragon.magic_and_taboo.capability.MagicPotion;
import enderdragon.magic_and_taboo.client.ClientUtil;
import enderdragon.magic_and_taboo.init.MATCapabilities;
import enderdragon.magic_and_taboo.init.MATItems;
import enderdragon.magic_and_taboo.registry.Element;
import enderdragon.magic_and_taboo.util.MagicPotionBottle;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

import static enderdragon.magic_and_taboo.block.entity.EnchantedCrucibleBlockEntity.MAX_RECIPE_SIZE;
import static enderdragon.magic_and_taboo.util.RegistryAccessor.getRegistries;

public class MagicPotionParchmentScreen extends Screen {
    public static final ResourceLocation PARCHMENT = MagicAndTabooMod.makeId("textures/gui/book/parchment.png");
    public static final float SIZE = 1.4F;
    public static final int WIDTH = (int) (121 * SIZE);
    public static final int HEIGHT = (int) (169 * SIZE);
    public static final int ITEM_COLUMNS = 8;
    public static final int ICON_SIZE = 16;
    protected final ObjectArrayList<Widget> widgets = new ObjectArrayList<>();
    public final NonNullList<ItemStack> stacks = NonNullList.withSize(MAX_RECIPE_SIZE, ItemStack.EMPTY);
    private final Object2FloatOpenHashMap<Element> elements = new Object2FloatOpenHashMap<>();

    private final List<Component> temperature;
    public final Fluid fluid;
    private int top;
    private int left;

    public MagicPotionParchmentScreen(CompoundTag tag) {
        super(CommonComponents.EMPTY);
        var tooltips = new ArrayList<Component>();
        tooltips.add(Component.translatable("tooltip.magic_and_taboo.magic_potion.temperature", tag.getInt("Temperature")));
        tooltips.add(Component.translatable("tooltip.magic_and_taboo.magic_potion.temperature.add"));
        ContainerHelper.loadAllItems(tag, stacks);
        this.fluid = FluidStack.loadFluidStackFromNBT(tag.getCompound("Fluid")).getFluid();
        if (tag.contains("Elements")) {
            this.elements.clear();
            var elements = tag.getCompound("Elements");
            var lookup = getRegistries(Minecraft.getInstance().level).registryOrThrow(Element.RESOURCE_KEY);
            for (var entry : lookup.entrySet()) {
                float value = elements.getFloat(entry.getKey().location().toString());
                if (value <= 0.0F) continue;
                this.elements.put(entry.getValue(), value);
            }
        }
        this.temperature = tooltips;
    }

    @Override
    public void init() {
        super.init();
        int x = this.left = (this.width - WIDTH) / 2;
        int y = this.top = (this.height - HEIGHT) / 2;
        var widgets = this.widgets;
        widgets.clear();
        TooltipRenderer renderer = GuiGraphics::renderTooltip;
        widgets.add(new Widget(left + 28, top + 53, setPotion(), renderer));
        int left = x + 5, top = y + 135, columns = 0;
        for (var stack : this.stacks) {
            widgets.add(new Widget(left, top, stack, renderer));
            left += ICON_SIZE + 4;
            if (++columns >= ITEM_COLUMNS) {
                columns = 0;
                top += ICON_SIZE + 2;
                left = x + 5;
            }
        }
        if (this.fluid != null) {
            var solvent = new ArrayList<Component>();
            solvent.add(Component.translatable("tooltip.magic_and_taboo.magic_potion.solvent", this.fluid.getFluidType().getDescription()));
            solvent.add(Component.translatable("tooltip.magic_and_taboo.magic_potion.solvent.add"));
            widgets.add(new Widget(
                    x + 8,
                    y + 29,
                    new ItemStack(this.fluid.getBucket()),
                    (graphics, font, stack, mouseX, mouseY) ->
                            graphics.renderComponentTooltip(font, solvent, mouseX, mouseY)
            ));
        }
        widgets.add(new Widget(
                x + 8,
                y + 56,
                new ItemStack(MATItems.BLAZE_BURNER.get()),
                (graphics, font, stack, mouseX, mouseY) ->
                        graphics.renderComponentTooltip(font, this.temperature, mouseX, mouseY)
        ));

        var red = new ArrayList<Component>();
        red.add(Component.translatable("item.magic_and_taboo.glass_potion_bottle_red"));
        red.add(Component.translatable("tooltip.magic_and_taboo.magic_potion.red"));
        widgets.add(new Widget(
                x + 8,
                y + 82,
                new ItemStack(MATItems.POTION_BOTTLE_RED.get()),
                (graphics, font, stack, mouseX, mouseY) ->
                        graphics.renderComponentTooltip(font, red, mouseX, mouseY)
        ));

        var glow = new ArrayList<Component>();
        glow.add(Component.translatable("item.magic_and_taboo.glass_potion_bottle_glow"));
        glow.add(Component.translatable("tooltip.magic_and_taboo.magic_potion.glow"));
        widgets.add(new Widget(
                x + 8,
                y + 109,
                new ItemStack(MATItems.POTION_BOTTLE_GLOW.get()),
                (graphics, font, stack, mouseX, mouseY) ->
                        graphics.renderComponentTooltip(font, glow, mouseX, mouseY)
        ));
//        widgets.add(new Widget(
//                x + 30,
//                y + 53,
//                setPotion(),
//                (graphics, font, stack, mouseX, mouseY) ->
//                        graphics.renderComponentTooltip(font, glow, mouseX, mouseY)
//        ));

    }
    public ItemStack setPotion() {
        var itemStack = new ItemStack(MATItems.POTION_BOTTLE.get());
        var elements = this.elements;
        for (var entry : elements.object2FloatEntrySet()) {
            var element = entry.getKey();
            float conflict = 0.0F, bonus = 1.0F;
            for (var inner : element.resistanceElementMap().object2FloatEntrySet()) {
                if (elements.containsKey(inner.getKey().value())) {
                    conflict += inner.getFloatValue();
                }
            }
            for (var inner : element.resistanceElementMap().object2FloatEntrySet()) {
                if (elements.containsKey(inner.getKey().value())) {
                    bonus += inner.getFloatValue();
                }
            }
            entry.setValue((entry.getFloatValue() - conflict) * bonus);
        }
        var cap = itemStack.getCapability(MATCapabilities.MAGIC_POTION);
        cap.ifPresent( c ->
                c.setContent(this.fluid.getFluidType(), elements)
        );
        return itemStack;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTicks);
        graphics.blit(PARCHMENT, this.left, this.top, 0, 0, WIDTH, HEIGHT, WIDTH, HEIGHT);

        boolean tooltip = false;
        for (var widget : this.widgets) {
            var stack = widget.stack;
            if (stack.isEmpty()) continue;
            if (stack.is(MATItems.POTION_BOTTLE.get())) {
                renderScaledItem(graphics, stack, widget.left, widget.top, 4.0F);
            } else {
                graphics.renderItem(stack, widget.left, widget.top);
            }
            if (!tooltip) {
                int size = stack.is(MATItems.POTION_BOTTLE.get()) ? 64 : 16;
                if (ClientUtil.isMouseOver(mouseX, mouseY, widget.left, widget.top, size, size)) {
                    widget.tooltip.render(graphics, this.font, stack, mouseX, mouseY);
                    tooltip = true;
                }
            }
        }
    }
    public void renderScaledItem(GuiGraphics graphics, ItemStack stack, int left, int top, float scale){
        var pose = graphics.pose();

        pose.pushPose();

        pose.translate(left, top, 0);
        pose.scale(scale, scale, 1.0F);
        pose.translate(-left, -top, 0);
        graphics.renderItem(stack, left, top);

        pose.popPose();
        RenderSystem.applyModelViewMatrix();
    }

    public interface TooltipRenderer {
        void render(GuiGraphics graphics, Font font, ItemStack stack, int mouseX, int mouseY);
    }

    public record Widget(int left, int top, ItemStack stack, TooltipRenderer tooltip) {
    }
    public class sliderButton extends AbstractSliderButton {

        public sliderButton(int pX, int pY, int pWidth, int pHeight, Component pMessage, double pValue) {
            super(pX, pY, pWidth, pHeight, pMessage, pValue);
        }

        @Override
        protected void updateMessage() {

        }

        @Override
        protected void applyValue() {

        }
    }
}
