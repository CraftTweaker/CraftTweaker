package com.blamejared.crafttweaker.impl.nbt;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.nbt.ICollectionData;
import com.blamejared.crafttweaker.api.nbt.IData;
import com.blamejared.crafttweaker.api.nbt.INumberData;
import net.minecraft.nbt.ByteArrayNBT;
import net.minecraft.nbt.ByteNBT;
import net.minecraft.nbt.NumberNBT;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Name("crafttweaker.api.nbt.ByteArrayData")
@ZenRegister
public class ByteArrayData implements ICollectionData {
    
    private ByteArrayNBT internal;
    
    public ByteArrayData(ByteArrayNBT internal) {
        this.internal = internal;
    }
    
    @ZenCodeType.Constructor
    public ByteArrayData(byte[] internal) {
        this.internal = new ByteArrayNBT(internal);
    }
    
    @Override
    public IData copy() {
        return new ByteArrayData(internal);
    }
    
    @Override
    public ByteArrayNBT getInternal() {
        return internal;
    }
    
    @Override
    public IData set(int index, IData value) {
        if(value instanceof NumberNBT) {
            return new ByteData(internal.set(index, new ByteNBT(((INumberData) value).getByte())));
        } else {
            return null;
        }
    }
    
    
    @Override
    public void add(int index, IData value) {
        if(value instanceof INumberData) {
            internal.add(index, new ByteNBT(((INumberData) value).getByte()));
        }
    }
    
    @Override
    public IData remove(int index) {
        return new ByteData(internal.remove(index));
    }
    
    @Override
    public IData get(int index) {
        return new ByteData(internal.get(index));
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
