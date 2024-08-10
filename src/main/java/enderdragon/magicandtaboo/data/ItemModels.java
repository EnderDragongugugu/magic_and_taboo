package enderdragon.magicandtaboo.data;

import enderdragon.magicandtaboo.MagicAndTabooMod;
import enderdragon.magicandtaboo.item.MATItem;
import enderdragon.magicandtaboo.util.DatagenUtil;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemModels extends ItemModelProvider {
    public static final String GENERATED = "item/generated";
    public static final String HANDHELD = "item/handheld";
    public ItemModels(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MagicAndTabooMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        blockItemGeneratedModel(MATItem.MERCURY_ORE.get(),"");
    }
    public void blockItemGeneratedModel(Item item,String suffix) {
        String name = DatagenUtil.getItemNanme(item);
        withExistingParent(name,DatagenUtil.resourceBlock(name + suffix));
        MagicAndTabooMod.LOGGER.debug("哈机密哈机密哈机密哈机密哈机密哈机密哈机密哈机密哈机密哈机密哈机密哈机密哈机密哈机密哈机密");
    }
    public void itemGeneratedModel(Item item, ResourceLocation texture) {
        withExistingParent(DatagenUtil.getItemNanme(item), GENERATED).texture("layer0", texture);
    }

}
