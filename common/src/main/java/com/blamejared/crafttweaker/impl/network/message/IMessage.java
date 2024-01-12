package com.blamejared.crafttweaker.impl.network.message;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public interface IMessage extends CustomPacketPayload {
    
    void write(FriendlyByteBuf buf);
    
    void handle();
    
    ResourceLocation id();
    
}
