package com.blamejared.crafttweaker.api.data;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * The ICollection data is used to represent a collection of {@link IData} like a List<IData>
 *
 * @docParam this new ListData(["Hello", "World"])
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.data.ICollectionData")
@Document("vanilla/api/data/ICollectionData")
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
    IData setAt(int index, IData value);

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

    /**
     * Removes the {@link IData} stored at the given index.
     * @param index The index (0-based)
     * @docParam index 0
     * @return The {@link IData} that was removed
     */
    @ZenCodeType.Method
    IData remove(int index);

    /**
     * Retrieves the {@link IData} stored at the given index.
     * @param index The index (0-based)
     * @docParam index 0
     * @return The {@link IData}
     */
    @ZenCodeType.Method
    IData getAt(int index);
    
    @ZenCodeType.Getter("size")
    int size();

    /**
     * Removes every element in the list
     */
    @ZenCodeType.Method
    void clear();

    @Override
    default String toJsonString() {
        return this.asList().stream().map(IData::toJsonString).collect(Collectors.joining(",", "[", "]"));
    }

    //    @ZenCodeType.Caster(implicit = true)
//    default IData asIData(){
//        return this;
//    }
}
