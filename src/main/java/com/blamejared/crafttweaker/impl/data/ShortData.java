package com.blamejared.crafttweaker.impl.data;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import net.minecraft.nbt.ShortNBT;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Name("crafttweaker.api.data.ShortData")
@ZenRegister
public class ShortData implements IData {
    
    private ShortNBT internal;
    
    public ShortData(ShortNBT internal) {
        this.internal = internal;
    }
    
    @ZenCodeType.Constructor
    public ShortData(short internal) {
        this.internal = new ShortNBT(internal);
    }
    
    @Override
    public IData copy() {
        return new ShortData(internal);
    }
    
    @Override
    public ShortNBT getInternal() {
        return internal;
    }
    
    @Override
    public boolean contains(IData data) {
        if(data instanceof ShortData) {
            return getInternal().getShort() == ((ShortData) data).getInternal().getShort();
        }
        return false;
    }
    
    @Override
    public String asString() {
        return internal.getShort() + " as short";
    }
}
