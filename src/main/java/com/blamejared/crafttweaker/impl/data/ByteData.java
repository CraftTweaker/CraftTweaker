package com.blamejared.crafttweaker.impl.data;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.INumberData;
import net.minecraft.nbt.ByteNBT;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Name("crafttweaker.api.data.ByteData")
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
    public boolean contains(IData data) {
        if(data instanceof ByteData) {
            return getInternal().getByte() == ((ByteData) data).getInternal().getByte();
        }
        return false;
    }
    
    @Override
    public String asString() {
        return internal.getByte() + " as byte";
    }
    
}
