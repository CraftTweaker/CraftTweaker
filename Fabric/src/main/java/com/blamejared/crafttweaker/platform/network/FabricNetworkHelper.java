package com.blamejared.crafttweaker.platform.network;

import com.blamejared.crafttweaker.impl.network.message.ClientMessages;
import com.blamejared.crafttweaker.impl.network.message.MessageCopy;
import com.blamejared.crafttweaker.impl.network.message.MessageOpen;
import com.blamejared.crafttweaker.platform.services.INetworkHelper;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public class FabricNetworkHelper implements INetworkHelper {
    
    @Override
    public void sendCopyMessage(ServerPlayer target, MessageCopy message) {
        
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        message.serialize(buf);
        ServerPlayNetworking.send(target, ClientMessages.COPY.getId(), buf);
    }
    
    @Override
    public void sendOpenMessage(ServerPlayer target, MessageOpen message) {
        
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        message.serialize(buf);
        ServerPlayNetworking.send(target, ClientMessages.OPEN.getId(), buf);
    }
    
}
