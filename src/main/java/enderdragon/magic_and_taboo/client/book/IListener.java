package enderdragon.magic_and_taboo.client.book;

public interface IListener {
    void trigger(IBook screen);

    IListener CLOSE_PAGE = book -> book.open((Page) null);
    IListener CLOSE_PAGE_CONTENT = book -> book.open((PageContent) null);
    IListener NONE = book -> {
    };
}
