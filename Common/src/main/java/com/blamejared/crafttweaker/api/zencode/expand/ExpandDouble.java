package com.blamejared.crafttweaker.api.zencode.expand;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.DoubleData;
import com.blamejared.crafttweaker.api.data.base.IData;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Expansion("double")
public class ExpandDouble {
    
    @ZenCodeType.Caster(implicit = true)
    public static IData asData(double value) {
        
        return new DoubleData(value);
    }
    
}
