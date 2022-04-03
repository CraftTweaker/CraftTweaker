package com.blamejared.crafttweaker.api.util;

public class GenericUtil {
    
    public static <T> T uncheck(final Object o) {
        
        return (T) o;
    }
    
    // Don't ask, this makes the compiler shut up
    public static <T> Class<? super T> castToSuperExplicitly(final Class<T> t) {
        
        return t;
    }
    
}
