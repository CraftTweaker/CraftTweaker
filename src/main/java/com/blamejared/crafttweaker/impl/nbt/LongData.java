package com.blamejared.crafttweaker.impl.nbt;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.nbt.IData;
import net.minecraft.nbt.LongNBT;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Name("crafttweaker.api.nbt.LongData")
@ZenRegister
public class LongData implements IData {
    
    private LongNBT internal;
    
    public LongData(LongNBT internal) {
        this.internal = internal;
    }
    
    @ZenCodeType.Constructor
    public LongData(long internal) {
        this.internal = new LongNBT(internal);
    }
    
    @Override
    public IData copy() {
        return new LongData(internal);
    }
    
    @Override
    public LongNBT getInternal() {
        return internal;
    }
    
    @Override
    public String asString() {
        return internal.getLong() + " as long";
    }
}
