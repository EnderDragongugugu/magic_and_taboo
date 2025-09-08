package enderdragon.magic_and_taboo.client.gui;

import enderdragon.magic_and_taboo.MagicAndTabooMod;
import enderdragon.magic_and_taboo.block.entity.WorkHubBlockEntity;
import enderdragon.magic_and_taboo.inventory.WorkHubMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class WorkHubScreen extends AbstractContainerScreen<WorkHubMenu> {
    private static final ResourceLocation TEXTURE = MagicAndTabooMod.makeId("textures/gui/container/work_hub.png");

    public WorkHubScreen(WorkHubMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);
        this.renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        WorkHubBlockEntity hub = this.menu.workHub;
        int left = this.leftPos, top = this.topPos;
        graphics.blit(TEXTURE, left, top, 0, 0, this.imageWidth, this.imageHeight);
        int width = 1 + (int) (hub.time.get() * 24 / (float) hub.timeTotal.get());
        graphics.blit(TEXTURE, left + 78, top + 19, 176, 0, width, 16);
    }
}
