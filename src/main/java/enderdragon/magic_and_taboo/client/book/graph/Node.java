package enderdragon.magic_and_taboo.client.book.graph;

import com.google.common.collect.ImmutableList;
import enderdragon.magic_and_taboo.client.book.IListener;
import net.minecraft.advancements.FrameType;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Supplier;

public class Node {
    private static final ResourceLocation WIDGETS_LOCATION = new ResourceLocation("textures/gui/advancements/widgets.png");
    public final Node parent;
    public final FrameType type;
    public final int halfWidth;
    public final int halfHeight;
    public final int x;
    public final int y;
    public final IListener onClick;
    private final Supplier<ItemStack> iconSupplier;
    private final ImmutableList<Component> tooltip;
    private @Nonnull ItemStack icon = ItemStack.EMPTY;

    public Node(
            Node parent,
            FrameType type,
            ImmutableList.Builder<Component> tooltip,
            int x,
            int y,
            IListener onClick,
            Supplier<ItemStack> icon
    ) {
        this.parent = parent;
        this.type = type;
        this.tooltip = tooltip.build();
        this.x = x;
        this.y = y;
        this.halfWidth = 10;
        this.halfHeight = 10;
        this.iconSupplier = icon;
        this.onClick = onClick;
    }

    public void reload() {
        this.icon = this.iconSupplier.get();
    }

    public boolean isHovered(int offsetX, int offsetY, double mouseX, double mouseY) {
        int x = offsetX + this.x;
        int y = offsetY + this.y;
        return mouseX >= x - this.halfWidth && mouseX <= x + this.halfWidth &&
                mouseY >= y - this.halfHeight && mouseY <= y + this.halfHeight;
    }

    public @NotNull List<Component> getTooltip() {
        return this.tooltip;
    }

    public void render(GuiGraphics graphics, Font font, int offsetX, int offsetY) {
        int x = offsetX + this.x;
        int y = offsetY + this.y;
        graphics.blit(WIDGETS_LOCATION, x - 13, y - 13, this.type.getTexture(), 128, 26, 26);
        if (!icon.isEmpty()) {
            graphics.renderItem(icon, x - 8, y - 8);
        }
    }
}
