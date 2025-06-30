package enderdragon.magic_and_taboo.capability;

import net.minecraft.resources.ResourceLocation;

import static enderdragon.magic_and_taboo.MagicAndTabooMod.makeId;

public interface IPlayerMagicPoint {
    ResourceLocation IDENTIFIER = makeId("magic_point");
    
    int getMagic();

    int getMaxMagic();

    void setMagic(int amount);

    void setMaxMagic(int amount);

    void addMagic(int amount);

    IPlayerMagicPoint EMPTY = new IPlayerMagicPoint() {

        @Override
        public int getMagic() {
            return 0;
        }

        @Override
        public int getMaxMagic() {
            return 0;
        }

        @Override
        public void setMagic(int amount) {

        }

        @Override
        public void setMaxMagic(int amount) {

        }

        @Override
        public void addMagic(int amount) {

        }
    };
}
