package enderdragon.magicandtaboo.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;

public class ClientUtil {
    public static boolean isUsing(ItemStack stack) {
        var minecraft = Minecraft.getInstance();
        if (!minecraft.options.keyUse.isDown()) return false;
        var player = minecraft.player;
        if (player == null) return false;
        var candidate = player.getUseItem();
        if (candidate == stack) return true;
        if (candidate.isEmpty()) {
            candidate = player.getMainHandItem();
            return candidate == stack || (candidate.isEmpty() && player.getOffhandItem() == stack);
        }
        return false;
    }
}
