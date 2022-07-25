package com.blamejared.crafttweaker.api.data.base;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.nbt.NumericTag;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Represents a Number in the form of an {@link IData}, useful for converting between types (double to int / long for example).
 *
 * @docParam this 1
 */
@ZenRegister(loaders = {CraftTweakerConstants.DEFAULT_LOADER_NAME, CraftTweakerConstants.TAGS_LOADER_NAME})
@Document("vanilla/api/data/INumberData")
@ZenCodeType.Name("crafttweaker.api.data.INumberData")
public interface INumberData extends IData {
    
    @Override
    INumberData copy();
    
    @Override
    INumberData copyInternal();
    
    @Override
    NumericTag getInternal();
    
    /**
     * Gets the value of this {@link IData} as a long
     *
     * @return the long value of this {@link IData}
     */
    @ZenCodeType.Method
    @ZenCodeType.Caster
    default long getLong() {
        
        return getInternal().getAsLong();
    }
    
    /**
     * Gets the value of this {@link IData} as an int
     *
     * @return the int value of this {@link IData}
     */
    @ZenCodeType.Method
    @ZenCodeType.Caster
    default int getInt() {
        
        return getInternal().getAsInt();
    }
    
    /**
     * Gets the value of this {@link IData} as a short
     *
     * @return the short value of this {@link IData}
     */
    @ZenCodeType.Method
    @ZenCodeType.Caster
    default short getShort() {
        
        return getInternal().getAsShort();
    }
    
    /**
     * Gets the value of this {@link IData} as a byte
     *
     * @return the byte value of this {@link IData}
     */
    @ZenCodeType.Method
    @ZenCodeType.Caster
    default byte getByte() {
        
        return getInternal().getAsByte();
    }
    
    /**
     * Gets the value of this {@link IData} as a double
     *
     * @return the double value of this {@link IData}
     */
    @ZenCodeType.Method
    @ZenCodeType.Caster
    default double getDouble() {
        
        return getInternal().getAsDouble();
    }
    
    /**
     * Gets the value of this {@link IData} as a float
     *
     * @return the float value of this {@link IData}
     */
    @ZenCodeType.Method
    @ZenCodeType.Caster
    default float getFloat() {
        
        return getInternal().getAsFloat();
    }
    
}
