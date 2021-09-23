package com.blamejared.crafttweaker.api.zencode.expands;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.impl.data.BoolData;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Expansion("bool")
public class ExpandBool {
    
    @ZenCodeType.Caster(implicit = true)
    public static IData asData(boolean _this) {
        
        return new BoolData(_this);
    }
    
}
