package com.blamejared.crafttweaker.api.data;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.base.IData;
import com.blamejared.crafttweaker.api.data.base.INumberData;
import com.blamejared.crafttweaker.api.data.base.visitor.DataVisitor;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.nbt.ByteTag;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docParam this 4
 */
@ZenCodeType.Name("crafttweaker.api.data.ByteData")
@ZenRegister
@Document("vanilla/api/data/ByteData")
public class ByteData implements INumberData {
    
    private final ByteTag internal;
    
    public ByteData(ByteTag internal) {
        
        this.internal = internal;
    }
    
    @ZenCodeType.Constructor
    public ByteData(byte internal) {
        
        this.internal = ByteTag.valueOf(internal);
    }
    
    @Override
    public ByteData copy() {
        
        return new ByteData(getInternal());
    }
    
    @Override
    public ByteData copyInternal() {
        
        return new ByteData(getInternal().copy());
    }
    
    @Override
    public ByteTag getInternal() {
        
        return internal;
    }
    
    @Override
    public boolean contains(IData data) {
        
        if(data instanceof ByteData) {
            return getInternal().getAsByte() == ((ByteData) data).getInternal().getAsByte();
        }
        return false;
    }
    
    @Override
    public Type getType() {
        
        return Type.BYTE;
    }
    
    @Override
    public <T> T accept(DataVisitor<T> visitor) {
        
        return visitor.visitByte(this);
    }
    
    @Override
    public boolean equals(Object o) {
        
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        
        ByteData byteData = (ByteData) o;
        
        return internal.equals(byteData.internal);
    }
    
    @Override
    public int hashCode() {
        
        return internal.hashCode();
    }
    
}
