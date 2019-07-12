package com.blamejared.crafttweaker.impl.nbt;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.nbt.IData;
import net.minecraft.nbt.FloatNBT;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Name("crafttweaker.api.nbt.FloatData")
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
}
