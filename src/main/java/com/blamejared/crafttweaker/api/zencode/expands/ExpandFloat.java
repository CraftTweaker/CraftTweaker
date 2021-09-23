package com.blamejared.crafttweaker.api.zencode.expands;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.impl.data.FloatData;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Expansion("float")
public class ExpandFloat {
    
    @ZenCodeType.Caster(implicit = true)
    public static IData asData(float value) {
        
        return new FloatData(value);
    }
    
}
