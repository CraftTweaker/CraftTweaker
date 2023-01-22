package com.blamejared.crafttweaker.natives.text;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;
import java.util.Optional;

@ZenRegister
@Document("vanilla/api/text/Component")
@NativeTypeRegistration(value = Component.class, zenCodeName = "crafttweaker.api.text.Component")
public class ExpandComponent {
    
    @ZenCodeType.StaticExpansionMethod
    public static Component nullToEmpty(@ZenCodeType.Nullable String content) {
        
        return Component.nullToEmpty(content);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static MutableComponent literal(String content) {
        
        return Component.literal(content);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static MutableComponent translatable(String content, String... args) {
        
        return Component.translatable(content, (Object[]) args);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static MutableComponent translatable(String content, Component... args) {
        
        return Component.translatable(content, (Object[]) args);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static MutableComponent empty() {
        
        return Component.empty();
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static MutableComponent keybind(String name) {
        
        return Component.keybind(name);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static MutableComponent score(String name, String objective) {
        
        return Component.score(name, objective);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static MutableComponent selector(String pattern, @ZenCodeType.Nullable @ZenCodeType.Optional Component separator) {
        
        return Component.selector(pattern, Optional.ofNullable(separator));
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("style")
    public static Style getStyle(Component internal) {
        
        return internal.getStyle();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("contents")
    public static ComponentContents getContents(Component internal) {
        
        return internal.getContents();
    }
    
    @ZenCodeType.Method
    public static String getString(Component internal, int maxLength) {
        
        return internal.getString(maxLength);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("siblings")
    public static List<Component> getSiblings(Component internal) {
        
        return internal.getSiblings();
    }
    
    @ZenCodeType.Method
    public static MutableComponent plainCopy(Component internal) {
        
        return internal.plainCopy();
    }
    
    @ZenCodeType.Method
    public static MutableComponent copy(Component internal) {
        
        return internal.copy();
    }
    
}
