package com.blamejared.crafttweaker.api.data;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

/**
 * The ICollection data is used to represent a collection of IData like a List<IData>
 *
 * @docParam this new ListData(["Hello", "World"])
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.data.ICollectionData")
@Document("vanilla/data/ICollectionData")
public interface ICollectionData extends IData {

    /**
     * Sets the item at the provided index to the given value
     * @param index The index to set (0-based)
     * @docParam index 0
     * @param value The new Value
     * @docParam value "Bye"
     * @return The replaced value
     */
    @ZenCodeType.Method
    IData set(int index, IData value);

    /**
     * @param index The index to add to. Subsequent items will be moved one index higher
     * @docParam index 1
     * @param value The value to add to the list
     * @docParam value "beautiful"
     */
    @ZenCodeType.Method
    void add(int index, IData value);

    /**
     * @param value The value to add to the list
     * @docParam value "today"
     */
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
