package com.blamejared.crafttweaker.impl.data;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.INumberData;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.nbt.ShortNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docParam this 1058
 */
@ZenCodeType.Name("crafttweaker.api.data.ShortData")
@ZenRegister
@Document("vanilla/api/data/ShortData")
public class ShortData implements INumberData {
    
    private final ShortNBT internal;
    
    public ShortData(ShortNBT internal) {
        
        this.internal = internal;
    }
    
    @ZenCodeType.Constructor
    public ShortData(short internal) {
        
        this.internal = ShortNBT.valueOf(internal);
    }
    
    
    @Override
    public IData copy() {
        
        return new ShortData(getInternal());
    }
    
    @Override
    public IData copyInternal() {
        
        return new ShortData(getInternal().copy());
    }
    
    @Override
    public ShortNBT getInternal() {
        
        return internal;
    }
    
    @Override
    public boolean contains(IData data) {
        
        if(data instanceof ShortData) {
            return getInternal().getShort() == ((ShortData) data).getInternal().getShort();
        }
        return false;
    }
    
    @Override
    public String asString() {
        
        return getInternal().getShort() + " as short";
    }
    
    @Override
    public ITextComponent asFormattedComponent(String indentation, int indentDepth) {
        
        ITextComponent as = new StringTextComponent(" as ").mergeStyle(IData.SYNTAX_HIGHLIGHTING_AS);
        ITextComponent type = new StringTextComponent("short").mergeStyle(IData.SYNTAX_HIGHLIGHTING_TYPE);
        return new StringTextComponent(toJsonString()).append(as).append(type)
                .mergeStyle(IData.SYNTAX_HIGHLIGHTING_NUMBER);
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
