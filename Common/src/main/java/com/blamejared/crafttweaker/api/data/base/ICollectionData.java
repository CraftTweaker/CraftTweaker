package com.blamejared.crafttweaker.api.data.base;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.*;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;

/**
 * The ICollection data is used to represent a collection of {@link IData} like a List<IData>
 *
 * @docParam this new ListData(["Hello", "World"])
 */
@ZenRegister(loaders = {CraftTweakerConstants.DEFAULT_LOADER_NAME, CraftTweakerConstants.TAGS_LOADER_NAME})
@ZenCodeType.Name("crafttweaker.api.data.ICollectionData")
@Document("vanilla/api/data/ICollectionData")
public interface ICollectionData extends IData {
    
    @Override
    ICollectionData copy();
    
    @Override
    ICollectionData copyInternal();
    
    /**
     * Creates the most specific ICollectionData form possible for the provided members.
     *
     * Tries to return (in order) {@link ByteArrayData}, {@link IntArrayData}, {@link LongArrayData} or if neither is applicable {@link ListData}
     */
    @ZenCodeType.Method
    static ICollectionData getFromMembers(IData... members) {
        
        if(members == null || members.length == 0) {
            return new ListData();
        }
        
        if(Arrays.stream(members).allMatch(member -> member instanceof ByteData)) {
            byte[] result = new byte[members.length];
            for(int i = 0; i < members.length; i++) {
                result[i] = members[i].asNumber().getByte();
            }
            return new ByteArrayData(result);
        }
        
        if(Arrays.stream(members).allMatch(member -> member instanceof ByteData || member instanceof IntData)) {
            int[] result = new int[members.length];
            for(int i = 0; i < members.length; i++) {
                result[i] = members[i].asNumber().getInt();
            }
            return new IntArrayData(result);
        }
        
        if(Arrays.stream(members)
                .allMatch(member -> member instanceof ByteData || member instanceof IntData || member instanceof LongData)) {
            long[] result = new long[members.length];
            for(int i = 0; i < members.length; i++) {
                result[i] = members[i].asNumber().getLong();
            }
            return new LongArrayData(result);
        }
        
        return new ListData(members);
    }
    
    /**
     * Sets the item at the provided index to the given value
     *
     * @param index The index to set (0-based)
     * @param value The new Value
     *
     * @return The replaced value
     *
     * @docParam index 0
     * @docParam value "Bye"
     */
    @ZenCodeType.Method
    IData setAt(int index, IData value);
    
    /**
     * @param index The index to add to. Subsequent items will be moved one index higher
     * @param value The value to add to the list
     *
     * @docParam index 1
     * @docParam value "beautiful"
     */
    @ZenCodeType.Method
    void add(int index, IData value);
    
    /**
     * @param value The value to add to the list
     *
     * @docParam value "today"
     */
    @ZenCodeType.Method
    void add(IData value);
    
    /**
     * Removes the {@link IData} stored at the given index.
     *
     * @param index The index (0-based)
     *
     * @return The {@link IData} that was removed
     *
     * @docParam index 0
     */
    @ZenCodeType.Method
    IData remove(int index);
    
    /**
     * Retrieves the {@link IData} stored at the given index.
     *
     * @param index The index (0-based)
     *
     * @return The {@link IData}
     *
     * @docParam index 0
     */
    @ZenCodeType.Method
    IData getAt(int index);
    
    @ZenCodeType.Getter("size")
    int size();
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("empty")
    boolean isEmpty();
    
    /**
     * Removes every element in the list
     */
    @ZenCodeType.Method
    void clear();
    
}
