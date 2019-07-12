package com.blamejared.crafttweaker.api.nbt;

import com.blamejared.crafttweaker.api.annotations.*;
import net.minecraft.nbt.INBT;
import org.openzen.zencode.java.*;

@ZenCodeType.Name("crafttweaker.api.tag.IData")
@ZenRegister
public interface IData {
    
    @ZenCodeType.Method
    default byte getId() {
        return getInternal().getId();
    }
    
    @ZenCodeType.Method
    IData copy();
    
    INBT getInternal();
    
    
    @ZenCodeType.Caster(implicit = true)
    default String getString() {
        return getInternal().toString();
    }
}
