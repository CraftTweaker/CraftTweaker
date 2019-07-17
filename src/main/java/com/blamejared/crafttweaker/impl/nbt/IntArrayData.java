package com.blamejared.crafttweaker.impl.nbt;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.nbt.ICollectionData;
import com.blamejared.crafttweaker.api.nbt.IData;
import com.blamejared.crafttweaker.api.nbt.INumberData;
import net.minecraft.nbt.IntArrayNBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.nbt.NumberNBT;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Name("crafttweaker.api.nbt.IntArrayData")
@ZenRegister
public class IntArrayData implements ICollectionData {
    
    private IntArrayNBT internal;
    
    public IntArrayData(IntArrayNBT internal) {
        this.internal = internal;
    }
    
    @ZenCodeType.Constructor
    public IntArrayData(int[] internal) {
        this.internal = new IntArrayNBT(internal);
    }
    
    @Override
    public IData copy() {
        return new IntArrayData(internal);
    }
    
    @Override
    public IntArrayNBT getInternal() {
        return internal;
    }
    
    @Override
    public IntData set(int index, IData value) {
        if(value instanceof NumberNBT) {
            return new IntData(internal.set(index, new IntNBT(((INumberData) value).getInt())));
        } else {
            return null;
        }
    }
    
    
    @Override
    public void add(int index, IData value) {
        if(value instanceof INumberData) {
            internal.add(index, new IntNBT(((INumberData) value).getInt()));
        }
    }
    
    @Override
    public IntData remove(int index) {
        return new IntData(internal.remove(index));
    }
    
    @Override
    public IData get(int index) {
        return new IntData(internal.get(index));
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
        for(IntNBT nbt : internal) {
            if(first) {
                first = false;
            } else {
                result.append(", ");
            }
            result.append(nbt.getInt());
        }
        result.append(']').append("as int[]");
        return result.toString();
    }
}
