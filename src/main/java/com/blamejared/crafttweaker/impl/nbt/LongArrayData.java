package com.blamejared.crafttweaker.impl.nbt;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.nbt.ICollectionData;
import com.blamejared.crafttweaker.api.nbt.IData;
import com.blamejared.crafttweaker.api.nbt.INumberData;
import net.minecraft.nbt.LongArrayNBT;
import net.minecraft.nbt.LongNBT;
import net.minecraft.nbt.NumberNBT;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Name("crafttweaker.api.nbt.LongArrayData")
@ZenRegister
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
}
