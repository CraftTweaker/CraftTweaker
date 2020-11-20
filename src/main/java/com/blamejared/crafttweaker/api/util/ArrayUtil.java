package com.blamejared.crafttweaker.api.util;


import java.util.Arrays;

public final class ArrayUtil {
    
    private ArrayUtil() {
    }
    
    public static <T> T[] mirror(T[] array) {
        if(array == null)
            return null;
        
        final T[] out = Arrays.copyOf(array, array.length);
    
        //We only need half since we go from both ends in the loop
        final int upperIndex = array.length / 2;
        for(int index = 0; index < upperIndex; index++) {
            out[index] = array[array.length - index - 1];
            out[array.length - index - 1] = array[index];
        }
        
        return out;
    }
}
