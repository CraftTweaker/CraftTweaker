package com.blamejared.crafttweaker.api.data;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.converter.tag.TagToDataConverter;
import com.blamejared.crafttweaker.api.data.visitor.DataVisitor;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.nbt.LongArrayTag;
import net.minecraft.nbt.LongTag;
import org.jetbrains.annotations.NotNull;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @docParam this ([100000, 800000, 50000] as IData)
 */
@ZenCodeType.Name("crafttweaker.api.data.LongArrayData")
@ZenRegister
@Document("vanilla/api/data/LongArrayData")
public class LongArrayData implements IData {
    
    private final LongArrayTag internal;
    
    public LongArrayData(LongArrayTag internal) {
        
        this.internal = internal;
    }
    
    @ZenCodeType.Constructor
    public LongArrayData(long[] internal) {
        
        this.internal = new LongArrayTag(internal);
    }
    
    @Override
    public LongArrayTag getInternal() {
        
        return internal;
    }
    
    @Override
    public void put(String index, IData value) {
        
        try {
            getInternal().setTag(Integer.parseInt(index), LongTag.valueOf(value.asLong()));
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
    
        return getInternal().contains(LongTag.valueOf(other.asLong()));
    }
    
    @Override
    public int compareTo(@NotNull IData other) {
        
        return Arrays.compare(asLongArray(), other.asLongArray());
    }
    
    @Override
    public boolean equalTo(IData other) {
        
        return Arrays.equals(asLongArray(), other.asLongArray());
    }
    
    @Override
    public List<IData> asList() {
        
        return getInternal().stream().map(TagToDataConverter::convert).collect(Collectors.toList());
    }
    
    @Override
    public boolean isListable() {
        
        return true;
    }
    
    @Override
    public byte[] asByteArray() {
        
        long[] longs = asLongArray();
        byte[] bytes = new byte[longs.length];
        for(int i = 0; i < longs.length; i++) {
            bytes[i] = (byte) longs[i];
        }
        return bytes;
    }
    
    @Override
    public int[] asIntArray() {
        
        long[] longs = asLongArray();
        int[] ints = new int[longs.length];
        for(int i = 0; i < longs.length; i++) {
            ints[i] = (int) longs[i];
        }
        return ints;
    }
    
    @Override
    public long[] asLongArray() {
        
        return getInternal().getAsLongArray();
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
    public IData copy() {
        
        return new LongArrayData(getInternal());
    }
    
    @Override
    public IData copyInternal() {
        
        return new LongArrayData(getInternal().copy());
    }
    
    @Override
    public <T> T accept(DataVisitor<T> visitor) {
        
        return visitor.visitLongArray(this);
    }
    
    @Override
    public Type getType() {
        
        return Type.LONG_ARRAY;
    }
    
    @Override
    public boolean equals(Object o) {
        
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        LongArrayData iData = (LongArrayData) o;
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
