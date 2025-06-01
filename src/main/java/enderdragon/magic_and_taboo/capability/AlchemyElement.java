package enderdragon.magic_and_taboo.capability;

import enderdragon.magic_and_taboo.registry.Element;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import org.jetbrains.annotations.Nullable;

@AutoRegisterCapability
public interface AlchemyElement {

    void setElement(ResourceKey<Element> key, Float count);

    @Nullable
    Element getElement();


    float getCount();

    AlchemyElement EMPTY = new AlchemyElement() {
        @Override
        public void setElement(ResourceKey<Element> key, Float count) {

        }

        @Override
        public Element getElement() {
            return null;
        }

        @Override
        public float getCount() {
            return 0.0F;
        }
    };
}
