package enderdragon.magic_and_taboo.util;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class ItemGroup implements CreativeModeTab.DisplayItemsGenerator {
    public static ItemGroup of() {
        return new ItemGroup(Collections.emptyList());
    }

    public static ItemGroup of(CreativeModeTab.DisplayItemsGenerator... children) {
        return new ItemGroup(List.of(children));
    }

    protected final ObjectArrayList<RegistryObject<? extends ItemLike>> items = new ObjectArrayList<>();
    public final List<CreativeModeTab.DisplayItemsGenerator> children;

    protected ItemGroup(List<CreativeModeTab.DisplayItemsGenerator> children) {
        this.children = children;
    }

    public <T extends ItemLike> RegistryObject<T> register(DeferredRegister<? super T> registry, String name, Supplier<T> supplier) {
        var holder = registry.register(name, supplier);
        this.items.add(holder);
        return holder;
    }

    public void add(RegistryObject<? extends ItemLike> item) {
        this.items.add(item);
    }

    @Override
    public void accept(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output output) {
        for (var item : this.items) {
            if (item.isPresent()) {
                output.accept(item.get());
            }
        }
        for (var item : this.children) {
            item.accept(parameters, output);
        }
    }

    public void register(DeferredRegister<CreativeModeTab> registry, String name, String title, Supplier<ItemStack> icon) {
        registry.register(name, () -> CreativeModeTab.builder().title(Component.translatable(title)).icon(icon).displayItems(this).build());
    }
}
