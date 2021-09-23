package com.blamejared.crafttweaker.api.zencode.expands;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.impl.data.DoubleData;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Expansion("double")
public class ExpandDouble {
    
    @ZenCodeType.Caster(implicit = true)
    public static IData asData(double value) {
        
        return new DoubleData(value);
    }
    
}
