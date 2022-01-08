package com.blamejared.crafttweaker.api.data;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.base.ICollectionData;
import com.blamejared.crafttweaker.api.data.base.IData;
import com.blamejared.crafttweaker.api.data.base.INumberData;
import com.blamejared.crafttweaker.api.data.base.visitor.DataVisitor;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.NumericTag;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.List;

/**
 * @docParam this [4, 128, 256, 1024]
 */
@ZenCodeType.Name("crafttweaker.api.data.IntArrayData")
@ZenRegister
@Document("vanilla/api/data/IntArrayData")
public class IntArrayData implements ICollectionData {
    
    private final IntArrayTag internal;
    
    public IntArrayData(IntArrayTag internal) {
        
        this.internal = internal;
    }
    
    @ZenCodeType.Constructor
    public IntArrayData(int[] internal) {
        
        this.internal = new IntArrayTag(internal);
    }
    
    @Override
    public IntArrayData copy() {
        
        return new IntArrayData(getInternal());
    }
    
    @Override
    public IntArrayData copyInternal() {
        
        return new IntArrayData(getInternal().copy());
    }
    
    @Override
    public IntArrayTag getInternal() {
        
        return internal;
    }
    
    @Override
    public IntData setAt(int index, IData value) {
        
        if(value instanceof NumericTag) {
            return new IntData(getInternal().set(index, IntTag.valueOf(((INumberData) value).getInt())));
        } else {
            return null;
        }
    }
    
    
    @Override
    public void add(int index, IData value) {
        
        if(value instanceof INumberData) {
            getInternal().add(index, IntTag.valueOf(((INumberData) value).getInt()));
        }
    }
    
    @Override
    public void add(IData value) {
        
        if(value instanceof INumberData) {
            getInternal().add(IntTag.valueOf(((INumberData) value).getInt()));
        }
    }
    
    @Override
    public IntData remove(int index) {
        
        return new IntData(getInternal().remove(index));
    }
    
    @Override
    public IData getAt(int index) {
        
        return new IntData(getInternal().get(index));
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
    public List<IData> asList() {
        
        final int[] intArray = getInternal().getAsIntArray();
        List<IData> list = new ArrayList<>(intArray.length);
        for(int i : intArray) {
            list.add(new IntData(i));
        }
        return list;
    }
    
    @Override
    public Type getType() {
        
        return Type.INT_ARRAY;
    }
    
    @Override
    public <T> T accept(DataVisitor<T> visitor) {
        
        return visitor.visitIntArray(this);
    }
    
    @Override
    public boolean equals(Object o) {
        
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        
        IntArrayData that = (IntArrayData) o;
        
        return internal.equals(that.internal);
    }
    
    @Override
    public int hashCode() {
        
        return internal.hashCode();
    }
    
}
