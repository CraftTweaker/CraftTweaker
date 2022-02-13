package com.blamejared.crafttweaker.impl.network.message;

import com.blamejared.crafttweaker.CraftTweakerCommon;
import net.minecraft.network.FriendlyByteBuf;

public record MessageOpen(String path) implements IMessage<MessageOpen> {
    
    @Override
    public void serialize(FriendlyByteBuf buf) {
        
        buf.writeUtf(path());
    }
    
    @Override
    public void handle() {
        
        CraftTweakerCommon.LOG.info("Unable to open {}!", path());
    }
    
    public static MessageOpen deserialize(FriendlyByteBuf buf) {
        
        return new MessageOpen(buf.readUtf());
    }
    
}
