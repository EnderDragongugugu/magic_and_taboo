package enderdragon.magicandtaboo.data;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import static enderdragon.magicandtaboo.MagicAndTabooMod.MOD_ID;

public class MATItemModelProvider extends ItemModelProvider {
    public MATItemModelProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, MOD_ID, helper);
    }

    @Override
    protected void registerModels() {

    }

    public ItemModelBuilder handheldItem(RegistryObject<? extends Item> item) {
        var id = item.getId();
        return this.getBuilder(id.toString())
                .parent(new ModelFile.UncheckedModelFile("item/handheld"))
                .texture("layer0", id.withPrefix("item/"));
    }
}
