package enderdragon.magicandtaboo.capability;

import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import org.jetbrains.annotations.Nullable;

@AutoRegisterCapability
public interface IPurenessStorage {
    int getMaxPureness();

    int getPureness();

    void setPureness(int amount);

    void setSource(@Nullable EntityType<?> source);

    @Nullable EntityType<?> getSource();

    String getFormattedPureness();

    float getPercent();

    boolean isValid();

    boolean transferForm(IPurenessStorage other);

    default void receivePureness(int amount) {
        this.setPureness(this.getPureness() + amount);
    }

    default void extractPureness(int amount) {
        this.setPureness(this.getPureness() - amount);
    }

    IPurenessStorage EMPTY = new IPurenessStorage() {
        @Override
        public int getMaxPureness() {return 0;}

        @Override
        public int getPureness() {return 0;}

        @Override
        public void setPureness(int amount) {}

        @Override
        public void setSource(EntityType<?> source) {}

        @Override
        public EntityType<?> getSource() {return null;}

        @Override
        public String getFormattedPureness() {return "0.0";}

        @Override
        public float getPercent() {return 0;}

        @Override
        public boolean isValid() {return false;}

        @Override
        public boolean transferForm(IPurenessStorage other) {return false;}
    };
}
