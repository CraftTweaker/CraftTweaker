package com.blamejared.crafttweaker.api.data;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.base.ICollectionData;
import com.blamejared.crafttweaker.api.data.base.IData;
import com.blamejared.crafttweaker.api.data.base.INumberData;
import com.blamejared.crafttweaker.api.data.base.visitor.DataVisitor;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.nbt.LongArrayTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.NumericTag;
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
    
    private final LongArrayTag internal;
    
    public LongArrayData(LongArrayTag internal) {
        
        this.internal = internal;
    }
    
    @ZenCodeType.Constructor
    public LongArrayData(long[] internal) {
        
        this.internal = new LongArrayTag(internal);
    }
    
    @Override
    public LongArrayData copy() {
        
        return new LongArrayData(getInternal());
    }
    
    @Override
    public LongArrayData copyInternal() {
        
        return new LongArrayData(getInternal().copy());
    }
    
    @Override
    public LongArrayTag getInternal() {
        
        return internal;
    }
    
    @Override
    public LongData setAt(int index, IData value) {
        
        if(value instanceof NumericTag) {
            return new LongData(getInternal().set(index, LongTag.valueOf(((INumberData) value).getLong())));
        } else {
            return null;
        }
    }
    
    
    @Override
    public void add(int index, IData value) {
        
        if(value instanceof INumberData) {
            getInternal().add(index, LongTag.valueOf(((INumberData) value).getInt()));
        }
    }
    
    @Override
    public void add(IData value) {
        
        if(value instanceof INumberData) {
            getInternal().add(LongTag.valueOf(((INumberData) value).getInt()));
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
    public List<IData> asList() {
        
        final long[] asLongArray = getInternal().getAsLongArray();
        List<IData> list = new ArrayList<>(asLongArray.length);
        for(long l : asLongArray) {
            list.add(new LongData(l));
        }
        return list;
    }
    
    @Override
    public Type getType() {
        
        return Type.LONG_ARRAY;
    }
    
    @Override
    public <T> T accept(DataVisitor<T> visitor) {
        
        return visitor.visitLongArray(this);
    }
    
    @Override
    public boolean equals(Object o) {
        
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        
        LongArrayData that = (LongArrayData) o;
        
        return internal.equals(that.internal);
    }
    
    @Override
    public int hashCode() {
        
        return internal.hashCode();
    }
    
}
