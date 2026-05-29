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

import java.util.function.Consumer;

import static com.mojang.authlib.minecraft.MinecraftProfileTexture.Type.SKIN;

public class MannequinChunk extends Chunk implements Consumer<GameProfile> {
    public static MannequinChunk of(String name) {
        return new MannequinChunk(name);
    }

    private @NotNull GameProfile profile;
    private AbstractClientPlayer mannequin;

    public MannequinChunk(String name) {
        this.profile = new GameProfile(UUIDUtil.getOrCreatePlayerUUID(new GameProfile(null, name)), name);
    }

    @Override
    public int measure(Font font, int space) {
        SkullBlockEntity.updateGameprofile(this.profile, this);
        return 80;
    }

    @Override
    public void render(GuiGraphics graphics, Font font, int mouseX, int mouseY, float partialTicks) {
        var pose = graphics.pose();
        pose.popPose();
        int x = this.left + 55;
        int y = this.top + 70;
        if (this.mannequin != null) {
            InventoryScreen.renderEntityInInventoryFollowsMouse(
                    graphics,
                    x,
                    y,
                    30,
                    (float) x - mouseX,
                    (float) y - mouseY,
                    this.mannequin
            );
        }
        pose.pushPose();
    }

    @Override
    public void accept(GameProfile profile) {
        this.profile = profile;
        var level = Minecraft.getInstance().level;
        if (level == null) return;
        this.mannequin = new AbstractClientPlayer(level, profile) {
            @Override
            public @NotNull ResourceLocation getSkinTextureLocation() {
                var minecraft = Minecraft.getInstance();
                var map = minecraft.getSkinManager().getInsecureSkinInformation(this.getGameProfile());
                return map.containsKey(SKIN)
                        ? minecraft.getSkinManager().registerTexture(map.get(SKIN), SKIN)
                        : DefaultPlayerSkin.getDefaultSkin(UUIDUtil.getOrCreatePlayerUUID(this.getGameProfile()));
            }

            @Override
            public boolean isSkinLoaded() {
                return true;
            }

            @Override
            public boolean isModelPartShown(PlayerModelPart part) {
                return true;
            }
        };
    }
}
