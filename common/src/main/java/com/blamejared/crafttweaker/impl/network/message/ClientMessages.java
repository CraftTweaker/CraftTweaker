package com.blamejared.crafttweaker.impl.network.message;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public enum ClientMessages {
    COPY(MessageCopy.class, MessageCopy.ID, buf -> new MessageCopy(buf.readUtf()));
    
    private final Class<?> messageClass;
    private final ResourceLocation id;
    private final Function<FriendlyByteBuf, IMessage> messageFactory;
    
    ClientMessages(Class<?> messageClass, String path, Function<FriendlyByteBuf, IMessage> messageFactory) {
        
        this(messageClass, CraftTweakerConstants.rl(path), messageFactory);
    }
    
    ClientMessages(Class<?> messageClass, ResourceLocation id, Function<FriendlyByteBuf, IMessage> messageFactory) {
        
        this.messageClass = messageClass;
        this.id = id;
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
    
    public CustomPacketPayload getCustomPacketPayload(FriendlyByteBuf buf) {
        
        return getMessageFactory().apply(buf);
    }
}
