package enderdragon.magic_and_taboo.item;

import enderdragon.magic_and_taboo.client.ClientUtil;
import enderdragon.magic_and_taboo.init.MATItems;
import enderdragon.magic_and_taboo.util.ContainerUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class OccultCodexItem extends Item {
    private static final String INIT_BOOK = "init_occult_codex";

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

    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        var player = event.getEntity();
        var data = player.getPersistentData();
        if (data.getBoolean(INIT_BOOK)) return;
        ContainerUtil.addItem(player, new ItemStack(MATItems.OCCULT_CODEX.get()));
        data.putBoolean(INIT_BOOK, true);
    }
}
