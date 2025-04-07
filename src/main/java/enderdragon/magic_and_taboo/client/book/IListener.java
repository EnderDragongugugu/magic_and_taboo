package enderdragon.magic_and_taboo.client.book;

public interface IListener {
    void trigger(IBook screen);

    IListener CLOSE = book -> book.open(null);
    IListener NONE = book -> {};
}
