package com.blamejared.crafttweaker.impl.nbt;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.nbt.IData;
import com.blamejared.crafttweaker.api.nbt.INumberData;
import net.minecraft.nbt.ByteNBT;
import org.openzen.zencode.java.ZenCodeType;

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
    
    @Override
    public String asString() {
        return internal.getByte() + " as byte";
    }
    
}
