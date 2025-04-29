package enderdragon.magic_and_taboo.client.book.page;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

public class Separator extends Chunk {
    public static final Separator NEXT_PAGE = new Separator();

    public static Separator of(int distance) {
        return distance < 0 ? NEXT_PAGE : new Fixed(distance);
    }

    protected Separator() {}

    @Override
    public int measure(Font font, int space) {
        return space;
    }

    @Override
    public void render(GuiGraphics graphics, Font font, int mouseX, int mouseY, float partialTicks) {}

    public static class Fixed extends Separator {
        public final int distance;

        public Fixed(int distance) {
            this.distance = distance;
        }

        @Override
        public int measure(Font font, int space) {
            return this.distance;
        }
    }
}
