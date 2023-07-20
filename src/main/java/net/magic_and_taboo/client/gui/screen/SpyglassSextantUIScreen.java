package net.magic_and_taboo.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.magic_and_taboo.MagicAndTabooMod;
import net.magic_and_taboo.client.gui.screen.handler.SpyglassSextantUIScreenHandler;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class SpyglassSextantUIScreen extends HandledScreen<SpyglassSextantUIScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(MagicAndTabooMod.MOD_ID,"texture/block/spyglass_sextant.png");
    public SpyglassSextantUIScreen(SpyglassSextantUIScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.passEvents = false;
        this.backgroundHeight = 167;
        this.playerInventoryTitleY = this.backgroundHeight - 94;
    }
    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int i = this.x;
        int j = this.y;
        this.drawTexture(matrices, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }
}
