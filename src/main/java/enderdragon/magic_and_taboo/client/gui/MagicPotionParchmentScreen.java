package enderdragon.magic_and_taboo.client.gui;

import enderdragon.magic_and_taboo.MagicAndTabooMod;
import enderdragon.magic_and_taboo.client.ClientUtil;
import enderdragon.magic_and_taboo.init.MATCapabilities;
import enderdragon.magic_and_taboo.init.MATItems;
import enderdragon.magic_and_taboo.registry.Element;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.Reference2FloatOpenHashMap;
import net.minecraft.client.Minecraft;
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
import net.minecraftforge.fluids.FluidStack;

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
    protected final ObjectArrayList<Slot> slots = new ObjectArrayList<>();
    public final NonNullList<ItemStack> stacks = NonNullList.withSize(MAX_RECIPE_SIZE, ItemStack.EMPTY);
    private final ItemStack bottle;
    private final List<Component> temperature;
    public final Fluid fluid;
    private int top;
    private int left;

    public MagicPotionParchmentScreen(CompoundTag tag) {
        super(CommonComponents.EMPTY);
        ContainerHelper.loadAllItems(tag, stacks);
        this.fluid = FluidStack.loadFluidStackFromNBT(tag.getCompound("Fluid")).getFluid();
        this.temperature = List.of(
                Component.translatable("tooltip.magic_and_taboo.magic_potion.temperature", tag.getInt("Temperature")),
                Component.translatable("tooltip.magic_and_taboo.magic_potion.temperature.add")
        );
        var elements = tag.getCompound("Elements");
        if (elements.isEmpty()) {
            this.bottle = ItemStack.EMPTY;
            return;
        }
        (this.bottle = new ItemStack(MATItems.POTION_BOTTLE.get())).getCapability(MATCapabilities.MAGIC_POTION).ifPresent(potion -> {
            var content = new Reference2FloatOpenHashMap<Element>();
            var lookup = getRegistries(Minecraft.getInstance().level).registryOrThrow(Element.RESOURCE_KEY);
            for (var entry : lookup.entrySet()) {
                float value = elements.getFloat(entry.getKey().location().toString());
                if (value <= 0.0F) continue;
                content.put(entry.getValue(), value);
            }
            Element.antagonize(content);
            potion.setContent(this.fluid.getFluidType(), content);
        });
    }

    @Override
    public void init() {
        super.init();
        int x = this.left = (this.width - WIDTH) / 2;
        int y = this.top = (this.height - HEIGHT) / 2;
        var slots = this.slots;
        slots.clear();
        TooltipRenderer itemTooltip = GuiGraphics::renderTooltip;
        int left = x + 5, top = y + 135, columns = 0;
        for (var stack : this.stacks) {
            slots.add(new Slot(left, top, stack, itemTooltip));
            left += ICON_SIZE + 4;
            if (++columns >= ITEM_COLUMNS) {
                columns = 0;
                top += ICON_SIZE + 2;
                left = x + 5;
            }
        }
        if (this.fluid != null) {
            slots.add(new Slot(x + 8, y + 29, new ItemStack(this.fluid.getBucket()), TooltipRenderer.of(List.of(
                    Component.translatable("tooltip.magic_and_taboo.magic_potion.solvent", this.fluid.getFluidType().getDescription()),
                    Component.translatable("tooltip.magic_and_taboo.magic_potion.solvent.add")
            ))));
        }
        slots.add(new Slot(
                x + 8,
                y + 56,
                new ItemStack(MATItems.BLAZE_BURNER.get()),
                (graphics, font, stack, mouseX, mouseY) ->
                        graphics.renderComponentTooltip(font, this.temperature, mouseX, mouseY)
        ));
        slots.add(new Slot(x + 8, y + 82, new ItemStack(MATItems.POTION_BOTTLE_RED.get()), TooltipRenderer.of(List.of(
                Component.translatable("item.magic_and_taboo.glass_potion_bottle_red"),
                Component.translatable("tooltip.magic_and_taboo.magic_potion.red")
        ))));
        slots.add(new Slot(x + 8, y + 109, new ItemStack(MATItems.POTION_BOTTLE_GLOW.get()), TooltipRenderer.of(List.of(
                Component.translatable("item.magic_and_taboo.glass_potion_bottle_glow"),
                Component.translatable("tooltip.magic_and_taboo.magic_potion.glow")
        ))));
//        slots.add(new slot(
//                x + 30,
//                y + 53,
//                setPotion(),
//                (graphics, font, stack, mouseX, mouseY) ->
//                        graphics.renderComponentTooltip(font, glow, mouseX, mouseY)
//        ));
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTicks);
        graphics.blit(PARCHMENT, this.left, this.top, 0, 0, WIDTH, HEIGHT, WIDTH, HEIGHT);
        var font = this.font;
        boolean tooltip = false;
        if (!this.bottle.isEmpty()) {
            int left = this.left + 28, top = this.top + 53;
            var pose = graphics.pose();
            pose.pushPose();
            pose.translate(left, top, 0);
            pose.scale(4.0F, 4.0F, 1.0F);
            pose.translate(-left, -top, 0);
            graphics.renderItem(this.bottle, left, top);
            pose.popPose();
            if (ClientUtil.isMouseOver(mouseX, mouseY, left, top, 64, 64)) {
                graphics.renderTooltip(font, this.bottle, mouseX, mouseY);
                tooltip = true;
            }
        }
        for (var slot : this.slots) {
            var stack = slot.stack;
            if (stack.isEmpty()) continue;
            graphics.renderItem(stack, slot.left, slot.top);
            if (tooltip) continue;
            if (ClientUtil.isMouseOver(mouseX, mouseY, slot.left, slot.top, 16, 16)) {
                slot.tooltip.render(graphics, font, stack, mouseX, mouseY);
                tooltip = true;
            }
        }
    }

    public interface TooltipRenderer {
        static TooltipRenderer of(List<Component> tooltips) {
            return (graphics, font, stack, mouseX, mouseY) ->
                    graphics.renderComponentTooltip(font, tooltips, mouseX, mouseY);
        }

        void render(GuiGraphics graphics, Font font, ItemStack stack, int mouseX, int mouseY);
    }

    public record Slot(int left, int top, ItemStack stack, TooltipRenderer tooltip) {}
}
