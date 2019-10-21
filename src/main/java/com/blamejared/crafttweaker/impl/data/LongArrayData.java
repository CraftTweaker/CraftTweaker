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

@ZenCodeType.Name("crafttweaker.api.data.LongArrayData")
@ZenRegister
@Document("vanilla/data/LongArrayData")
public class LongArrayData implements ICollectionData {
    
    private LongArrayNBT internal;
    
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
    public LongArrayNBT getInternal() {
        return internal;
    }
    
    @Override
    public LongData set(int index, IData value) {
        if(value instanceof NumberNBT) {
            return new LongData(internal.set(index, new LongNBT(((INumberData) value).getLong())));
        } else {
            return null;
        }
    }
    
    
    @Override
    public void add(int index, IData value) {
        if(value instanceof INumberData) {
            internal.add(index, new LongNBT(((INumberData) value).getInt()));
        }
    }
    
    @Override
    public void add(IData value) {
        if(value instanceof INumberData) {
            internal.add(new LongNBT(((INumberData) value).getInt()));
        }
    }
    
    @Override
    public LongData remove(int index) {
        return new LongData(internal.remove(index));
    }
    
    @Override
    public IData get(int index) {
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
        result.append(']').append("as long[]");
        return result.toString();
    }
}
