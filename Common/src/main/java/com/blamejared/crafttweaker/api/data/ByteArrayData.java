package com.blamejared.crafttweaker.api.data;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.base.ICollectionData;
import com.blamejared.crafttweaker.api.data.base.IData;
import com.blamejared.crafttweaker.api.data.base.INumberData;
import com.blamejared.crafttweaker.api.data.base.visitor.DataVisitor;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.nbt.ByteArrayTag;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.NumericTag;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.List;

/**
 * @docParam this [4, 1, 2]
 */
@ZenCodeType.Name("crafttweaker.api.data.ByteArrayData")
@ZenRegister
@Document("vanilla/api/data/ByteArrayData")
public class ByteArrayData implements ICollectionData {
    
    private final ByteArrayTag internal;
    
    public ByteArrayData(ByteArrayTag internal) {
        
        this.internal = internal;
    }
    
    @ZenCodeType.Constructor
    public ByteArrayData(byte[] internal) {
        
        this.internal = new ByteArrayTag(internal);
    }
    
    @Override
    public ByteArrayData copy() {
        
        return new ByteArrayData(getInternal());
    }
    
    @Override
    public ByteArrayData copyInternal() {
        
        return new ByteArrayData((ByteArrayTag) getInternal().copy());
    }
    
    @Override
    public ByteArrayTag getInternal() {
        
        return internal;
    }
    
    @Override
    public IData setAt(int index, IData value) {
        
        if(value instanceof NumericTag) {
            return new ByteData(getInternal().set(index, ByteTag.valueOf(((INumberData) value).getByte())));
        } else {
            return null;
        }
    }
    
    
    @Override
    public void add(int index, IData value) {
        
        if(value instanceof INumberData) {
            getInternal().add(index, ByteTag.valueOf(((INumberData) value).getByte()));
        }
    }
    
    @Override
    public void add(IData value) {
        
        if(value instanceof INumberData) {
            getInternal().add(ByteTag.valueOf(((INumberData) value).getByte()));
        }
    }
    
    @Override
    public IData remove(int index) {
        
        return new ByteData(getInternal().remove(index));
    }
    
    @Override
    public IData getAt(int index) {
        
        return new ByteData(getInternal().get(index));
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
    public Type getType() {
        
        return Type.BYTE_ARRAY;
    }
    
    @Override
    public <T> T accept(DataVisitor<T> visitor) {
        
        return visitor.visitByteArray(this);
    }
    
    @Override
    public List<IData> asList() {
        
        final byte[] byteArray = getInternal().getAsByteArray();
        final List<IData> out = new ArrayList<>(byteArray.length);
        for(byte b : byteArray) {
            out.add(new ByteData(b));
        }
        return out;
    }
    
    @Override
    public boolean equals(Object o) {
        
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        
        ByteArrayData that = (ByteArrayData) o;
        
        return internal.equals(that.internal);
    }
    
    @Override
    public int hashCode() {
        
        return internal.hashCode();
    }
    
}
