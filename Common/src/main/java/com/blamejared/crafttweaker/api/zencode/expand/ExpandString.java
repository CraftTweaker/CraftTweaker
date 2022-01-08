package com.blamejared.crafttweaker.api.zencode.expand;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.StringData;
import com.blamejared.crafttweaker.api.data.base.IData;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Expansion("string")
public class ExpandString {
    
    @ZenCodeType.Caster(implicit = true)
    public static IData asData(String value) {
        
        return new StringData(value);
    }
    
    @ZenCodeType.Caster()
    public static int asInt(String value) {
        
        try {
            return Integer.parseInt(value);
        } catch(NumberFormatException e) {
            throw new NumberFormatException("\"%s\" is not an integer!".formatted(value));
        }
    }
    
    @ZenCodeType.Caster()
    public static double asDouble(String value) {
        
        try {
            return Double.parseDouble(value);
        } catch(NumberFormatException e) {
            throw new NumberFormatException("\"%s\" is not a double!".formatted(value));
        }
    }
    
    @ZenCodeType.Caster(implicit = true)
    public static TextComponent asTextComponent(String value) {
        
        return new TextComponent(value);
    }
    
    @ZenCodeType.Caster(implicit = true)
    public static Component asComponent(String value) {
        
        return asTextComponent(value);
    }
    
}
