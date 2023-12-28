package com.blamejared.crafttweaker.impl.network.message;

import net.minecraft.network.FriendlyByteBuf;

public interface IMessage<T extends IMessage<?>> {
    
    void serialize(FriendlyByteBuf buf);
    
    void handle();
    
}
