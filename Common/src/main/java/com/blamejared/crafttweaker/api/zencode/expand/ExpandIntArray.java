package com.blamejared.crafttweaker.api.zencode.expand;


import com.blamejared.crafttweaker.api.data.IntArrayData;
import com.blamejared.crafttweaker.api.data.base.IData;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Expansion("int[]")
public class ExpandIntArray {
    
    @ZenCodeType.Caster(implicit = true)
    public static IData asData(int[] value) {
        
        return new IntArrayData(value);
    }
    
}
