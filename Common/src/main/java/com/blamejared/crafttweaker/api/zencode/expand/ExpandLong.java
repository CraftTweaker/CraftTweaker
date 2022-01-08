package com.blamejared.crafttweaker.api.zencode.expand;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.LongData;
import com.blamejared.crafttweaker.api.data.base.IData;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Expansion("long")
public class ExpandLong {
    
    @ZenCodeType.Caster(implicit = true)
    public static IData asData(long value) {
        
        return new LongData(value);
    }
    
}
