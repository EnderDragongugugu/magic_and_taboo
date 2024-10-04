package enderdragon.magicandtaboo.inventory.menu;

import enderdragon.magicandtaboo.block.entity.FederationWorkstationBlockEntity;
import enderdragon.magicandtaboo.init.MATMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.Nullable;

public class FederationWorkstationMenu extends AbstractContainerMenu {

    public final FederationWorkstationBlockEntity blockEntity;
//    public final ItemStackHandler inventory;
    private final ContainerData data;

    protected final Level level;
    public FederationWorkstationMenu(final int windowId, final Inventory playerInventory, final FriendlyByteBuf data) {
        this(windowId, playerInventory, getTileEntity(playerInventory, data),new SimpleContainerData(4));
    }
    public FederationWorkstationMenu(int id, Inventory playerInventory,FederationWorkstationBlockEntity blockEntity, ContainerData containerData) {
        super(MATMenuTypes.FWMENU.get(), id);
        this.blockEntity = blockEntity;
//        this.inventory = blockEntity.getInventory();
        this.data = containerData;
        this.level = playerInventory.player.level();
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 3, 62 + j * 18, 17 + i * 18));
            }
        }
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    private static FederationWorkstationBlockEntity getTileEntity(final Inventory playerInventory, final FriendlyByteBuf data) {
        final BlockEntity tileAtPos = playerInventory.player.level().getBlockEntity(data.readBlockPos());
        if (tileAtPos instanceof FederationWorkstationBlockEntity) {
            return (FederationWorkstationBlockEntity) tileAtPos;
        }
        throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
    }
}
