package com.blamejared.crafttweaker.api.data;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.nbt.NumberNBT;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Represents a Number in the form of an {@link IData}, useful for converting between types (double to int / long for example).
 * @docParam this 1
 */
@Document("vanilla/data/INumberData")
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.data.INumberData")
public interface INumberData extends IData {
    
    /**
     * Gets the value of this {@link IData} as a long
     *
     * @return the long value of this {@link IData}
     */
    default long getLong() {
        return getInternal().getLong();
    }
    
    /**
     * Gets the value of this {@link IData} as an int
     *
     * @return the int value of this {@link IData}
     */
    default int getInt() {
        return getInternal().getInt();
    }
    
    /**
     * Gets the value of this {@link IData} as a short
     *
     * @return the short value of this {@link IData}
     */
    default short getShort() {
        return getInternal().getShort();
    }
    
    /**
     * Gets the value of this {@link IData} as a byte
     *
     * @return the byte value of this {@link IData}
     */
    default byte getByte() {
        return getInternal().getByte();
    }
    
    /**
     * Gets the value of this {@link IData} as a double
     *
     * @return the double value of this {@link IData}
     */
    default double getDouble() {
        return getInternal().getDouble();
    }
    
    /**
     * Gets the value of this {@link IData} as a float
     *
     * @return the float value of this {@link IData}
     */
    default float getFloat() {
        return getInternal().getFloat();
    }
    
    @Override
    NumberNBT getInternal();
}
