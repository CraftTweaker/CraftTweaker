package com.blamejared.crafttweaker.api.zencode.expand;


import com.blamejared.crafttweaker.api.data.ByteArrayData;
import com.blamejared.crafttweaker.api.data.base.IData;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Expansion("byte[]")
public class ExpandByteArray {
    
    @ZenCodeType.Caster(implicit = true)
    public static IData asData(byte[] value) {
        
        return new ByteArrayData(value);
    }
    
}
