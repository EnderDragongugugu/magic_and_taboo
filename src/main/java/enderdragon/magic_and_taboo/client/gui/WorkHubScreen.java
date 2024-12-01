package enderdragon.magic_and_taboo.client.gui;

import enderdragon.magic_and_taboo.MagicAndTabooMod;
import enderdragon.magic_and_taboo.block.entity.WorkHubBlockEntity;
import enderdragon.magic_and_taboo.inventory.WorkHubMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class WorkHubScreen extends AbstractContainerScreen<WorkHubMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MagicAndTabooMod.MOD_ID, "textures/gui/container/work_hub.png");

    public WorkHubScreen(WorkHubMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);
        this.renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        WorkHubBlockEntity hub = this.menu.workHub;
        int i = this.leftPos;
        int j = this.topPos;
        graphics.blit(TEXTURE, i, j, 0, 0, this.imageWidth, this.imageHeight);
        int k = (int) ((float) hub.time.get() / (float) hub.timeTotal.get() * 24);
        graphics.blit(TEXTURE, i + 78, j + 19, 176, 0, k + 1, 16);
    }
}
