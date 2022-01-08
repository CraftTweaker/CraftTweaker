package com.blamejared.crafttweaker.api.zencode.impl.registry;

import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Contains all info on Bracket resolvers, validators and dumpers
 */
public class BracketEnumRegistry {
    
    private final Map<ResourceLocation, Class<Enum<?>>> enums = new HashMap<>();
    
    public void addClasses(final List<Class<?>> clsList) {
        
        for(Class<?> cls : clsList) {
            if(cls.isEnum()) {
                if(cls.isAnnotationPresent(BracketEnum.class)) {
                    
                    String value = cls.getAnnotation(BracketEnum.class).value();
                    ResourceLocation rl = ResourceLocation.tryParse(value);
                    
                    if(rl == null) {
                        throw new IllegalArgumentException("Provided resource location ('" + value + "') for enum: " + cls + " is not a valid resource location!");
                    }
                    enums.put(rl, (Class<Enum<?>>) cls);
                }
            } else {
                if(cls.isAnnotationPresent(NativeTypeRegistration.class) && cls.isAnnotationPresent(BracketEnum.class)) {
                    NativeTypeRegistration annotation = cls.getAnnotation(NativeTypeRegistration.class);
                    Class<?> nativeType = annotation.value();
                    if(nativeType.isEnum()) {
                        String value = cls.getAnnotation(BracketEnum.class).value();
                        ResourceLocation rl = ResourceLocation.tryParse(value);
                        
                        if(rl == null) {
                            throw new IllegalArgumentException("Provided resource location ('" + value + "') for enum: " + cls + " is not a valid resource location!");
                        }
                        enums.put(rl, (Class<Enum<?>>) nativeType);
                    }
                }
            }
        }
        
    }
    
    public Map<ResourceLocation, Class<Enum<?>>> getEnums() {
        
        return enums;
    }
    
}
