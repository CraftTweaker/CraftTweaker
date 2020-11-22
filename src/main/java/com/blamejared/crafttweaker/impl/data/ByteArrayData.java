package com.blamejared.crafttweaker.impl.data;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.data.ICollectionData;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.INumberData;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.nbt.ByteArrayNBT;
import net.minecraft.nbt.ByteNBT;
import net.minecraft.nbt.NumberNBT;
import org.openzen.zencode.java.ZenCodeType;

import java.util.*;
import java.util.stream.*;

/**
 * @docParam this [4, 1, 2]
 */
@ZenCodeType.Name("crafttweaker.api.data.ByteArrayData")
@ZenRegister
@Document("vanilla/api/data/ByteArrayData")
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
    public IData copyInternal() {
        return new ByteArrayData((ByteArrayNBT) getInternal().copy());
    }
    
    @Override
    public ByteArrayNBT getInternal() {
        return internal;
    }
    
    @Override
    public IData setAt(int index, IData value) {
        if(value instanceof NumberNBT) {
            return new ByteData(internal.set(index, ByteNBT.valueOf(((INumberData) value).getByte())));
        } else {
            return null;
        }
    }
    
    
    @Override
    public void add(int index, IData value) {
        if(value instanceof INumberData) {
            internal.add(index, ByteNBT.valueOf(((INumberData) value).getByte()));
        }
    }
    
    @Override
    public void add(IData value) {
        if(value instanceof INumberData) {
            internal.add(ByteNBT.valueOf(((INumberData) value).getByte()));
        }
    }
    
    @Override
    public IData remove(int index) {
        return new ByteData(internal.remove(index));
    }
    
    @Override
    public IData getAt(int index) {
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
    
    @Override
    public String asString() {
        StringBuilder result = new StringBuilder();
        result.append("[");
        boolean first = true;
        for(ByteNBT nbt : internal) {
            if(first)
                first = false;
            else
                result.append(", ");
            
            result.append(nbt.getByte());
        }
        result.append("]");
        return result.toString();
    }
    
    @Override
    public List<IData> asList() {
        final byte[] byteArray = internal.getByteArray();
        final List<IData> out = new ArrayList<>(byteArray.length);
        for(byte b : byteArray) {
            out.add(new ByteData(b));
        }
        return out;
    }
}
