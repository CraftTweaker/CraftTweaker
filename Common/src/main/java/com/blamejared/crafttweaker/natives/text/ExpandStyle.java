package com.blamejared.crafttweaker.natives.text;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/text/Style")
@NativeTypeRegistration(value = Style.class, zenCodeName = "crafttweaker.api.text.Style")
public class ExpandStyle {
    
    @ZenCodeType.StaticExpansionMethod
    public static Style empty() {
        
        return Style.EMPTY;
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Nullable
    @ZenCodeType.Getter("color")
    public static TextColor getColor(Style internal) {
        
        return internal.getColor();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("bold")
    public static boolean isBold(Style internal) {
        
        return internal.isBold();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("italic")
    public static boolean isItalic(Style internal) {
        
        return internal.isItalic();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("strikethrough")
    public static boolean isStrikethrough(Style internal) {
        
        return internal.isStrikethrough();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("underlined")
    public static boolean isUnderlined(Style internal) {
        
        return internal.isUnderlined();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("obfucated")
    public static boolean isObfuscated(Style internal) {
        
        return internal.isObfuscated();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("empty")
    public static boolean isEmpty(Style internal) {
        
        return internal.isEmpty();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Nullable
    @ZenCodeType.Getter("insertion")
    public static String getInsertion(Style internal) {
        
        return internal.getInsertion();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("font")
    public static ResourceLocation getFont(Style internal) {
        
        return internal.getFont();
    }
    
    @ZenCodeType.Method
    public static Style withColor(Style internal, @ZenCodeType.Nullable TextColor textColor) {
        
        return internal.withColor(textColor);
    }
    
    @ZenCodeType.Method
    public static Style withColor(Style internal, @ZenCodeType.Nullable ChatFormatting formatting) {
        
        return internal.withColor(formatting);
    }
    
    @ZenCodeType.Method
    public static Style withColor(Style internal, int color) {
        
        return internal.withColor(color);
    }
    
    @ZenCodeType.Method
    public static Style withBold(Style internal, @ZenCodeType.Nullable Boolean value) {
        
        return internal.withBold(value);
    }
    
    @ZenCodeType.Method
    public static Style setBold(Style internal) {
        
        return withBold(internal, true);
    }
    
    @ZenCodeType.Method
    public static Style withItalic(Style internal, @ZenCodeType.Nullable Boolean value) {
        
        return internal.withItalic(value);
    }
    
    @ZenCodeType.Method
    public static Style setItalic(Style internal) {
        
        return withItalic(internal, true);
    }
    
    @ZenCodeType.Method
    public static Style withUnderlined(Style internal, @ZenCodeType.Nullable Boolean value) {
        
        return internal.withUnderlined(value);
    }
    
    @ZenCodeType.Method
    public static Style setUnderlined(Style internal) {
        
        return withUnderlined(internal, true);
    }
    
    @ZenCodeType.Method
    public static Style withStrikethrough(Style internal, @ZenCodeType.Nullable Boolean value) {
        
        return internal.withStrikethrough(value);
    }
    
    @ZenCodeType.Method
    public static Style setStrikethrough(Style internal) {
        
        return withStrikethrough(internal, true);
    }
    
    @ZenCodeType.Method
    public static Style withObfuscated(Style internal, @ZenCodeType.Nullable Boolean value) {
        
        return internal.withObfuscated(value);
    }
    
    @ZenCodeType.Method
    public static Style setObfuscated(Style internal) {
        
        return withObfuscated(internal, true);
    }
    
    @ZenCodeType.Method
    public static Style withInsertion(Style internal, @ZenCodeType.Nullable String content) {
        
        return internal.withInsertion(content);
    }
    
    @ZenCodeType.Method
    public static Style withFont(Style internal, @ZenCodeType.Nullable ResourceLocation fontId) {
        
        return internal.withFont(fontId);
    }
    
    @ZenCodeType.Method
    public static Style applyFormat(Style internal, ChatFormatting format) {
        
        return internal.applyFormat(format);
    }
    
    @ZenCodeType.Method
    public static Style applyLegacyFormat(Style internal, ChatFormatting format) {
        
        return internal.applyLegacyFormat(format);
    }
    
    @ZenCodeType.Method
    public static Style applyFormats(Style internal, ChatFormatting... formattings) {
        
        return internal.applyFormats(formattings);
    }
    
    @ZenCodeType.Method
    public static Style applyTo(Style internal, Style style) {
        
        return internal.applyTo(style);
    }
    
}
