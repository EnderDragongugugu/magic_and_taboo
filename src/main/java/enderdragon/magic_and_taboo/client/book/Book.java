package enderdragon.magic_and_taboo.client.book;

import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Style;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface Book {
    void turnTo(@Nullable Chapter chapter, boolean isOverlay);

    Font getFont();

    /**
     * Use different name because vanilla name will get obfuscated.
     *
     * @see net.minecraft.client.gui.screens.Screen#handleComponentClicked(Style)
     */
    boolean onClickComponent(@Nonnull Style style);
}
