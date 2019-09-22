package com.blamejared.crafttweaker.api.zencode.expands;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.impl.data.ShortData;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Expansion("short")
public class ExpandShort {
    
    @ZenCodeType.Caster(implicit = true)
    public static IData asData(short value) {
        return new ShortData(value);
    }
    
}
