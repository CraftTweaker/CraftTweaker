package com.blamejared.crafttweaker.api.util;

import java.util.function.Function;

public class GenericUtil {
    
    @SuppressWarnings("unchecked")
    public static <T> T uncheck(final Object o) {
        
        return (T) o;
    }
    
    @SuppressWarnings("unchecked")
    public static <T, U, V> Function<U, T> uncheckFunc(final Function<U, V> func) {
        
        return (Function<U, T>) func;
    }
    
}
