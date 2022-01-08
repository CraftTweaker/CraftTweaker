package com.blamejared.crafttweaker.api.data;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.base.IData;
import com.blamejared.crafttweaker.api.data.base.INumberData;
import com.blamejared.crafttweaker.api.data.base.visitor.DataVisitor;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.nbt.ShortTag;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docParam this 1058
 */
@ZenCodeType.Name("crafttweaker.api.data.ShortData")
@ZenRegister
@Document("vanilla/api/data/ShortData")
public class ShortData implements INumberData {
    
    private final ShortTag internal;
    
    public ShortData(ShortTag internal) {
        
        this.internal = internal;
    }
    
    @ZenCodeType.Constructor
    public ShortData(short internal) {
        
        this.internal = ShortTag.valueOf(internal);
    }
    
    
    @Override
    public ShortData copy() {
        
        return new ShortData(getInternal());
    }
    
    @Override
    public ShortData copyInternal() {
        
        return new ShortData(getInternal().copy());
    }
    
    @Override
    public ShortTag getInternal() {
        
        return internal;
    }
    
    @Override
    public boolean contains(IData data) {
        
        if(data instanceof ShortData) {
            return getInternal().getAsShort() == ((ShortData) data).getInternal().getAsShort();
        }
        return false;
    }
    
    @Override
    public Type getType() {
        
        return Type.SHORT;
    }
    
    @Override
    public <T> T accept(DataVisitor<T> visitor) {
        
        return visitor.visitShort(this);
    }
    
    @Override
    public boolean equals(Object o) {
        
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        
        ShortData shortData = (ShortData) o;
        
        return internal.equals(shortData.internal);
    }
    
    @Override
    public int hashCode() {
        
        return internal.hashCode();
    }
    
}
