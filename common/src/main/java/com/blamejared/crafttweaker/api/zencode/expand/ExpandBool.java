package com.blamejared.crafttweaker.api.zencode.expand;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.BoolData;
import com.blamejared.crafttweaker.api.data.IData;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Expansion("bool")
public class ExpandBool {
    
    @ZenCodeType.Caster(implicit = true)
    public static IData asData(boolean _this) {
        
        return new BoolData(_this);
    }
    
}
