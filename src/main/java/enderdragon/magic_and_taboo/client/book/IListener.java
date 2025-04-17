package enderdragon.magic_and_taboo.client.book;

public interface IListener {
    void trigger(IBook screen);

    IListener NONE = book -> {};
}
