package com.blamejared.crafttweaker.api.data;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.base.IData;
import com.blamejared.crafttweaker.api.data.base.INumberData;
import com.blamejared.crafttweaker.api.data.base.visitor.DataVisitor;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.nbt.IntTag;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docParam this 8192
 */
@ZenCodeType.Name("crafttweaker.api.data.IntData")
@ZenRegister
@Document("vanilla/api/data/IntData")
public class IntData implements INumberData {
    
    private final IntTag internal;
    
    public IntData(IntTag internal) {
        
        this.internal = internal;
    }
    
    @ZenCodeType.Constructor
    public IntData(int internal) {
        
        this.internal = IntTag.valueOf(internal);
    }
    
    @Override
    public IntData copy() {
        
        return new IntData(getInternal());
    }
    
    @Override
    public IntData copyInternal() {
        
        return new IntData(getInternal().copy());
    }
    
    @Override
    public IntTag getInternal() {
        
        return internal;
    }
    
    @Override
    public boolean contains(IData data) {
        
        if(data instanceof IntData) {
            return getInternal().getAsInt() == ((IntData) data).getInternal().getAsInt();
        }
        return false;
    }
    
    @Override
    public Type getType() {
        
        return Type.INT;
    }
    
    @Override
    public <T> T accept(DataVisitor<T> visitor) {
        
        return visitor.visitInt(this);
    }
    
    @Override
    public boolean equals(Object o) {
        
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        
        IntData intData = (IntData) o;
        
        return internal.equals(intData.internal);
    }
    
    @Override
    public int hashCode() {
        
        return internal.hashCode();
    }
    
}
