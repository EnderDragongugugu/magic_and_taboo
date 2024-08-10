package enderdragon.magicandtaboo.item;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.awt.datatransfer.Transferable;
import java.util.List;

public class TooltipBlockItem extends BlockItem {
    public String tooltip;

    public TooltipBlockItem(Block pBlock, Properties pProperties,String tooltip) {
        super(pBlock, pProperties);
        this.tooltip = tooltip;
    }


    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable(tooltip));
    }
}
