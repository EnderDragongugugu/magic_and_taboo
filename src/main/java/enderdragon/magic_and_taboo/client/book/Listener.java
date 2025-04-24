package enderdragon.magic_and_taboo.client.book;

public interface Listener {
    void trigger(Book screen);

    Listener NONE = book -> {};
}
