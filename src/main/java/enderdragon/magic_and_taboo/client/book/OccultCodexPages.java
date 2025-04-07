package enderdragon.magic_and_taboo.client.book;

public class OccultCodexPages {
    public static final Page ENTRY = new Page.Builder(
            IListener.CLOSE
    ).child(a -> a
            .labeled("a")
            .posited(0, -50)
            .pressed(book ->
                    book.open(OccultCodexPages.TEST)
            ).child(a1 -> a1
                    .labeled("a 1")
                    .posited(50, 0)
                    .child(b -> b
                            .labeled("b")
                            .posited(50, 50)
                    )
            ).child(a2 -> a2
                    .labeled("a 2")
                    .posited(-50, 0)
            )
    ).child(n -> n
            .labeled("n")
            .sized(30, 10)
            .posited(-50, -50)
            .pressed(IListener.CLOSE)
            .child(d -> d
                    .labeled("¿")
                    .sized(10, 40)
                    .posited(0, -100)
            )
    ).build();
    public static final Page TEST = new Page.Builder(book ->
            book.open(OccultCodexPages.ENTRY)
    ).child(root -> root
            .labeled("c")
            .posited(0, -50)
    ).build();
}
