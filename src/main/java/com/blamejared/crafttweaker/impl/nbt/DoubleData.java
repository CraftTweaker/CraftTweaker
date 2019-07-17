package com.blamejared.crafttweaker.impl.nbt;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.nbt.IData;
import net.minecraft.nbt.DoubleNBT;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Name("crafttweaker.api.nbt.DoubleData")
@ZenRegister
public class DoubleData implements IData {
    
    private DoubleNBT internal;
    
    public DoubleData(DoubleNBT internal) {
        this.internal = internal;
    }
    
    @ZenCodeType.Constructor
    public DoubleData(double internal) {
        this.internal = new DoubleNBT(internal);
    }
    
    @Override
    public IData copy() {
        return new DoubleData(internal);
    }
    
    @Override
    public DoubleNBT getInternal() {
        return internal;
    }
    
    @Override
    public String asString() {
        return internal.getDouble() + " as double";
    }
}
