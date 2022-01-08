package com.blamejared.crafttweaker.api.zencode.expand;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.IntData;
import com.blamejared.crafttweaker.api.data.base.IData;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Expansion("int")
public class ExpandInt {
    
    @ZenCodeType.Caster(implicit = true)
    public static IData asData(int value) {
        
        return new IntData(value);
    }
    
}
