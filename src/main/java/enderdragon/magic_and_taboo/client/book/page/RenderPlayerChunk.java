package enderdragon.magic_and_taboo.client.book.page;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.core.UUIDUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import org.jetbrains.annotations.NotNull;

import static com.mojang.authlib.minecraft.MinecraftProfileTexture.Type.SKIN;

public class RenderPlayerChunk extends Chunk {
    public static RenderPlayerChunk renderPlayer(String name) {
        return new RenderPlayerChunk(name);
    }

    private @NotNull GameProfile robotProfile;
    private AbstractClientPlayer robot;

    public RenderPlayerChunk(String name) {
        robotProfile = new GameProfile(null, name);
    }

    @Override
    public int measure(Font font, int space) {
        return this.top + 80;
    }

    @Override
    public void render(GuiGraphics graphics, Font font, int mouseX, int mouseY, float partialTicks) {
        var pose = graphics.pose();
        pose.popPose();
        int x = this.left + 55;
        int y = this.top + 70;
        SkullBlockEntity.updateGameprofile(robotProfile, this::updateProfile);
        if (this.robot != null) {
            InventoryScreen.renderEntityInInventoryFollowsMouse(
                    graphics,
                    x,
                    y,
                    30,
                    (float) x - mouseX,
                    (float) y - mouseY,
                    this.robot
            );
        }

        pose.pushPose();
    }

    protected void updateProfile(GameProfile profile) {
        robotProfile = profile;
        var level = Minecraft.getInstance().level;
        if (level == null) return;
        this.robot = new AbstractClientPlayer(level, profile) {
            @Override
            public boolean isSkinLoaded() {
                return true;
            }

            @Override
            public @NotNull ResourceLocation getSkinTextureLocation() {
                var minecraft = Minecraft.getInstance();
                var map = minecraft.getSkinManager().getInsecureSkinInformation(this.getGameProfile());
                return map.containsKey(SKIN)
                        ? minecraft.getSkinManager().registerTexture(map.get(SKIN), SKIN)
                        : DefaultPlayerSkin.getDefaultSkin(UUIDUtil.getOrCreatePlayerUUID(this.getGameProfile()));
            }

            @Override
            public boolean isModelPartShown(PlayerModelPart pPart) {
//                if (profile.getName().equals("2190303755")) {
//                    return false;
//                }
//                if (profile.getName().equals("bltsbb_114514")) {
//                    return false;
//                }
                return true;
            }
        };
    }
}
