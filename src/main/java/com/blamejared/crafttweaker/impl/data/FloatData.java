package com.blamejared.crafttweaker.impl.data;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import net.minecraft.nbt.FloatNBT;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Name("crafttweaker.api.data.FloatData")
@ZenRegister
public class FloatData implements IData {
    
    private FloatNBT internal;
    
    public FloatData(FloatNBT internal) {
        this.internal = internal;
    }
    
    @ZenCodeType.Constructor
    public FloatData(float internal) {
        this.internal = new FloatNBT(internal);
    }
    
    @Override
    public IData copy() {
        return new FloatData(internal);
    }
    
    @Override
    public FloatNBT getInternal() {
        return internal;
    }
    
    @Override
    public boolean contains(IData data) {
        if(data instanceof FloatData) {
            return getInternal().getFloat() == ((FloatData) data).getInternal().getFloat();
        }
        return false;
    }
    
    @Override
    public String asString() {
        return internal.getFloat() + " as float";
    }
}
