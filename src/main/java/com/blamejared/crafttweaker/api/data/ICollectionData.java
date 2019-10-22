package com.blamejared.crafttweaker.api.data;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

/**
 * The ICollection data is used to represent a collection of IData like a List<IData>
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.data.ICollectionData")
@Document("vanilla/data/ICollectionData")
public interface ICollectionData extends IData {
    
    @ZenCodeType.Method
    IData set(int index, IData value);
    
    @ZenCodeType.Method
    void add(int index, IData value);
    
    @ZenCodeType.Method
    void add(IData value);
    
    @ZenCodeType.Method
    IData remove(int index);
    
    @ZenCodeType.Method
    IData get(int index);
    
    @ZenCodeType.Getter("size")
    int size();
    
    @ZenCodeType.Method
    void clear();
    
//    @ZenCodeType.Caster(implicit = true)
//    default IData asIData(){
//        return this;
//    }
}
