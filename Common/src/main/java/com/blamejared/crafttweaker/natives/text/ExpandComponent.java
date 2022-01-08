package com.blamejared.crafttweaker.natives.text;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;

@ZenRegister
@Document("vanilla/api/text/Component")
@NativeTypeRegistration(value = Component.class, zenCodeName = "crafttweaker.api.text.Component")
public class ExpandComponent {
    
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("style")
    public static Style getStyle(Component internal) {
        
        return internal.getStyle();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("contents")
    public static String getContents(Component internal) {
        
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
