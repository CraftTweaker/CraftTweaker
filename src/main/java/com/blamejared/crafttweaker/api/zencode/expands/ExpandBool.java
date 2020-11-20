package com.blamejared.crafttweaker.api.zencode.expands;

import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.api.data.*;
import com.blamejared.crafttweaker.impl.data.*;
import org.openzen.zencode.java.*;

@ZenRegister
@ZenCodeType.Expansion("bool")
public class ExpandBool {
    
    @ZenCodeType.Caster(implicit = true)
    public static IData asData(boolean _this) {
        return new BoolData(_this);
    }
}
