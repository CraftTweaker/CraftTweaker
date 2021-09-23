package com.blamejared.crafttweaker.api.zencode.expands;

import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.impl.data.IntArrayData;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Expansion("int[]")
public class ExpandIntArray {
    
    @ZenCodeType.Caster(implicit = true)
    public static IData asData(int[] value) {
        
        return new IntArrayData(value);
    }
    
}
