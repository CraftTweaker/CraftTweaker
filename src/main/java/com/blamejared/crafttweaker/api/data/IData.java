package com.blamejared.crafttweaker.api.data;

import com.blamejared.crafttweaker.api.annotations.*;
import net.minecraft.nbt.INBT;
import org.openzen.zencode.java.*;

@ZenCodeType.Name("crafttweaker.api.data.IData")
@ZenRegister
public interface IData {
    
    @ZenCodeType.Method
    default byte getId() {
        return getInternal().getId();
    }
    
    @ZenCodeType.Method
    IData copy();
    
    INBT getInternal();
    
    
    @ZenCodeType.Method
    default String getString() {
        return getInternal().toString();
    }
    
    @ZenCodeType.Method
    String asString();
    
}
