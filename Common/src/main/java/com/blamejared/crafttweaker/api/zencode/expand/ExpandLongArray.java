package com.blamejared.crafttweaker.api.zencode.expand;


import com.blamejared.crafttweaker.api.data.LongArrayData;
import com.blamejared.crafttweaker.api.data.IData;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Expansion("long[]")
public class ExpandLongArray {
    
    @ZenCodeType.Caster(implicit = true)
    public static IData asData(long[] value) {
        
        return new LongArrayData(value);
    }
    
}
