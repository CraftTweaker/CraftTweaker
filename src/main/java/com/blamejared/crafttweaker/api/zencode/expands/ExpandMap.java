package com.blamejared.crafttweaker.api.zencode.expands;

import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.impl.data.MapData;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Map;

//@ZenRegister
@ZenCodeType.Expansion("IData[string]")
public class ExpandMap {
    
    @ZenCodeType.Caster
    public static IData asData(Map<String, IData> map) {
        return new MapData(map);
    }
    
}
