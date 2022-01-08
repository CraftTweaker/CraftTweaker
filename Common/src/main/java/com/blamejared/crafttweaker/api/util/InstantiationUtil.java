package com.blamejared.crafttweaker.api.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Optional;

public class InstantiationUtil {
    
    /**
     * Tries to get the instance two ways (in this order):
     * 1) Via a public static field (recommended being final as well)
     * 2) Via a public no-arg constructor
     * <p>
     * Does absolutely no caching, so don't call this when not necessary
     *
     * @param cls The class to instantiate
     *
     * @return The instance, or 'null'
     */
    public static <T> T getOrCreateInstance(Class<T> cls) {
        
        try {
            final Optional<Field> any = Arrays.stream(cls.getDeclaredFields())
                    .filter(f -> Modifier.isPublic(f.getModifiers()))
                    .filter(f -> Modifier.isStatic(f.getModifiers()))
                    .filter(f -> f.getType().equals(cls))
                    .findAny();
            if(any.isPresent()) {
                return (T) any.get().get(null);
            }
        } catch(IllegalAccessException ignored) {
        }
        
        try {
            return cls.newInstance();
        } catch(InstantiationException | IllegalAccessException ignored) {
        }
        
        return null;
    }
    
}
