package com.blamejared.crafttweaker.api.data.base.converter;


import com.blamejared.crafttweaker.api.data.ByteData;
import com.blamejared.crafttweaker.api.data.DoubleData;
import com.blamejared.crafttweaker.api.data.FloatData;
import com.blamejared.crafttweaker.api.data.IntData;
import com.blamejared.crafttweaker.api.data.LongData;
import com.blamejared.crafttweaker.api.data.ShortData;
import com.blamejared.crafttweaker.api.data.base.INumberData;

import javax.annotation.Nullable;

public class NumberConverter {
    
    /**
     * Converts the given number to it's IData representation.
     *
     * If the number is not of the following types:
     * {@link Integer}, {@link Byte}, {@link Double}, {@link Float}, {@link Long} or {@link Short},
     * it will be converted to a {@link DoubleData} as that has the most precision.
     *
     * @param number The number to convert
     *
     * @return An IData representation of the given number.
     */
    @Nullable
    public static INumberData convertNumber(Number number) {
        
        if(number == null) {
            return null;
        }
        
        if(number instanceof Integer) {
            return new IntData(number.intValue());
        } else if(number instanceof Byte) {
            return new ByteData(number.byteValue());
        } else if(number instanceof Double) {
            return new DoubleData(number.doubleValue());
        } else if(number instanceof Float) {
            return new FloatData(number.floatValue());
        } else if(number instanceof Long) {
            return new LongData(number.longValue());
        } else if(number instanceof Short) {
            return new ShortData(number.shortValue());
        }
        
        //Fallback
        return new DoubleData(number.doubleValue());
    }
    
}
