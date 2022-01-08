package com.blamejared.crafttweaker.natives.advancement;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.FrameType;
import net.minecraft.network.chat.Component;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/advancement/FrameType")
@NativeTypeRegistration(value = FrameType.class, zenCodeName = "crafttweaker.api.advancement.FrameType")
@BracketEnum("minecraft:advancement/frametype")
public class ExpandFrameType {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("name")
    public static String getName(FrameType internal) {
        
        return internal.getName();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("texture")
    public static int getTexture(FrameType internal) {
        
        return internal.getTexture();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("chatColor")
    public static ChatFormatting getChatColor(FrameType internal) {
        
        return internal.getChatColor();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("displayName")
    public static Component getDisplayName(FrameType internal) {
        
        return internal.getDisplayName();
    }
    
}
