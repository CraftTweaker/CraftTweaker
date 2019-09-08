package com.blamejared.crafttweaker.api.data;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import net.minecraft.nbt.INBT;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;
import java.util.Map;

@ZenCodeType.Name("crafttweaker.api.data.IData")
@ZenRegister
public interface IData {
    
    @ZenCodeType.Method
    default byte getId() {
        return getInternal().getId();
    }
    
    @ZenCodeType.Method
    IData copy();
    
    INBT getInternal();
    
    
    @ZenCodeType.Method
    default String getString() {
        return getInternal().toString();
    }
    
    
    @ZenCodeType.Method
    default boolean contains(IData data) {
        return getInternal().equals(data.getInternal());
    }
    
    @ZenCodeType.Method
    default Map<String, IData> asMap() {
        return null;
    }
    
    @ZenCodeType.Method
    default List<IData> asList() {
        return null;
    }
    
    @ZenCodeType.Method
    String asString();
    
}
