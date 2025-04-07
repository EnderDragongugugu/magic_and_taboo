package enderdragon.magic_and_taboo.client.book;

import enderdragon.magic_and_taboo.init.MATItems;
import net.minecraft.world.item.Items;

public class OccultCodexPages {
    public static final Page ENTRY = new Page.Builder(
            IListener.CLOSE
    ).child(a -> a
            .labeled("a")
            .posited(0, -50)
            .nodeType(Node.NodeType.SECTION)
            .pressed(book ->
                    book.open(OccultCodexPages.TEST)
            ).child(a1 -> a1
                    .labeled("a 1")
                    .nodeType(Node.NodeType.PART)
                    .content("哈!")
                    .icon(MATItems.CONDENSER.get())
                    .posited(50, 0)
                    .child(b -> b
                            .labeled("b")
                            .posited(50, 50)
                    )
            ).child(a2 -> a2
                    .labeled("a 2")
                    .icon(Items.ARROW)
                    .posited(-50, 0)
            )
    ).child(n -> n
            .labeled("n")
            .icon(MATItems.CONDENSER.get())
            .posited(-50, -50)
            .pressed(IListener.CLOSE)
            .child(d -> d
                    .labeled("¿")
                    .icon(MATItems.CONDENSER.get())
                    .posited(0, -100)
                    .pressed(book ->
                            book.open(OccultCodexPages.TEST1)
                    )
            )
    ).build();
    public static final Page TEST = new Page.Builder(book ->
            book.open(OccultCodexPages.ENTRY)
    ).child(root -> root
            .labeled("c")
            .posited(0, -50)
    ).build();
    public static final Page TEST1 = new Page.Builder(book ->
            book.open(OccultCodexPages.ENTRY)
    ).child(root -> root
            .labeled("d")
            .posited(0, -50)
    ).build();
}
