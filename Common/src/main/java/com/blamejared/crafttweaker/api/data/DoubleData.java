package com.blamejared.crafttweaker.api.data;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.base.IData;
import com.blamejared.crafttweaker.api.data.base.INumberData;
import com.blamejared.crafttweaker.api.data.base.visitor.DataVisitor;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.nbt.DoubleTag;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docParam this 3.25
 */
@ZenCodeType.Name("crafttweaker.api.data.DoubleData")
@ZenRegister
@Document("vanilla/api/data/DoubleData")
public class DoubleData implements INumberData {
    
    private final DoubleTag internal;
    
    public DoubleData(DoubleTag internal) {
        
        this.internal = internal;
    }
    
    @ZenCodeType.Constructor
    public DoubleData(double internal) {
        
        this.internal = DoubleTag.valueOf(internal);
    }
    
    @Override
    public DoubleData copy() {
        
        return new DoubleData(getInternal());
    }
    
    @Override
    public DoubleData copyInternal() {
        
        return new DoubleData(getInternal().copy());
    }
    
    @Override
    public DoubleTag getInternal() {
        
        return internal;
    }
    
    @Override
    public boolean contains(IData data) {
        
        if(data instanceof DoubleData) {
            return getInternal().getAsDouble() == ((DoubleData) data).getInternal().getAsDouble();
        }
        return false;
    }
    
    @Override
    public Type getType() {
        
        return Type.DOUBLE;
    }
    
    @Override
    public <T> T accept(DataVisitor<T> visitor) {
        
        return visitor.visitDouble(this);
    }
    
    @Override
    public boolean equals(Object o) {
        
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        
        DoubleData that = (DoubleData) o;
        
        return internal.equals(that.internal);
    }
    
    @Override
    public int hashCode() {
        
        return internal.hashCode();
    }
    
}
