package enderdragon.magic_and_taboo.item;

import enderdragon.magic_and_taboo.client.ClientUtil;
import enderdragon.magic_and_taboo.init.MATItems;
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
        var persistentData = player.getPersistentData();
        if (!persistentData.getBoolean(INIT_BOOK)) {
            var book = new ItemStack(MATItems.OCCULT_CODEX.get());
            player.addItem(book);
            persistentData.putBoolean(INIT_BOOK, true);
        }
    }
}
