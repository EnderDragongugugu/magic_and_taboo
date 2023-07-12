package net.magic_and_taboo.item;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.magic_and_taboo.network.MATPacketHandler;
import net.magic_and_taboo.util.MATWorldTime;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

public class TimeItem extends Item {
    private static long time = 0;
    public TimeItem(Settings settings) {
        super(settings);
    }
    
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(world.isClient()) return;
        if(entity instanceof PlayerEntity player){
            MATWorldTime worldTime = new MATWorldTime(world);
//            player.sendMessage(new TranslatableText("当前时间:" + (worldTime.hours > 17 ? worldTime.hours - 18 : worldTime.hours + 6 ) + ':' + (worldTime.mins < 10 ? '0' + worldTime.mins : worldTime.mins)),true);
            ServerPlayNetworking.send((ServerPlayerEntity) player, MATPacketHandler.TEST, PacketByteBufs.empty());
        }
    }

}
