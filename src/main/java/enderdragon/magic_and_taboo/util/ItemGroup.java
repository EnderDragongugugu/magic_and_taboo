package enderdragon.magic_and_taboo.util;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ItemGroup implements CreativeModeTab.DisplayItemsGenerator {
    protected final ObjectArrayList<RegistryObject<? extends ItemLike>> items = new ObjectArrayList<>();
    protected final Supplier<? extends ItemLike> icon;
    protected final Component title;

    public ItemGroup(DeferredRegister<CreativeModeTab> registry, String name, String title, Supplier<? extends ItemLike> icon) {
        this.icon = icon;
        this.title = Component.translatable(title);
        registry.register(name, this::makeTab);
    }

    public <T extends ItemLike> RegistryObject<T> register(DeferredRegister<? super T> registry, String name, Supplier<T> supplier) {
        var holder = registry.register(name, supplier);
        this.items.add(holder);
        return holder;
    }

    public void add(RegistryObject<? extends ItemLike> item) {
        this.items.add(item);
    }

    public ItemStack getIcon() {
        return new ItemStack(this.icon.get());
    }

    public CreativeModeTab makeTab() {
        return CreativeModeTab.builder().title(this.title).icon(this::getIcon).displayItems(this).build();
    }

    @Override
    public void accept(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output output) {
        for (var item : this.items) {
            if (item.isPresent()) {
                output.accept(item.get());
            }
        }
    }
}
