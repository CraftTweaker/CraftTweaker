package com.blamejared.crafttweaker.api.zencode.expand;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.FloatData;
import com.blamejared.crafttweaker.api.data.IData;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Expansion("float")
public class ExpandFloat {
    
    @ZenCodeType.Caster(implicit = true)
    public static IData asData(float value) {
        
        return new FloatData(value);
    }
    
}
