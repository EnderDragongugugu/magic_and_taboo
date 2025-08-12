package enderdragon.magic_and_taboo.capability;

import enderdragon.magic_and_taboo.registry.Element;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import org.jetbrains.annotations.Nullable;

@AutoRegisterCapability
public interface ElementHolder {
    void setElement(@Nullable Element element);

    @Nullable
    Element getElement();

    void setAmount(float amount);

    float getAmount();
}
