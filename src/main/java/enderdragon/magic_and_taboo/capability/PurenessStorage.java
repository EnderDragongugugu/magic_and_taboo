package enderdragon.magic_and_taboo.capability;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import org.jetbrains.annotations.Nullable;

@AutoRegisterCapability
public interface PurenessStorage {
    int getMaxPureness();

    int getPureness();

    void setPureness(int amount);

    void setSource(@Nullable EntityType<?> source);

    @Nullable EntityType<?> getSource();

    /**
     * @return a virtual container if it exists
     */
    ItemStack tryReplaceContainer(Item container);

    ItemStack takeFilledContainer();

    String getFormattedPureness();

    float getPercent();

    boolean isValid();

    boolean transferForm(PurenessStorage other);

    default void receivePureness(int amount) {
        this.setPureness(this.getPureness() + amount);
    }

    default void extractPureness(int amount) {
        this.setPureness(this.getPureness() - amount);
    }

    PurenessStorage EMPTY = new PurenessStorage() {
        @Override
        public int getMaxPureness() {return 0;}

        @Override
        public int getPureness() {return 0;}

        @Override
        public void setPureness(int amount) {}

        @Override
        public void setSource(@Nullable EntityType<?> source) {}

        @Nullable
        @Override
        public EntityType<?> getSource() {return null;}

        @Override
        public ItemStack tryReplaceContainer(Item container) {return ItemStack.EMPTY;}

        @Override
        public ItemStack takeFilledContainer() {return ItemStack.EMPTY;}

        @Override
        public String getFormattedPureness() {return "0.0";}

        @Override
        public float getPercent() {return 0;}

        @Override
        public boolean isValid() {return false;}

        @Override
        public boolean transferForm(PurenessStorage other) {return false;}
    };
}
