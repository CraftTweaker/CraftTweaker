package com.blamejared.crafttweaker.platform.services;

import com.blamejared.crafttweaker.impl.network.message.MessageCopy;
import net.minecraft.server.level.ServerPlayer;

public interface INetworkHelper {
    
    void sendCopyMessage(ServerPlayer target, MessageCopy message);
    
}
