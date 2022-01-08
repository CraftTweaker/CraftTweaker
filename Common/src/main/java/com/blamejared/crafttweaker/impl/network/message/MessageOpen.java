package com.blamejared.crafttweaker.impl.network.message;

import net.minecraft.Util;
import net.minecraft.network.FriendlyByteBuf;

public record MessageOpen(String path) implements IMessage<MessageOpen> {
    
    @Override
    public void serialize(FriendlyByteBuf buf) {
        
        buf.writeUtf(path());
    }
    
    @Override
    public void handle() {
        
        Util.getPlatform().openUri(path());
    }
    
    public static MessageOpen deserialize(FriendlyByteBuf buf) {
        
        return new MessageOpen(buf.readUtf());
    }
    
}
