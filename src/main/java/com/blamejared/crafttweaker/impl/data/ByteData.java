package com.blamejared.crafttweaker.impl.data;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.INumberData;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.nbt.ByteNBT;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docParam this 4
 */
@ZenCodeType.Name("crafttweaker.api.data.ByteData")
@ZenRegister
@Document("vanilla/api/data/ByteData")
public class ByteData implements INumberData {
    
    private ByteNBT internal;
    
    public ByteData(ByteNBT internal) {
        this.internal = internal;
    }
    
    @ZenCodeType.Constructor
    public ByteData(byte internal) {
        this.internal = ByteNBT.valueOf(internal);
    }
    
    @Override
    public IData copy() {
        return new ByteData(internal);
    }
    
    @Override
    public IData copyInternal() {
        return new ByteData(getInternal().copy());
    }
    
    @Override
    public ByteNBT getInternal() {
        return internal;
    }
    
    @Override
    public boolean contains(IData data) {
        if(data instanceof ByteData) {
            return getInternal().getByte() == ((ByteData) data).getInternal().getByte();
        }
        return false;
    }
    
    @Override
    public String asString() {
        return internal.getByte() + " as byte";
    }
    
}
