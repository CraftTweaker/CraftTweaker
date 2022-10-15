package com.blamejared.crafttweaker.api.data;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.converter.tag.TagToDataConverter;
import com.blamejared.crafttweaker.api.data.visitor.DataVisitor;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.nbt.ByteArrayTag;
import net.minecraft.nbt.ByteTag;
import org.jetbrains.annotations.NotNull;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @docParam this ([4, 1, 2] as IData)
 */
@ZenCodeType.Name("crafttweaker.api.data.ByteArrayData")
@ZenRegister
@Document("vanilla/api/data/ByteArrayData")
public class ByteArrayData implements IData {
    
    private final ByteArrayTag internal;
    
    public ByteArrayData(ByteArrayTag internal) {
        
        this.internal = internal;
    }
    
    @ZenCodeType.Constructor
    public ByteArrayData(byte[] internal) {
        
        this.internal = new ByteArrayTag(internal);
    }
    
    @Override
    public void put(String index, IData value) {
        try {
            getInternal().setTag(Integer.parseInt(index), ByteTag.valueOf(value.asByte()));
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
        
        return getInternal().contains(ByteTag.valueOf(other.asByte()));
    }
    
    @Override
    public int compareTo(@NotNull IData other) {
        
        return Arrays.compare(asByteArray(), other.asByteArray());
    }
    
    @Override
    public boolean equalTo(IData other) {
        
        return Arrays.equals(asByteArray(), other.asByteArray());
    }
    
    @Override
    public List<IData> asList() {
        
        return getInternal().stream().map(ByteData::new).collect(Collectors.toList());
    }
    
    @Override
    public boolean isListable() {
        
        return true;
    }
    
    @Override
    public byte[] asByteArray() {
        
        return getInternal().getAsByteArray();
    }
    
    @Override
    public int[] asIntArray() {
        
        byte[] bytes = asByteArray();
        int[] ints = new int[bytes.length];
        for(int i = 0; i < bytes.length; i++) {
            ints[i] = bytes[i];
        }
        return ints;
    }
    
    @Override
    public long[] asLongArray() {
        
        byte[] bytes = asByteArray();
        long[] longs = new long[bytes.length];
        for(int i = 0; i < bytes.length; i++) {
            longs[i] = bytes[i];
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
    public ByteArrayTag getInternal() {
        
        return internal;
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
    public <T> T accept(DataVisitor<T> visitor) {
        
        return visitor.visitByteArray(this);
    }
    
    @Override
    public Type getType() {
        
        return Type.BYTE_ARRAY;
    }
    
    @Override
    public boolean equals(Object o) {
        
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        ByteArrayData iData = (ByteArrayData) o;
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
