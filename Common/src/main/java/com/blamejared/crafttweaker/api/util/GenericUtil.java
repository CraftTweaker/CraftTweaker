package com.blamejared.crafttweaker.api.util;

public class GenericUtil {
    
    @SuppressWarnings("unchecked")
    public static <T> T uncheck(final Object o) {
        
        return (T) o;
    }
    
}
