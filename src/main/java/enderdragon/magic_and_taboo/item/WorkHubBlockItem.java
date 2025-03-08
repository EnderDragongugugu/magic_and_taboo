package enderdragon.magic_and_taboo.item;

import enderdragon.magic_and_taboo.block.entity.WorkHubBlockEntity;
import enderdragon.magic_and_taboo.capability.WorkHubResult;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.wrapper.EmptyHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class WorkHubBlockItem extends BlockItem {
    public WorkHubBlockItem(Block block, Properties props) {
        super(block, props);
    }

    @Override
    protected boolean updateCustomBlockEntityTag(BlockPos pos, Level level, @Nullable Player player, ItemStack stack, BlockState state) {
        boolean flag = super.updateCustomBlockEntityTag(pos, level, player, stack, state);
        ItemStack content;
        var handler = stack.getCapability(ForgeCapabilities.ITEM_HANDLER).orElse(EmptyHandler.INSTANCE);
        if (handler.getSlots() > 0 && !(content = handler.getStackInSlot(0)).isEmpty() && level.getBlockEntity(pos) instanceof WorkHubBlockEntity hub) {
            hub.setStackInSlot(8, content);
        }
        return flag;
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        var handler = stack.getCapability(ForgeCapabilities.ITEM_HANDLER).orElse(EmptyHandler.INSTANCE);
        return handler.getSlots() > 0 && !handler.getStackInSlot(0).isEmpty();
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        var handler = stack.getCapability(ForgeCapabilities.ITEM_HANDLER).orElse(EmptyHandler.INSTANCE);
        if (handler.getSlots() > 0) {
            var content = handler.getStackInSlot(0);
            if (!content.isEmpty()) return Math.min(Math.round(
                    content.getCount() * 13.0F / content.getMaxStackSize()
            ), 13);
        }
        return 0;
    }

    @Override
    public int getBarColor(ItemStack stack) {
        var handler = stack.getCapability(ForgeCapabilities.ITEM_HANDLER).orElse(EmptyHandler.INSTANCE);
        if (handler.getSlots() > 0) {
            var content = handler.getStackInSlot(0);
            if (!content.isEmpty()) return Mth.hsvToRgb(
                    Math.max(0.0F, content.getCount() / 3.0F / content.getMaxStackSize()),
                    1.0F,
                    1.0F
            );
        }
        return 0xFFFFFF;
    }

    @Override
    public @NotNull Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
        return stack.getCapability(ForgeCapabilities.ITEM_HANDLER)
                .<TooltipComponent>cast() // 泛型擦除魅力时刻
                .filter(TooltipComponent.class::isInstance);
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new WorkHubResult(nbt == null || !nbt.contains("Result")
                ? ItemStack.EMPTY
                : ItemStack.of(nbt.getCompound("Result"))
        );
    }
}
