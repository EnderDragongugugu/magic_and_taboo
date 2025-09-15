package enderdragon.magic_and_taboo.client.book.graph;

import com.google.common.collect.ImmutableList;
import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.systems.RenderSystem;
import enderdragon.magic_and_taboo.MagicAndTabooMod;
import enderdragon.magic_and_taboo.client.book.Book;
import enderdragon.magic_and_taboo.client.book.Chapter;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class GraphChapter implements Chapter {
    private static final int WIDTH = 269;
    private static final int HEIGHT = 176;
    private static final ResourceLocation PAGE_FRAME = MagicAndTabooMod.makeId("textures/gui/book/book_1.png");
    private static @NotNull GameProfile robotProfile = new GameProfile(null, "Ender_Dragon0629");
    public final ImmutableList<Node> nodes;
    public final ImmutableList<Line> lines;
    private double scrollX;
    private double scrollY;
    private boolean dragging;
    private boolean clickable;
    private AbstractClientPlayer robot;

    @SuppressWarnings("UnstableApiUsage")
    public GraphChapter(ImmutableList.Builder<Node> nodes) {
        this.nodes = nodes.build();
        var builder = ImmutableList.<Line>builderWithExpectedSize(this.nodes.size());
        for (Node node : this.nodes) {
            if (node.parent == null) continue;
            builder.add(new Line(node.parent, node));
        }
        this.lines = builder.build();
//        SkullBlockEntity.updateGameprofile(robotProfile, this::updateProfile);
    }

    @Override
    public void render(
            GuiGraphics graphics,
            Font font,
            int width,
            int height,
            int mouseX,
            int mouseY,
            float partialTicks
    ) {
        var pose = graphics.pose();
        int startX = (int) scrollX;
        int startY = (int) scrollY;
//        test
        int x = (width - WIDTH) / 2;
        int y = (height - HEIGHT) / 2;

        RenderSystem.enableBlend();

        pose.pushPose();
        RenderSystem.disableBlend();
        graphics.fillGradient(
                RenderType.endGateway(),
                x + 6, y + 14, x + WIDTH - 6, y + HEIGHT - 6,
                0, 0, 0
        );

        RenderSystem.enableBlend();
        pose.popPose();

//        if (this.robot != null) {
//            InventoryScreen.renderEntityInInventoryFollowsMouse(
//                    graphics,
//                    startX + 20,
//                    startY + 20,
//                    30,
//                    (float) (startX + 20) - mouseX,
//                    (float) (startY + 20 - 50) - mouseY,
//                    this.robot
//            );
//        }

        pose.pushPose();
        pose.translate(0, 0, 200);
        graphics.blit(PAGE_FRAME, x, y, 0, 0, WIDTH, HEIGHT, 353, 256);
        pose.popPose();

        graphics.enableScissor(x + 8, y + 16, x + WIDTH - 4, y + HEIGHT - 4);

        for (Line line : this.lines) {
            line.render(graphics, startX, startY);
        }

        Node hovered = null;

        for (Node node : this.nodes) {
            node.render(graphics, font, startX, startY);
            if (Node.isMouseOver(node, mouseX, mouseY, startX, startY)) {
                hovered = node;
            }
        }
        graphics.disableScissor();

        if (hovered != null) {
            pose.pushPose();
            pose.translate(0, 0, 4000.0F);
            RenderSystem.enableDepthTest();
            graphics.renderTooltip(font, hovered.getTooltip(), Optional.empty(), mouseX, mouseY);
            RenderSystem.disableDepthTest();
            pose.popPose();
        }
    }

    @Override
    public Collection<AbstractWidget> init(Font font, int width, int height) {
        this.scrollX = width / 2.0;
        this.scrollY = height / 2.0;
        this.nodes.forEach(Node::reload);
        this.clickable = false;
        this.robot = null;
//        SkullBlockEntity.updateGameprofile(robotProfile, this::updateProfile);
        return Collections.emptyList();
    }

    @Override
    public boolean onMouseDown(Book book, double x, double y, int button) {
        if (button != 0) return false;
        this.dragging = true;
        this.clickable = true;
        return true;
    }

    @Override
    public boolean onMouseUp(Book book, double x, double y, int button) {
        if (button != 0) return false;
        this.dragging = false;
        if (this.clickable) {
            int offsetX = (int) this.scrollX, offsetY = (int) this.scrollY;
            for (Node node : this.nodes) {
                if (Node.isMouseOver(node, x, y, offsetX, offsetY)) {
                    node.onClick.trigger(book);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean onDrag(double deltaX, double deltaY, int button) {
        if (button != 0 || !this.dragging) return false;
        this.scrollX += deltaX;
        this.scrollY += deltaY;
        if (deltaX > 0.5 || deltaX < -0.5 || deltaY > 0.5 || deltaY < -0.5) {
            this.clickable = false;
        }
        return true;
    }

//    protected void updateProfile(GameProfile profile) {
//        robotProfile = profile;
//        var level = Minecraft.getInstance().level;
//        if (level == null) return;
//        this.robot = new AbstractClientPlayer(level, profile) {
//            @Override
//            public boolean isSkinLoaded() {
//                return true;
//            }
//
//            @Override
//            public @NotNull ResourceLocation getSkinTextureLocation() {
//                var minecraft = Minecraft.getInstance();
//                var map = minecraft.getSkinManager().getInsecureSkinInformation(this.getGameProfile());
//                return map.containsKey(SKIN)
//                        ? minecraft.getSkinManager().registerTexture(map.get(SKIN), SKIN)
//                        : DefaultPlayerSkin.getDefaultSkin(UUIDUtil.getOrCreatePlayerUUID(this.getGameProfile()));
//            }
//        };
//    }
}
