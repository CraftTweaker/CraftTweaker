package com.blamejared.crafttweaker.impl.network.message;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;

public record MessageCopy(String toCopy) implements IMessage<MessageCopy> {
    
    @Override
    public void serialize(FriendlyByteBuf buf) {
        
        buf.writeUtf(toCopy());
    }
    
    @Override
    public void handle() {
        
        Minecraft.getInstance().keyboardHandler.setClipboard(toCopy());
    }
    
}
