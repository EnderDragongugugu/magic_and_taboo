package enderdragon.magic_and_taboo.client.book;

import net.minecraft.advancements.FrameType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class OccultCodexPages {
    public static final Page ENTRY = new Page.NodeBuilder(
            IListener.CLOSE_PAGE
    ).child(FrameType.CHALLENGE, root -> root
            .labeled("text.occult_codex.node.prologue")
            .withIcon(() -> new ItemStack(Items.WRITABLE_BOOK))
            .described("text.occult_codex.node.prologue.tooltip")
            .posited(0, -50)
            .clicked(book ->
                    book.open(OccultCodexPages.PROLOGUE)
            )
    ).build();
    public static final Page PROLOGUE = new Page.NodeBuilder(book ->
            book.open(OccultCodexPages.ENTRY)
    ).child(FrameType.TASK, root -> root
                    .withIcon(() -> new ItemStack(Items.DRAGON_EGG))
                    .labeled("text.occult_codex.node.prologue_1")
                    .described("text.occult_codex.node.prologue_1.tooltip")
                    .posited(0, -50)
                    .clicked(book -> book.open(OccultCodexPages.DEV))
            )
            .child(FrameType.TASK, root -> root
                    .withIcon(() -> new ItemStack(Items.ENDER_PEARL))
                    .labeled("text.occult_codex.node.prologue_2")
                    .described("text.occult_codex.node.prologue_2.tooltip")
            ).build();
//    public static final Page TEST1 = new Page.NodeBuilder(book ->
//            book.open(OccultCodexPages.ENTRY)
//    ).child(FrameType.TASK, root -> root
//            .labeled("LPaicen")
//            .described("\\ LPaicen /")
//            .posited(0, -50)
//            .clicked(
//                    book -> book.open(OccultCodexPages.TEST2)
//            )
//
//    ).build();

    public static final PageContent DEV = new PageContent.ContentBuilder()
            .add(content -> content
                    .title("text.occult_codex.node.prologue_2.content.title")
                    .text("text.occult_codex.node.prologue_2.content.text")
            ).add(content -> content
                    .title("test_2")
            ).add(content -> content
                    .title("test_3")
            )
            .build();
}
