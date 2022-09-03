package com.blamejared.crafttweaker.api.zencode.expand;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.ByteData;
import com.blamejared.crafttweaker.api.data.IData;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Expansion("byte")
public class ExpandByte {
    
    @ZenCodeType.Caster(implicit = true)
    public static IData asData(byte value) {
        
        return new ByteData(value);
    }
    
}
