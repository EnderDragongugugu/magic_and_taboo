package net.magic_and_taboo.block.entity;

import net.magic_and_taboo.MagicAndTabooMod;
import net.magic_and_taboo.init.MATBlockEntities;
import net.magic_and_taboo.init.MATBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;

public class SpyglassSextantEntity extends BlockEntity {
    public SpyglassSextantEntity( BlockPos pos, BlockState state) {
        super(MATBlockEntities.SPYGLASS_SEXTANT_BLOCK_ENTITY, pos, state);
    }
    private final Inventory inventory = new Inventory() {
        @Override
        public int size() {
            return 3;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public ItemStack getStack(int slot) {
            return null;
        }

        @Override
        public ItemStack removeStack(int slot, int amount) {
            return null;
        }

        @Override
        public ItemStack removeStack(int slot) {
            return null;
        }

        @Override
        public void setStack(int slot, ItemStack stack) {

        }

        @Override
        public void markDirty() {

        }

        @Override
        public boolean canPlayerUse(PlayerEntity player) {
            return true;
        }

        @Override
        public void clear() {

        }
    };
}
