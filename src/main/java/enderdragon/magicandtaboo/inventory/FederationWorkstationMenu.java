package enderdragon.magicandtaboo.inventory;

import enderdragon.magicandtaboo.block.entity.FederationWorkstationBlockEntity;
import enderdragon.magicandtaboo.init.MATMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class FederationWorkstationMenu extends AbstractContainerMenu {
    @Nullable
    public static FederationWorkstationMenu formPacket(final int id, final Inventory inventory, final @Nullable FriendlyByteBuf buffer) {
        if (buffer == null) return null;// vanilla
        if (inventory.player.level().getBlockEntity(buffer.readBlockPos()) instanceof FederationWorkstationBlockEntity workstation) {
            try {
                workstation.setTime(buffer.readVarInt());
                workstation.setTotalTime(buffer.readVarInt());
            } catch (Exception ignored) {
                workstation.setTime(0);
                workstation.setTotalTime(0);
            }
            return new FederationWorkstationMenu(id, inventory, workstation);
        }
        return null;
    }

    public final FederationWorkstationBlockEntity workstation;
    //    public final ItemStackHandler inventory;

    protected final Level level;

    public FederationWorkstationMenu(int id, Inventory playerInventory, FederationWorkstationBlockEntity workstation) {
        super(MATMenuTypes.FEDERATION_WORKSTATION.get(), id);
        this.workstation = workstation;
//        this.inventory = blockEntity.getInventory();
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
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
