package com.blamejared.crafttweaker.api.zencode.expand;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.ListData;
import com.blamejared.crafttweaker.api.data.base.IData;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;

@ZenRegister
@ZenCodeType.Expansion("crafttweaker.api.data.IData[]")
public class ExpandArray {
    
    @ZenCodeType.Caster(implicit = true)
    public static IData asData(IData[] values) {
        
        return new ListData(Arrays.asList(values));
    }
    
}
