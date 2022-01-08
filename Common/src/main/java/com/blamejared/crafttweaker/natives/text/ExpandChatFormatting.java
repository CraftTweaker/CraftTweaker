package com.blamejared.crafttweaker.natives.text;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Style;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/text/ChatFormatting")
@NativeTypeRegistration(value = ChatFormatting.class, zenCodeName = "crafttweaker.api.text.ChatFormatting")
@BracketEnum("minecraft:formatting")
public class ExpandChatFormatting {
    
    @ZenCodeType.Method
    public static char getChar(ChatFormatting internal) {
        
        return internal.getChar();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("id")
    public static int getId(ChatFormatting internal) {
        
        return internal.getId();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isFormat")
    public static boolean isFormat(ChatFormatting internal) {
        
        return internal.isFormat();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isColor")
    public static boolean isColor(ChatFormatting internal) {
        
        return internal.isColor();
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Method("color")
    public static Integer getColor(ChatFormatting internal) {
        
        return internal.getColor();
    }
    
    @ZenCodeType.Method
    public static String getName(ChatFormatting internal) {
        
        return internal.getName();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Caster(implicit = true)
    public static String toString(ChatFormatting internal) {
        
        return internal.toString();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Caster(implicit = true)
    public static Style asStyle(ChatFormatting internal) {
        
        return Style.EMPTY.applyFormat(internal);
    }
    
}
