package enderdragon.magic_and_taboo.client.book;

import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.Nullable;

import java.util.function.UnaryOperator;

public class PageContent {
    public final ImmutableList<Content> contents;


    public PageContent(ImmutableList<Content> contents) {
        this.contents = contents;
    }

    public static class ContentBuilder implements IContentBuilder {
        private final ImmutableList.Builder<Content> contents = ImmutableList.builder();

        public PageContent.ContentBuilder add(UnaryOperator<Content.Builder<PageContent.ContentBuilder>> action) {
            return this.add(this, action);
        }

        public <T extends IContentBuilder> PageContent.ContentBuilder add(T parent, UnaryOperator<Content.Builder<T>> action) {
            this.contents.add(action.apply(new Content.Builder<>(parent)).asContent());
            return this;
        }

        public PageContent build() {
            return new PageContent(this.contents.build());
        }

        @Nullable
        @Override
        public Content asContent() {
            return null;
        }
    }
}
