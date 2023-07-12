package net.magic_and_taboo.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class StarMapItems extends Item{
    private final String str;
    public StarMapItems(Settings settings,String str) {
        super(settings);
        this.str = str;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(new TranslatableText(str));
    }
    @Override
    public String getTranslationKey() {
        return "magic_and_taboo.star_map";
    }
}
