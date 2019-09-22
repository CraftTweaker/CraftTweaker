package com.blamejared.crafttweaker.api.zencode.expands;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.impl.data.LongData;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Expansion("long")
public class ExpandLong {
    
    @ZenCodeType.Caster(implicit = true)
    public static IData asData(long value) {
        return new LongData(value);
    }
    
}
