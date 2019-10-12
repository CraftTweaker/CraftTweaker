package com.blamejared.crafttweaker.impl.data;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.data.ICollectionData;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.NBTConverter;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ZenCodeType.Name("crafttweaker.api.data.ListData")
@ZenRegister
public class ListData implements ICollectionData {
    
    private ListNBT internal;
    
    public ListData(ListNBT internal) {
        this.internal = internal;
    }
    
    @ZenCodeType.Constructor
    public ListData() {
        this.internal = new ListNBT();
    }
    
    @ZenCodeType.Constructor
    public ListData(@ZenCodeType.Optional List<IData> list) {
        this.internal = new ListNBT();
        if(list != null)
            list.forEach(iData -> internal.add(iData.getInternal()));
    }
    
    @ZenCodeType.Constructor
    public ListData(@ZenCodeType.Optional IData... array) {
        this(getArraySafe(array));
    }
    
    private static List<IData> getArraySafe(IData... array) {
        if(array == null) {
            array = new IData[0];
        }
        return Arrays.asList(array);
    }
    
    
    @Override
    public IData set(int index, IData value) {
        return NBTConverter.convert(internal.set(index, value.getInternal()));
    }
    
    @Override
    public void add(int index, IData value) {
        internal.add(index, value.getInternal());
    }
    
    @Override
    public void add(IData value) {
        internal.add(value.getInternal());
    }
    
    @Override
    public IData remove(int index) {
        return NBTConverter.convert(internal.remove(index));
    }
    
    @Override
    public IData get(int index) {
        return NBTConverter.convert(internal.get(index));
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
    public IData copy() {
        return new ListData(internal);
    }
    
    @Override
    public INBT getInternal() {
        return internal;
    }
    
    @Override
    public List<IData> asList() {
        List<IData> data = new ArrayList<>();
        for(INBT inbt : internal) {
            data.add(NBTConverter.convert(inbt));
        }
        return data;
    }
    
    @Override
    public boolean contains(IData data) {
        List<IData> dataValues = data.asList();
        if(dataValues != null && containsList(dataValues))
            return true;
        
        for(INBT value : internal) {
            if(NBTConverter.convert(value).contains(data))
                return true;
        }
        
        return false;
    }
    
    private boolean containsList(List<IData> dataValues) {
        outer:
        for(IData dataValue : dataValues) {
            for(INBT value : internal) {
                if(NBTConverter.convert(value).contains(dataValue))
                    continue outer;
            }
            
            return false;
        }
        
        return true;
    }
    
    @Override
    public String asString() {
        StringBuilder output = new StringBuilder();
        output.append('[');
        boolean first = true;
        for(INBT inbt : internal) {
            if(first) {
                first = false;
            } else {
                output.append(", ");
            }
            output.append(NBTConverter.convert(inbt).asString());
        }
        output.append(']');
        output.append(" as IData");
        return output.toString();
    }
    
    @ZenCodeType.Caster(implicit = true)
    public static List<IData> castToMap(ListData data) {
        return data.asList();
    }
}
