package enderdragon.magic_and_taboo.capability;

import net.minecraft.resources.ResourceLocation;

import static enderdragon.magic_and_taboo.MagicAndTabooMod.makeId;

public interface PlayerMagicPoint {
    ResourceLocation IDENTIFIER = makeId("magic_point");

    int getMagic();

    int getMaxMagic();

    void setMagic(int amount);

    void setMagic(int max, int magic);

    void tick();
}
