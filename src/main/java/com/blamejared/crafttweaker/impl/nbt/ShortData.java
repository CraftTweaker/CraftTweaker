package com.blamejared.crafttweaker.impl.nbt;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.nbt.IData;
import net.minecraft.nbt.ShortNBT;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Name("crafttweaker.api.nbt.ShortData")
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
    public String asString() {
        return internal.getShort() + " as short";
    }
}
