package com.blamejared.crafttweaker.impl.data;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.data.ICollectionData;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.NBTConverter;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import org.openzen.zencode.java.ZenCodeType;

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
    
    public ListData(List<IData> list) {
        this.internal = new ListNBT();
        list.forEach(iData -> internal.add(iData.getInternal()));
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
        return output.toString();
    }
}
