package com.blamejared.crafttweaker.api.data;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.converter.tag.TagToDataConverter;
import com.blamejared.crafttweaker.api.data.visitor.DataVisitor;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import org.jetbrains.annotations.NotNull;
import org.openzen.zencode.java.ZenCodeType;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @docParam this ["Hello", "World", "!"] as IData
 */
@ZenCodeType.Name("crafttweaker.api.data.ListData")
@ZenRegister
@Document("vanilla/api/data/ListData")
public class ListData implements IData {
    
    private final ListTag internal;
    
    public ListData(ListTag internal) {
        
        this.internal = internal;
    }
    
    @ZenCodeType.Constructor
    public ListData() {
        
        this.internal = new ListTag();
    }
    
    @ZenCodeType.Constructor
    public ListData(List<IData> list) {
        
        this.internal = new ListTag();
        if(list != null) {
            list.forEach(iData -> getInternal().add(iData.getInternal()));
        }
    }
    
    @ZenCodeType.Constructor
    public ListData(IData[] array) {
        //TODO 1.19 confirm
        this(Arrays.asList(array));
    }
    
    @Override
    public ListTag getInternal() {
        
        return internal;
    }
    
    @Override
    public IData add(IData other) {
        
        getInternal().add(other.getInternal());
        return this;
    }
    
    @Override
    public void put(String index, IData value) {
        
        try {
            getInternal().setTag(Integer.parseInt(index), value.getInternal());
        } catch(NumberFormatException e) {
            throw new IllegalArgumentException("Provided index: '%s' is not an Integer!".formatted(index));
        }
    }
    
    @Override
    public void remove(int index) {
        
        getInternal().remove(index);
    }
    
    @Override
    public IData getAt(int index) {
        
        return TagToDataConverter.convert(getInternal().get(index));
    }
    
    @Override
    public boolean contains(IData other) {
        
        if(other.isListable()) {
            List<IData> dataValues = other.asList();
            if(dataValues != null && containsList(dataValues)) {
                return true;
            }
        }
        
        for(Tag value : getInternal()) {
            if(TagToDataConverter.convert(value).contains(other)) {
                return true;
            }
        }
        
        return false;
    }
    
    private boolean containsList(List<IData> dataValues) {
        
        outer:
        for(IData dataValue : dataValues) {
            for(Tag value : getInternal()) {
                if(TagToDataConverter.convert(value).contains(dataValue)) {
                    continue outer;
                }
            }
            
            return false;
        }
        
        return true;
    }
    
    @Override
    public boolean equalTo(IData other) {
        
        List<IData> values = asList();
        List<IData> others = other.asList();
        if(values.size() != others.size()) {
            return false;
        }
        
        for(int i = 0; i < values.size(); i++) {
            if(!values.get(i).equalTo(others.get(i))) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public List<IData> asList() {
        
        return getInternal().stream().map(TagToDataConverter::convert).toList();
    }
    
    @Override
    public boolean isListable() {
        
        return true;
    }
    
    @Override
    public byte[] asByteArray() {
        
        List<IData> values = asList();
        byte[] ret = new byte[values.size()];
        for(int i = 0; i < values.size(); i++) {
            ret[i] = values.get(i).asByte();
        }
        return ret;
    }
    
    @Override
    public int[] asIntArray() {
        
        List<IData> values = asList();
        int[] ret = new int[values.size()];
        for(int i = 0; i < values.size(); i++) {
            ret[i] = values.get(i).asInt();
        }
        return ret;
    }
    
    @Override
    public long[] asLongArray() {
        
        List<IData> values = asList();
        long[] ret = new long[values.size()];
        for(int i = 0; i < values.size(); i++) {
            ret[i] = values.get(i).asLong();
        }
        return ret;
    }
    
    @Override
    public int length() {
        
        return asList().size();
    }
    
    @Override
    public Set<String> getKeys() {
        
        return IntStream.range(0, length()).mapToObj(String::valueOf).collect(Collectors.toSet());
    }
    
    @Override
    public @NotNull Iterator<IData> iterator() {
        
        return asList().iterator();
    }
    
    @Override
    public IData copy() {
        
        return new ListData(getInternal());
    }
    
    @Override
    public IData copyInternal() {
        
        return new ListData(getInternal().copy());
    }
    
    @Override
    public <T> T accept(DataVisitor<T> visitor) {
        
        return visitor.visitList(this);
    }
    
    @Override
    public Type getType() {
        
        return Type.LIST;
    }
    
    @Override
    public boolean equals(Object o) {
        
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        ListData iData = (ListData) o;
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
