package com.blamejared.crafttweaker.api.zencode.expands;

import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.impl.data.ListData;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;

//@ZenRegister
@ZenCodeType.Expansion("any[]")
public class ExpandArray {
    
    @ZenCodeType.Caster
    public static IData asData(IData[] values) {
        return new ListData(Arrays.asList(values));
    }
}
