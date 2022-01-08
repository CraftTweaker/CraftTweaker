package com.blamejared.crafttweaker.api.data;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.base.IData;
import com.blamejared.crafttweaker.api.data.base.visitor.DataVisitor;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.nbt.StringTag;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docParam this new StringData("Hello")
 */
@ZenCodeType.Name("crafttweaker.api.data.StringData")
@ZenRegister
@Document("vanilla/api/data/StringData")
public class StringData implements IData {
    
    private final StringTag internal;
    
    public StringData(StringTag internal) {
        
        this.internal = internal;
    }
    
    @ZenCodeType.Constructor
    public StringData(String internal) {
        
        this.internal = StringTag.valueOf(internal);
    }
    
    @Override
    public StringData copy() {
        
        return new StringData(getInternal());
    }
    
    @Override
    public StringData copyInternal() {
        
        return new StringData(getInternal().copy());
    }
    
    @Override
    public StringTag getInternal() {
        
        return internal;
    }
    
    @Override
    public boolean contains(IData data) {
        
        return asString().equals(data.asString());
    }
    
    /**
     * Concatenates the two string Datas and returns the result.
     *
     * @param data The other data to append
     *
     * @return A new StringData with the value concatenated.
     *
     * @docParam data new StringData("World")
     */
    @ZenCodeType.Operator(ZenCodeType.OperatorType.ADD)
    public StringData addTogether(StringData data) {
        
        return new StringData(internal.getAsString() + data.getInternal().getAsString());
    }
    
    @Override
    public Type getType() {
        
        return Type.STRING;
    }
    
    @Override
    public <T> T accept(DataVisitor<T> visitor) {
        
        return visitor.visitString(this);
    }
    
    @Override
    public boolean equals(Object o) {
        
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        
        StringData that = (StringData) o;
        
        return internal.equals(that.internal);
    }
    
    @Override
    public int hashCode() {
        
        return internal.hashCode();
    }
    
}
