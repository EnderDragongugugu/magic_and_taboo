package net.magic_and_taboo.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.impl.networking.client.ClientNetworkingImpl;
import net.magic_and_taboo.MagicAndTabooMod;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;

public class MATPacketHandler {
    public static final Identifier TEST = new Identifier(MagicAndTabooMod.MOD_ID,"114514");
//    ClientPlayNetworking.registerGlobalReceiver(MATPacketHandler.class,TEST) {
//        return ClientNetworkingImpl.PLAY.registerGlobalReceiver(channelName, channelHandler);
//    }
}