package enderdragon.magic_and_taboo.item;

import enderdragon.magic_and_taboo.block.entity.WorkHubBlockEntity;
import enderdragon.magic_and_taboo.client.gui.WorkHubTooltip;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.Optional;

public class WorkHubBlockItem extends BlockItem {
    public WorkHubBlockItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    public ItemStack getItemStack(ItemStack itemStack) {
        var blockEntityData = getBlockEntityData(itemStack);
        if (blockEntityData != null && blockEntityData.contains("Items", Tag.TAG_LIST)) {
            ListTag list = blockEntityData.getList("Items", Tag.TAG_COMPOUND);
            NonNullList<ItemStack> stacks = NonNullList.withSize(WorkHubBlockEntity.MAX_SIZE, ItemStack.EMPTY);
            for (int i = 0; i < list.size(); i++) {
                CompoundTag itemTags = list.getCompound(i);
                int slot = itemTags.getInt("Slot");
                if (slot >= 0) {
                    stacks.set(slot, ItemStack.of(itemTags));
                }
            }
            return stacks.get(8);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean isBarVisible(ItemStack pStack) {
        return !getItemStack(pStack).isEmpty();
    }

    @Override
    public int getBarWidth(ItemStack pStack) {
        var itemStack = getItemStack(pStack);
        if (!itemStack.isEmpty()) {
            return Math.min(Math.round((float) itemStack.getCount() * 13.0F / (float) itemStack.getMaxStackSize()), 13);
        }
        return super.getBarWidth(pStack);
    }

    @Override
    public int getBarColor(ItemStack pStack) {
        var itemStack = getItemStack(pStack);
        if (!itemStack.isEmpty()) {
            float f = Math.max(0.0F, ((float) itemStack.getCount()) / itemStack.getMaxStackSize());
            return Mth.hsvToRgb(f / 3.0F, 1.0F, 1.0F);
        }
        return super.getBarColor(pStack);
    }

    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack pStack) {
        return Optional.of(new WorkHubTooltip.WorkHubTooltipComponent(getItemStack(pStack)));
    }

}
