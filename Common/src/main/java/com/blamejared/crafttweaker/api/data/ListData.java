package com.blamejared.crafttweaker.api.data;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.base.ICollectionData;
import com.blamejared.crafttweaker.api.data.base.IData;
import com.blamejared.crafttweaker.api.data.base.converter.tag.TagToDataConverter;
import com.blamejared.crafttweaker.api.data.base.visitor.DataVisitor;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import org.openzen.zencode.java.ZenCodeType;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @docParam this ["Hello", "World", "!"]
 */
@ZenCodeType.Name("crafttweaker.api.data.ListData")
@ZenRegister
@Document("vanilla/api/data/ListData")
public class ListData implements ICollectionData {
    
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
    public ListData(IData... array) {
        
        this(getArraySafe(array));
    }
    
    @ZenCodeType.Method
    public static ListData create() {
        return new ListData();
    }
    
    private static List<IData> getArraySafe(IData... array) {
        
        if(array == null) {
            array = new IData[0];
        }
        return Arrays.asList(array);
    }
    
    
    @Override
    public IData setAt(int index, IData value) {
        
        return TagToDataConverter.convert(getInternal().set(index, value.getInternal()));
    }
    
    @Override
    public void add(int index, IData value) {
        
        getInternal().add(index, value.getInternal());
    }
    
    @Override
    public void add(IData value) {
        
        getInternal().add(value.getInternal());
    }
    
    @Override
    public IData remove(int index) {
        
        return TagToDataConverter.convert(getInternal().remove(index));
    }
    
    @Override
    public IData getAt(int index) {
        
        return TagToDataConverter.convert(getInternal().get(index));
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Nullable
    public <T extends IData> T getData(Class<T> clazz, int index) {
        
        try {
            return TagToDataConverter.convertTo(getInternal().get(index), clazz);
        } catch(NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Unable to convert IData to " + clazz, e);
        }
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
    public ListData copy() {
        
        return new ListData(getInternal());
    }
    
    @Override
    public ListData copyInternal() {
        
        return new ListData(getInternal().copy());
    }
    
    @Override
    public ListTag getInternal() {
        
        return internal;
    }
    
    @Override
    public List<IData> asList() {
        
        List<IData> data = new ArrayList<>();
        for(Tag inbt : getInternal()) {
            data.add(TagToDataConverter.convert(inbt));
        }
        return data;
    }
    
    @Override
    public boolean contains(IData data) {
        
        List<IData> dataValues = data.asList();
        if(dataValues != null && containsList(dataValues)) {
            return true;
        }
        
        for(Tag value : getInternal()) {
            if(TagToDataConverter.convert(value).contains(data)) {
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
    
    @ZenCodeType.Caster(implicit = true)
    public List<IData> castToList() {
        
        return this.asList();
    }
    
    @Override
    public Type getType() {
        
        return Type.LIST;
    }
    
    @Override
    public <T> T accept(DataVisitor<T> visitor) {
        
        return visitor.visitList(this);
    }
    
    @Override
    public boolean equals(Object o) {
        
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        
        ListData listData = (ListData) o;
        
        return internal.equals(listData.internal);
    }
    
    @Override
    public int hashCode() {
        
        return internal.hashCode();
    }
    
}
