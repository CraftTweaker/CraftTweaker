package com.blamejared.crafttweaker.api.data;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.base.IData;
import com.blamejared.crafttweaker.api.data.base.INumberData;
import com.blamejared.crafttweaker.api.data.base.visitor.DataVisitor;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.nbt.LongTag;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docParam this 800000000
 */
@ZenCodeType.Name("crafttweaker.api.data.LongData")
@ZenRegister
@Document("vanilla/api/data/LongData")
public class LongData implements INumberData {
    
    private final LongTag internal;
    
    public LongData(LongTag internal) {
        
        this.internal = internal;
    }
    
    @ZenCodeType.Constructor
    public LongData(long internal) {
        
        this.internal = LongTag.valueOf(internal);
    }
    
    @Override
    public LongData copy() {
        
        return new LongData(getInternal());
    }
    
    @Override
    public LongData copyInternal() {
        
        return new LongData(getInternal().copy());
    }
    
    @Override
    public LongTag getInternal() {
        
        return internal;
    }
    
    @Override
    public boolean contains(IData data) {
        
        if(data instanceof LongData) {
            return getInternal().getAsLong() == ((LongData) data).getInternal().getAsLong();
        }
        return false;
    }
    
    @Override
    public Type getType() {
        
        return Type.LONG;
    }
    
    @Override
    public <T> T accept(DataVisitor<T> visitor) {
        
        return visitor.visitLong(this);
    }
    
    @Override
    public boolean equals(Object o) {
        
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        
        LongData longData = (LongData) o;
        
        return internal.equals(longData.internal);
    }
    
    @Override
    public int hashCode() {
        
        return internal.hashCode();
    }
    
}
