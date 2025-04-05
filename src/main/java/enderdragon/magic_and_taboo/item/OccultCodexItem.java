package enderdragon.magic_and_taboo.item;

import enderdragon.magic_and_taboo.network.NetworkHandler;
import enderdragon.magic_and_taboo.network.OccultCodexGUIPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class OccultCodexItem extends Item {
    public OccultCodexItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        var stack = pPlayer.getItemInHand(pUsedHand);
        if (pPlayer instanceof ServerPlayer player) {
            NetworkHandler.sendToPlayer(player, new OccultCodexGUIPacket());
        }
        return InteractionResultHolder.success(stack);
    }
}
