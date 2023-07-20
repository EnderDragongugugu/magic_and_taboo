package net.magic_and_taboo.item;

import net.magic_and_taboo.util.MATWorldTime;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.world.World;

public class TimeItem extends Item {
    public TimeItem(Settings settings) {
        super(settings);
    }
    
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (world.isClient && entity instanceof PlayerEntity) {
            MATWorldTime.update(world, false);
            StringBuilder builder = new StringBuilder("当前时间：")
                    .append(MATWorldTime.HOURS)
                    .append(':')
                    .append(MATWorldTime.MINUTES);
            if (MATWorldTime.MINUTES < 10) {
                builder.insert(builder.length() - 1, '0');
            }
            ((PlayerEntity) entity).sendMessage(new LiteralText(builder.toString()), true);
            return;
        }
        if (entity instanceof PlayerEntity player) {
            //ServerPlayNetworking.send((ServerPlayerEntity) player, MATPacketHandler.TEST, PacketByteBufs.empty());
        }
    }

}
