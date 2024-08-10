package enderdragon.magicandtaboo.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TooltipBlockItem extends BlockItem {
    public String tooltip;

    public TooltipBlockItem(Block block, Properties props, String tooltip) {
        super(block, props);
        this.tooltip = tooltip;
    }


    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltips, TooltipFlag flag) {
        tooltips.add(Component.translatable(tooltip));
    }
}
