package com.blamejared.crafttweaker.api.zencode.expands;

import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.impl.data.LongArrayData;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Expansion("long[]")
public class ExpandLongArray {
    
    @ZenCodeType.Caster(implicit = true)
    public static IData asData(long[] value) {
        
        return new LongArrayData(value);
    }
    
}
