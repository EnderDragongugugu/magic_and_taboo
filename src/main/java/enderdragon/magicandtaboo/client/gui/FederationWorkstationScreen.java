package enderdragon.magicandtaboo.client.gui;

import enderdragon.magicandtaboo.inventory.menu.FederationWorkstationMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class FederationWorkstationScreen extends AbstractContainerScreen<FederationWorkstationMenu>{

    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/container/furnace.png");

    public FederationWorkstationScreen(FederationWorkstationMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pGuiGraphics);
            this.renderBg(pGuiGraphics, pPartialTick, pMouseX, pMouseY);
        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        int i = this.leftPos;
        int j = this.topPos;
        pGuiGraphics.blit(TEXTURE, i, j, 0, 0, this.imageWidth, this.imageHeight);
//        if (this.menu.isLit()) {
//            int k = this.menu.getLitProgress();
//            pGuiGraphics.blit(TEXTURE, i + 56, j + 36 + 12 - k, 176, 12 - k, 14, k + 1);
//        }
//
//        int l = this.menu.getBurnProgress();
//        pGuiGraphics.blit(TEXTURE, i + 79, j + 34, 176, 14, l + 1, 16);
    }
}
