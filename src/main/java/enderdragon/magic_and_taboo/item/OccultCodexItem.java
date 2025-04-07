package enderdragon.magic_and_taboo.item;

import enderdragon.magic_and_taboo.client.ClientUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class OccultCodexItem extends Item {
    public OccultCodexItem(Properties props) {
        super(props);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (player.isLocalPlayer()) {
            ClientUtil.openOccultCodexScreen();
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }
}
