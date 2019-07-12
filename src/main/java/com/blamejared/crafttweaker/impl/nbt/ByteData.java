package com.blamejared.crafttweaker.impl.nbt;

import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.api.nbt.*;
import net.minecraft.nbt.*;
import org.openzen.zencode.java.*;

@ZenCodeType.Name("crafttweaker.api.nbt.ByteData")
@ZenRegister
public class ByteData implements INumberData {
    
    private ByteNBT internal;
    
    public ByteData(ByteNBT internal) {
        this.internal = internal;
    }
    
    @ZenCodeType.Constructor
    public ByteData(byte internal) {
        this.internal = new ByteNBT(internal);
    }
    
    @Override
    public IData copy() {
        return new ByteData(internal);
    }
    
    @Override
    public ByteNBT getInternal() {
        return internal;
    }
    
}
