package com.blamejared.crafttweaker.impl.nbt;

import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.api.nbt.*;
import net.minecraft.nbt.*;
import org.openzen.zencode.java.*;

@ZenCodeType.Name("crafttweaker.api.nbt.StringData")
@ZenRegister
public class ByteData implements IData {
    
    private ByteNBT internal;
    
    public ByteData(ByteNBT internal) {
        this.internal = internal;
    }
    
    @ZenCodeType.Constructor
    public ByteData(byte internal) {
        this.internal = new ByteNBT(internal);
    }
    
    @Override
    public byte getId() {
        return internal.getId();
    }
    
    @Override
    public IData copy() {
        return new ByteData(internal);
    }
    
    @ZenCodeType.Method
    public byte getInternal() {
        return internal.getByte();
    }
    
    @ZenCodeType.Caster(implicit = true)
    public String toString() {
        return internal.toString();
    }
    
}
