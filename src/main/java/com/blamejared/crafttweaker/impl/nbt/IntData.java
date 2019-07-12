package com.blamejared.crafttweaker.impl.nbt;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.nbt.IData;
import net.minecraft.nbt.IntNBT;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Name("crafttweaker.api.nbt.IntData")
@ZenRegister
public class IntData implements IData {
    
    private IntNBT internal;
    
    public IntData(IntNBT internal) {
        this.internal = internal;
    }
    
    @ZenCodeType.Constructor
    public IntData(int internal) {
        this.internal = new IntNBT(internal);
    }
    
    @Override
    public IData copy() {
        return new IntData(internal);
    }
    
    @Override
    public IntNBT getInternal() {
        return internal;
    }
}
