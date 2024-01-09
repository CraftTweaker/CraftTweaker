package com.blamejared.crafttweaker.api.util;

import net.minecraft.core.NonNullList;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Supplier;

public final class ArrayUtil {
    
    private ArrayUtil() {
    
    }
    
    public static <T> T[] copy(T[] array) {
        
        return Arrays.copyOf(array, array.length);
    }
    
    public static <T> T[] mirror(T[] array) {
        
        if(array == null) {
            return null;
        }
        
        final T[] out = Arrays.copyOf(array, array.length);
        
        //We only need half since we go from both ends in the loop
        final int upperIndex = array.length / 2;
        for(int index = 0; index < upperIndex; index++) {
            out[index] = array[array.length - index - 1];
            out[array.length - index - 1] = array[index];
        }
        
        return out;
    }
    
    public static <T> T[] copyOf(T[] original, int newLength, T defaultValue) {
        
        T[] arr = Arrays.copyOf(original, newLength);
        replaceNulls(arr, defaultValue);
        return arr;
    }
    
    public static <T> void replaceNulls(T[] arr, T defaultValue) {
        
        for(int i = 0; i < arr.length; i++) {
            if(arr[i] == null) {
                arr[i] = defaultValue;
            }
        }
    }
    
    public static <T> int getMaxWidth(T[][] array) {
        
        return Arrays.stream(array)
                .mapToInt(row -> row.length)
                .max()
                .orElse(0);
    }
    
    public static <T, U> NonNullList<U> flattenToNNL(T[][] array, Supplier<U> empty, Function<T, U> converter) {
        
        int width = ArrayUtil.getMaxWidth(array);
        int height = array.length;
        return flattenToNNL(width, height, array, empty, converter);
    }
    
    public static <T, U> NonNullList<U> flattenToNNL(int width, int height, T[][] array, Supplier<U> empty, Function<T, U> converter) {
        
        NonNullList<U> ingredients = NonNullList.withSize(height * width, empty.get());
        for(int row = 0; row < array.length; row++) {
            T[] ingredientRow = array[row];
            for(int column = 0; column < ingredientRow.length; column++) {
                ingredients.set(row * width + column, converter.apply(ingredientRow[column]));
            }
        }
        return ingredients;
    }
    
}
