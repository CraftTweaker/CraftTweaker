package com.blamejared.crafttweaker.api.data;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.base.IData;
import com.blamejared.crafttweaker.api.data.base.INumberData;
import com.blamejared.crafttweaker.api.data.base.visitor.DataVisitor;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.nbt.FloatTag;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docParam this 8.5
 */
@ZenCodeType.Name("crafttweaker.api.data.FloatData")
@ZenRegister
@Document("vanilla/api/data/FloatData")
public class FloatData implements INumberData {
    
    private final FloatTag internal;
    
    public FloatData(FloatTag internal) {
        
        this.internal = internal;
    }
    
    @ZenCodeType.Constructor
    public FloatData(float internal) {
        
        this.internal = FloatTag.valueOf(internal);
    }
    
    @Override
    public FloatData copy() {
        
        return new FloatData(getInternal());
    }
    
    @Override
    public FloatData copyInternal() {
        
        return new FloatData(getInternal().copy());
    }
    
    @Override
    public FloatTag getInternal() {
        
        return internal;
    }
    
    @Override
    public boolean contains(IData data) {
        
        if(data instanceof FloatData) {
            return getInternal().getAsFloat() == ((FloatData) data).getInternal().getAsFloat();
        }
        return false;
    }
    
    
    @Override
    public Type getType() {
        
        return Type.FLOAT;
    }
    
    @Override
    public <T> T accept(DataVisitor<T> visitor) {
        
        return visitor.visitFloat(this);
    }
    
    @Override
    public boolean equals(Object o) {
        
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        
        FloatData floatData = (FloatData) o;
        
        return internal.equals(floatData.internal);
    }
    
    @Override
    public int hashCode() {
        
        return internal.hashCode();
    }
    
}
