package com.blamejared.crafttweaker.api.data.base;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.BoolData;
import com.blamejared.crafttweaker.api.data.ByteData;
import com.blamejared.crafttweaker.api.data.IntData;
import com.blamejared.crafttweaker.api.data.ListData;
import com.blamejared.crafttweaker.api.data.base.visitor.DataToStringVisitor;
import com.blamejared.crafttweaker.api.data.base.visitor.DataToTextComponentVisitor;
import com.blamejared.crafttweaker.api.data.base.visitor.DataVisitor;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.nbt.Tag;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;
import java.util.Map;

/**
 * The IData interface is a generic Interface for handling Data like NBT.
 * You can cast about all primitives (short, double, string, int, ...) as well as certain arrays to IData.
 * Remember that while they offer similar features, IData and their counterparts are NOT the same, which is why they will be referred to as DataTypes (e.g. {@link ByteData}).
 *
 * @docParam this {Display: {lore: ["Hello", "World"]}}
 */
@ZenRegister(loaders = {CraftTweakerConstants.DEFAULT_LOADER_NAME, CraftTweakerConstants.TAGS_LOADER_NAME})
@ZenCodeType.Name("crafttweaker.api.data.IData")
@Document("vanilla/api/data/IData")
public interface IData {
    
    /**
     * Gets the ID of the internal NBT tag.
     *
     * Used to determine what NBT type is stored (in a list for example)
     *
     * @return ID of the NBT tag that this data represents.
     */
    @ZenCodeType.Method
    default byte getId() {
        
        return getInternal().getId();
    }
    
    /**
     * Makes a copy of this IData.
     *
     * IData is immutable by default, use this to create a proper copy of the object.
     *
     * @return a copy of this IData.
     */
    @ZenCodeType.Method
    IData copy();
    
    /**
     * Makes a copy of this IData with a copy of the internal INBT object.
     *
     * @return a copy of this IData with a copy of the internal INBT object.
     */
    IData copyInternal();
    
    /**
     * Gets the internal INBT stored in this IData.
     *
     * @return the vanilla INBT tag that this IData represents.
     */
    Tag getInternal();
    
    
    /**
     * Gets the String representation of the internal INBT tag
     *
     * @return String that represents the internal INBT of this IData.
     */
    @ZenCodeType.Method
    default String getString() {
        
        return getInternal().getAsString();
    }
    
    /**
     * Checks if this IData contains another IData, mainly used in subclasses of {@link ICollectionData}, is the same as an equals check on other IData types
     *
     * @param data data to check if it is contained
     *
     * @return true if the given IData is contained in this IData
     *
     * @docParam data "Display"
     */
    @ZenCodeType.Method
    default boolean contains(IData data) {
        
        return getInternal().equals(data.getInternal());
    }
    
    /**
     * Gets a Map<String, IData> representation of this IData, returns null on anything but {@link com.blamejared.crafttweaker.api.data.MapData}.
     *
     * @return null if this IData is not a map.
     */
    @ZenCodeType.Method
    default Map<String, IData> asMap() {
        
        return null;
    }
    
    /**
     * Gets a List<IData> representation of this IData, returns null on anything but {@link ListData}.
     *
     * @return null if this IData is not a list.
     */
    @ZenCodeType.Method
    default List<IData> asList() {
        
        return null;
    }
    
    /**
     * Gets the String representation of this IData
     *
     * @return String that represents this IData (value and type).
     */
    @ZenCodeType.Method
    default String asString() {
        
        return this.accept(DataToStringVisitor.INSTANCE);
    }
    
    @ZenCodeType.Caster
    @ZenCodeType.Method
    default INumberData asNumber() {
        
        if(this instanceof INumberData) {
            return (INumberData) this;
        }
        
        return new IntData(0);
    }
    
    @ZenCodeType.Caster
    @ZenCodeType.Method
    default ICollectionData asCollection() {
        
        if(this instanceof ICollectionData) {
            return (ICollectionData) this;
        }
        
        return new ListData();
    }
    
    
    @ZenCodeType.Caster
    @ZenCodeType.Method
    default boolean asBoolean() {
        
        if(this instanceof BoolData) {
            return ((BoolData) this).getInternalValue();
        } else if(this instanceof ByteData) {
            return ((ByteData) this).getByte() == 1;
        }
        return false;
    }
    
    /**
     * Gets the type of this IData.
     *
     * @return The type of this IData.
     */
    Type getType();
    
    <T> T accept(DataVisitor<T> visitor);
    
    
    /**
     * Used to specify what "type" of IData this is.
     *
     * This is to make it easier to have a Map with an IData based key.
     *
     * See {@link DataToTextComponentVisitor#DATA_TO_COMPONENT}
     */
    enum Type {
        BOOL, BYTE_ARRAY, BYTE, DOUBLE, FLOAT, INT_ARRAY, INT, LIST, LONG_ARRAY, LONG, MAP, SHORT, STRING
    }
    
}
