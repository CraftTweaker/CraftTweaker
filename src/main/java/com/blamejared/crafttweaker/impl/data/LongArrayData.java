package com.blamejared.crafttweaker.impl.data;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.data.ICollectionData;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.INumberData;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.nbt.LongArrayNBT;
import net.minecraft.nbt.LongNBT;
import net.minecraft.nbt.NumberNBT;
import org.openzen.zencode.java.ZenCodeType;

import java.util.*;

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
        return new LongArrayData(internal);
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
            return new LongData(internal.set(index, LongNBT.valueOf(((INumberData) value).getLong())));
        } else {
            return null;
        }
    }
    
    
    @Override
    public void add(int index, IData value) {
        if(value instanceof INumberData) {
            internal.add(index, LongNBT.valueOf(((INumberData) value).getInt()));
        }
    }
    
    @Override
    public void add(IData value) {
        if(value instanceof INumberData) {
            internal.add(LongNBT.valueOf(((INumberData) value).getInt()));
        }
    }
    
    @Override
    public LongData remove(int index) {
        return new LongData(internal.remove(index));
    }
    
    @Override
    public IData getAt(int index) {
        return new LongData(internal.get(index));
    }
    
    @Override
    public int size() {
        return internal.size();
    }
    
    @Override
    public void clear() {
        internal.clear();
    }
    
    
    @Override
    public String asString() {
        StringBuilder result = new StringBuilder();
        result.append('[');
        boolean first = true;
        for(LongNBT nbt : internal) {
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
    public List<IData> asList() {
        final long[] asLongArray = internal.getAsLongArray();
        List<IData> list = new ArrayList<>(asLongArray.length);
        for(long l : asLongArray) {
            list.add(new LongData(l));
        }
        return list;
    }
}
