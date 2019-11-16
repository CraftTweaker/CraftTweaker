package com.blamejared.crafttweaker.impl.data;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.nbt.LongNBT;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docParam this 800000000
 */
@ZenCodeType.Name("crafttweaker.api.data.LongData")
@ZenRegister
@Document("vanilla/data/LongData")
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
    public boolean contains(IData data) {
        if(data instanceof LongData) {
            return getInternal().getLong() == ((LongData) data).getInternal().getLong();
        }
        return false;
    }
    
    @Override
    public String asString() {
        return internal.getLong() + "";
    }
}
