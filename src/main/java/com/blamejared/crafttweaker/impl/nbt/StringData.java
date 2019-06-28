package com.blamejared.crafttweaker.impl.nbt;

import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.api.nbt.*;
import net.minecraft.nbt.*;
import org.openzen.zencode.java.*;

@ZenCodeType.Name("crafttweaker.api.nbt.StringData")
@ZenRegister
public class StringData implements IData {
    
    private StringNBT internal;
    
    public StringData(StringNBT internal) {
        this.internal = internal;
    }
    
    @ZenCodeType.Constructor
    public StringData(String internal) {
        this.internal = new StringNBT(internal);
    }
    
    @Override
    public byte getId() {
        return internal.getId();
    }
    
    @Override
    public IData copy() {
        return new StringData(internal);
    }
    
    @ZenCodeType.Method
    public String getInternal() {
        return internal.getString();
    }
    
    @ZenCodeType.Caster(implicit = true)
    public String toString() {
        return internal.toString();
    }
    
}
