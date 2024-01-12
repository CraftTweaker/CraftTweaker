package com.blamejared.crafttweaker.impl.network.message;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record MessageCopy(String toCopy) implements IMessage {
    
    public static final ResourceLocation ID = CraftTweakerConstants.rl("copy");
    @Override
    public void write(FriendlyByteBuf buf) {
        
        buf.writeUtf(toCopy());
    }
    
    @Override
    public void handle() {
        
        Minecraft.getInstance().keyboardHandler.setClipboard(toCopy());
    }
    
    @Override
    public ResourceLocation id() {
        
        return ID;
    }
    
}
