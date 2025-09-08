package enderdragon.magic_and_taboo.capability;

import enderdragon.magic_and_taboo.registry.Element;
import it.unimi.dsi.fastutil.objects.Reference2FloatMap;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import org.jetbrains.annotations.Nullable;

@AutoRegisterCapability
public interface ElementStorage extends ElementSource {
    @Nullable Element getMajorElement();

    void setConcentration(Element element, float Concentration);

    /// @param concentrations MUST BE **MUTABLE**!
    void setConcentrations(Reference2FloatMap<Element> concentrations);
}
