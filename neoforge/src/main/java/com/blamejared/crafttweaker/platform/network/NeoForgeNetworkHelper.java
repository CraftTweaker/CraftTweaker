package com.blamejared.crafttweaker.platform.network;

import com.blamejared.crafttweaker.impl.network.message.MessageCopy;
import com.blamejared.crafttweaker.platform.services.INetworkHelper;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;

public class NeoForgeNetworkHelper implements INetworkHelper {
    
    @Override
    public void sendCopyMessage(ServerPlayer target, MessageCopy message) {
        
        PacketDistributor.PLAYER.with(target).send(message);
    }
    
}
