package enderdragon.magic_and_taboo.client.book;

import enderdragon.magic_and_taboo.init.MATItems;
import net.minecraft.advancements.FrameType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class OccultCodexPages {
    public static final Page ENTRY = new Page.Builder(
            IListener.CLOSE
    ).child(FrameType.CHALLENGE, a -> a
            .labeled("a")
            .described("笨龙")
            .posited(0, -50)
            .clicked(book ->
                    book.open(OccultCodexPages.TEST)
            ).child(FrameType.GOAL, a1 -> a1
                    .withIcon(() -> new ItemStack(MATItems.CONDENSER.get()))
                    .labeled("a 1")
                    .described("哈!")
                    .posited(50, 0)
                    .child(FrameType.TASK, b -> b
                            .labeled("b")
                            .posited(50, 50)
                    )
            ).child(FrameType.TASK, a2 -> a2
                    .withIcon(() -> new ItemStack(Items.ARROW))
                    .labeled("a 2")
                    .posited(-50, 0)
            )
    ).child(FrameType.TASK, n -> n
            .withIcon(() -> new ItemStack(MATItems.CONDENSER.get()))
            .labeled("n")
            .posited(-50, -50)
            .clicked(IListener.CLOSE)
            .child(FrameType.TASK, d -> d
                    .withIcon(() -> new ItemStack(MATItems.CONDENSER.get()))
                    .labeled("¿")
                    .posited(0, -100)
                    .clicked(book ->
                            book.open(OccultCodexPages.TEST1)
                    )
            )
    ).build();
    public static final Page TEST = new Page.Builder(book ->
            book.open(OccultCodexPages.ENTRY)
    ).child(FrameType.TASK, root -> root
            .labeled("c")
            .posited(0, -50)
    ).build();
    public static final Page TEST1 = new Page.Builder(book ->
            book.open(OccultCodexPages.ENTRY)
    ).child(FrameType.TASK, root -> root
            .labeled("LPaicen")
            .described("\\ LPaicen /")
            .posited(0, -50)
    ).build();
}
