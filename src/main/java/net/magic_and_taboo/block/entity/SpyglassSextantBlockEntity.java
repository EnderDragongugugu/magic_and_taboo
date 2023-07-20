package net.magic_and_taboo.block.entity;

import net.magic_and_taboo.screen.SpyglassSextantScreenHandler;
import net.magic_and_taboo.init.MATBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public class SpyglassSextantBlockEntity extends LootableContainerBlockEntity {
    protected static final String TRANSLATION_KEY = "block.magic_and_taboo.spyglass_sextant";
    DefaultedList<ItemStack> inv = DefaultedList.ofSize(3, ItemStack.EMPTY);

    public SpyglassSextantBlockEntity(BlockPos pos, BlockState state) {
        super(MATBlockEntities.SPYGLASS_SEXTANT_BLOCK_ENTITY, pos, state);
    }

    protected DefaultedList<ItemStack> getInv() {
        return this.inv;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, this.inv);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, this.inv);
//        return nbt;
    }

    @Override
    protected Text getContainerName() {
        return new TranslatableText(TRANSLATION_KEY);
    }

    @Override
    protected DefaultedList<ItemStack> getInvStackList() {
        return this.inv;
    }

    @Override
    protected void setInvStackList(DefaultedList<ItemStack> list) {
        this.inv = list;
    }


    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new SpyglassSextantScreenHandler(syncId, playerInventory, this);
    }

    @Override
    public int size() {
        return 3;
    }
}
//public class SpyglassSextantEntity extends BlockEntity implements NamedScreenHandlerFactory {
//    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(9, ItemStack.EMPTY);
//
//
//    public SpyglassSextantEntity( BlockPos pos, BlockState state) {
//        super(MATBlockEntities.SPYGLASS_SEXTANT_BLOCK_ENTITY, pos, state);
//    }
//
//
//    // 从 ImplementedInventory 接口
//
//    public DefaultedList<ItemStack> getItems() {
//        return inventory;
//
//    }
//    @Override
//    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
//        return new SpyglassSextantUIScreenHandler(syncId, playerInventory, (Inventory) this);
//    }
//
//
//
//    @Override
//    public void readNbt(NbtCompound nbt) {
//        super.readNbt(nbt);
//        Inventories.readNbt(nbt, this.inventory);
//    }
//
//    @Override
//    public void writeNbt(NbtCompound nbt) {
//        super.writeNbt(nbt);
//        Inventories.writeNbt(nbt, this.inventory);
////        return nbt;
//    }
//
//    @Override
//    public Text getDisplayName() {
//        return null;
//    }
//}
