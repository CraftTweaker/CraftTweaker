package com.blamejared.crafttweaker.impl.data;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.INumberData;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.nbt.IntNBT;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docParam this 8192
 */
@ZenCodeType.Name("crafttweaker.api.data.IntData")
@ZenRegister
@Document("vanilla/api/data/IntData")
public class IntData implements INumberData {
    
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
    
    @Override
    public boolean contains(IData data) {
        if(data instanceof IntData) {
            return getInternal().getInt() == ((IntData) data).getInternal().getInt();
        }
        return false;
    }
    
    @Override
    public String asString() {
        return internal.getInt() + "";
    }
}
