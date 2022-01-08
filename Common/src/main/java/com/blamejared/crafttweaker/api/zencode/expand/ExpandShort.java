package com.blamejared.crafttweaker.api.zencode.expand;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.ShortData;
import com.blamejared.crafttweaker.api.data.base.IData;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Expansion("short")
public class ExpandShort {
    
    @ZenCodeType.Caster(implicit = true)
    public static IData asData(short value) {
        
        return new ShortData(value);
    }
    
}
