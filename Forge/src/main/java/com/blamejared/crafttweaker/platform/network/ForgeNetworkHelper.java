package com.blamejared.crafttweaker.platform.network;

import com.blamejared.crafttweaker.impl.network.PacketHandler;
import com.blamejared.crafttweaker.impl.network.message.MessageCopy;
import com.blamejared.crafttweaker.platform.services.INetworkHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.PacketDistributor;

public class ForgeNetworkHelper implements INetworkHelper {
    
    @Override
    public void sendCopyMessage(ServerPlayer target, MessageCopy message) {
        
        PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> target), message);
    }
    
}
