package com.blamejared.crafttweaker.api.data;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.converter.tag.TagToDataConverter;
import com.blamejared.crafttweaker.api.data.visitor.DataVisitor;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.IntTag;
import org.jetbrains.annotations.NotNull;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @docParam this ([4, 128, 256, 1024] as IData)
 */
@ZenCodeType.Name("crafttweaker.api.data.IntArrayData")
@ZenRegister
@Document("vanilla/api/data/IntArrayData")
public class IntArrayData implements IData {
    
    private final IntArrayTag internal;
    
    public IntArrayData(IntArrayTag internal) {
        
        this.internal = internal;
    }
    
    @ZenCodeType.Constructor
    public IntArrayData(int[] internal) {
        
        this.internal = new IntArrayTag(internal);
    }
    
    @Override
    public void put(String index, IData value) {
        try {
            getInternal().setTag(Integer.parseInt(index), IntTag.valueOf(value.asInt()));
        } catch(NumberFormatException e) {
            throw new IllegalArgumentException("Provided index: '%s' is not an Integer!".formatted(index));
        }
    }
    
    @Override
    public IData getAt(int index) {
        
        return TagToDataConverter.convert(getInternal().get(index));
    }
    
    @Override
    public void remove(int index) {
        
        this.getInternal().remove(index);
    }
    
    @Override
    public boolean contains(IData other) {
    
        if(other.isListable()) {
            List<IData> dataValues = other.asList();
            return dataValues != null && containsList(dataValues);
        }
        
        return getInternal().contains(IntTag.valueOf(other.asInt()));
    }
    
    @Override
    public int compareTo(@NotNull IData other) {
        
        return Arrays.compare(asIntArray(), other.asIntArray());
    }
    
    @Override
    public boolean equalTo(IData other) {
        
        return Arrays.equals(asIntArray(), other.asIntArray());
    }
    
    @Override
    public List<IData> asList() {
        
        return getInternal().stream().map(IntData::new).collect(Collectors.toList());
    }
    
    @Override
    public boolean isListable() {
        
        return true;
    }
    
    @Override
    public byte[] asByteArray() {
        
        int[] ints = asIntArray();
        byte[] bytes = new byte[ints.length];
        for(int i = 0; i < ints.length; i++) {
            bytes[i] = (byte) ints[i];
        }
        return bytes;
    }
    
    @Override
    public int[] asIntArray() {
        
        return getInternal().getAsIntArray();
    }
    
    @Override
    public long[] asLongArray() {
        
        int[] ints = asIntArray();
        long[] longs = new long[ints.length];
        for(int i = 0; i < ints.length; i++) {
            longs[i] = ints[i];
        }
        return longs;
    }
    
    @Override
    public int length() {
        
        return getInternal().size();
    }
    
    @Override
    public @NotNull Iterator<IData> iterator() {
        
        return asList().iterator();
    }
    
    @Override
    public IntArrayTag getInternal() {
        
        return internal;
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
    public <T> T accept(DataVisitor<T> visitor) {
        
        return visitor.visitIntArray(this);
    }
    
    @Override
    public Type getType() {
        
        return Type.INT_ARRAY;
    }
    
    @Override
    public boolean equals(Object o) {
        
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        IntArrayData iData = (IntArrayData) o;
        return Objects.equals(getInternal(), iData.getInternal());
    }
    
    @Override
    public int hashCode() {
        
        return Objects.hash(getInternal());
    }
    
    @Override
    public String toString() {
        
        return getAsString();
    }
    
}
