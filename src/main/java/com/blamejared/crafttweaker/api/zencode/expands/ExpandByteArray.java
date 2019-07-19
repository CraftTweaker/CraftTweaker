package com.blamejared.crafttweaker.api.zencode.expands;

import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.impl.data.ByteArrayData;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Expansion("byte[]")
public class ExpandByteArray {
    
    @ZenCodeType.Caster
    public static IData asData(byte[] value) {
        return new ByteArrayData(value);
    }
    
}
