package com.blamejared.crafttweaker.impl.network.message;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public enum ClientMessages {
    COPY(MessageCopy.class, "copy", buf -> new MessageCopy(buf.readUtf()));
    
    private final Class<?> messageClass;
    private final ResourceLocation id;
    private final Function<FriendlyByteBuf, IMessage> messageFactory;
    
    ClientMessages(Class<?> messageClass, String path, Function<FriendlyByteBuf, IMessage> messageFactory) {
        
        this.messageClass = messageClass;
        this.id = CraftTweakerConstants.rl(path);
        this.messageFactory = messageFactory;
    }
    
    public ResourceLocation getId() {
        
        return id;
    }
    
    public Class getMessageClass() {
        
        return messageClass;
    }
    
    public Function<FriendlyByteBuf, IMessage> getMessageFactory() {
        
        return messageFactory;
    }
}
