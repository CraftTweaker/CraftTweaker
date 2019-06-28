package com.blamejared.crafttweaker.api.nbt;

import com.blamejared.crafttweaker.api.annotations.*;
import org.openzen.zencode.java.*;

@ZenCodeType.Name("crafttweaker.api.tag.ITag")
@ZenRegister
public interface IData {
    
    @ZenCodeType.Method
    byte getId();
    
    @ZenCodeType.Method
    IData copy();
}
