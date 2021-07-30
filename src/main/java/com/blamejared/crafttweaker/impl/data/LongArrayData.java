package com.blamejared.crafttweaker.impl.data;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.data.ICollectionData;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.INumberData;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.nbt.LongArrayNBT;
import net.minecraft.nbt.LongNBT;
import net.minecraft.nbt.NumberNBT;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.List;

/**
 * @docParam this [100000, 800000, 50000]
 */
@ZenCodeType.Name("crafttweaker.api.data.LongArrayData")
@ZenRegister
@Document("vanilla/api/data/LongArrayData")
public class LongArrayData implements ICollectionData {
    
    private final LongArrayNBT internal;
    
    public LongArrayData(LongArrayNBT internal) {
        
        this.internal = internal;
    }
    
    @ZenCodeType.Constructor
    public LongArrayData(long[] internal) {
        
        this.internal = new LongArrayNBT(internal);
    }
    
    @Override
    public IData copy() {
        
        return new LongArrayData(getInternal());
    }
    
    @Override
    public IData copyInternal() {
        
        return new LongArrayData(getInternal().copy());
    }
    
    @Override
    public LongArrayNBT getInternal() {
        
        return internal;
    }
    
    @Override
    public LongData setAt(int index, IData value) {
        
        if(value instanceof NumberNBT) {
            return new LongData(getInternal().set(index, LongNBT.valueOf(((INumberData) value).getLong())));
        } else {
            return null;
        }
    }
    
    
    @Override
    public void add(int index, IData value) {
        
        if(value instanceof INumberData) {
            getInternal().add(index, LongNBT.valueOf(((INumberData) value).getInt()));
        }
    }
    
    @Override
    public void add(IData value) {
        
        if(value instanceof INumberData) {
            getInternal().add(LongNBT.valueOf(((INumberData) value).getInt()));
        }
    }
    
    @Override
    public LongData remove(int index) {
        
        return new LongData(getInternal().remove(index));
    }
    
    @Override
    public IData getAt(int index) {
        
        return new LongData(getInternal().get(index));
    }
    
    @Override
    public int size() {
        
        return getInternal().size();
    }
    
    @Override
    public boolean isEmpty() {
        
        return getInternal().isEmpty();
    }
    
    @Override
    public void clear() {
        
        getInternal().clear();
    }
    
    
    @Override
    public String asString() {
        
        StringBuilder result = new StringBuilder();
        result.append('[');
        boolean first = true;
        for(LongNBT nbt : getInternal()) {
            if(first) {
                first = false;
            } else {
                result.append(", ");
            }
            result.append(nbt.getLong());
        }
        result.append(']');
        return result.toString();
    }
    
    
    @Override
    public ITextComponent asFormattedComponent(String indentation, int indentDepth) {
        
        ITextComponent as = new StringTextComponent(" as ").mergeStyle(IData.SYNTAX_HIGHLIGHTING_AS);
        ITextComponent baseType = new StringTextComponent("long").mergeStyle(IData.SYNTAX_HIGHLIGHTING_TYPE);
        ITextComponent type = new StringTextComponent("long[]").mergeStyle(IData.SYNTAX_HIGHLIGHTING_TYPE);
        IFormattableTextComponent component = (new StringTextComponent("["));
        
        for(int i = 0; i < size(); ++i) {
            IFormattableTextComponent child = new StringTextComponent(getAt(i).toJsonString()).mergeStyle(IData.SYNTAX_HIGHLIGHTING_NUMBER);
            component.appendString(i == 0 ? "" : " ").append(child);
            if(i != size() - 1) {
                component.appendString(",");
            }
        }
        
        component.appendString("]").append(as).append(type);
        return component;
    }
    
    @Override
    public List<IData> asList() {
        
        final long[] asLongArray = getInternal().getAsLongArray();
        List<IData> list = new ArrayList<>(asLongArray.length);
        for(long l : asLongArray) {
            list.add(new LongData(l));
        }
        return list;
    }
    
    @Override
    public boolean equals(Object o) {
        
        if(this == o)
            return true;
        if(o == null || getClass() != o.getClass())
            return false;
        
        LongArrayData that = (LongArrayData) o;
    
        return internal.equals(that.internal);
    }
    
    @Override
    public int hashCode() {
        
        return internal.hashCode();
    }
    
}
