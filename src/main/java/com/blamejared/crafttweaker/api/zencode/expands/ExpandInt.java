package com.blamejared.crafttweaker.api.zencode.expands;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.impl.data.IntData;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Expansion("int")
public class ExpandInt {
    
    @ZenCodeType.Caster(implicit = true)
    public static IData asData(int value) {
        return new IntData(value);
    }
    
}
