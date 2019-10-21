package com.blamejared.crafttweaker.impl.data;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.nbt.DoubleNBT;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Name("crafttweaker.api.data.DoubleData")
@ZenRegister
@Document("vanilla/data/DoubleData")
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
    public boolean contains(IData data) {
        if(data instanceof DoubleData) {
            return getInternal().getDouble() == ((DoubleData) data).getInternal().getDouble();
        }
        return false;
    }
    
    @Override
    public String asString() {
        return internal.getDouble() + " as double";
    }
}
