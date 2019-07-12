package com.blamejared.crafttweaker.api.nbt;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.tag.ICollectionData")
public interface ICollectionData extends IData {
    
    @ZenCodeType.Method
    IData set(int index, IData value);
    
    @ZenCodeType.Method
    void add(int index, IData value);
    
    @ZenCodeType.Method
    IData remove(int index);
    
    @ZenCodeType.Method
    IData get(int index);
    
    @ZenCodeType.Getter("size")
    int size();
    
    @ZenCodeType.Method
    void clear();
}
