package enderdragon.magic_and_taboo.client;

import enderdragon.magic_and_taboo.client.book.OccultCodexChapters;
import enderdragon.magic_and_taboo.client.gui.MagicPotionParchmentScreen;
import enderdragon.magic_and_taboo.client.gui.OccultCodexScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.Nullable;

public class ClientUtil {
    /// get client side registries
    public static @Nullable RegistryAccess getOptionalRegistries() {
        var connection = Minecraft.getInstance().getConnection();
        return connection == null ? null : connection.registryAccess();
    }

    public static void openOccultCodexScreen() {
        Minecraft.getInstance().setScreen(new OccultCodexScreen(OccultCodexChapters.ENTRY));
    }

    public static void openMagicPotionParchmentScreen(CompoundTag tag) {
        Minecraft.getInstance().setScreen(new MagicPotionParchmentScreen(tag));
    }

    public static boolean isMouseOver(int mouseX, int mouseY, int left, int top, int width, int height) {
        return mouseX >= left && mouseX <= left + width && mouseY >= top && mouseY <= top + height;
    }

    public static boolean isMouseOver(double mouseX, double mouseY, int left, int top, int width, int height) {
        return mouseX >= left && mouseX <= left + width && mouseY >= top && mouseY <= top + height;
    }
}
