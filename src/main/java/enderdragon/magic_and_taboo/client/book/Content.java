package enderdragon.magic_and_taboo.client.book;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class Content {
    public static final Supplier<ItemStack> NO_ICON = () -> ItemStack.EMPTY;
    public static final Component NO_STR = Component.translatable("");

    public final Content parent;

    public final Component title;
    public Component text;


    public Content(
            Content parent,
            Component title,
            Component text
    ) {
        this.parent = parent;
        this.title = title;
        this.text = text;
    }


    public static class Builder<T extends IContentBuilder> implements IContentBuilder {
        public final @Nonnull T parent;
        public Component title = NO_STR;
        public Component text = NO_STR;
        public Content content;
        public Supplier<ItemStack> icon;

        public Builder(@Nonnull T parent) {
            this.parent = parent;
        }

        public Builder<T> title(String str) {
            if (this.content == null) {
                this.title = Component.translatable(str);
            }
            return this;
        }

        public Builder<T> text(String str) {
            if (this.content == null) {
                this.text = Component.translatable(str);
            }
            return this;
        }

        @Nonnull
        @Override
        public Content asContent() {
            return this.content == null ? new Content(
                    this.parent.asContent(),
                    this.title,
                    this.text
            ) : this.content;
        }
    }
}
