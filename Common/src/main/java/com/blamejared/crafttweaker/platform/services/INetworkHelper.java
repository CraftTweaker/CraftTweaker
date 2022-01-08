package com.blamejared.crafttweaker.platform.services;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.impl.network.message.MessageCopy;
import com.blamejared.crafttweaker.impl.network.message.MessageOpen;
import net.minecraft.server.level.ServerPlayer;

public interface INetworkHelper {
    
    void sendCopyMessage(ServerPlayer target, MessageCopy message);
    void sendOpenMessage(ServerPlayer target, MessageOpen message);
    
    default String getNetworkVersion(){
        return CraftTweakerConstants.NETWORK_VERSION;
    }
    
}
