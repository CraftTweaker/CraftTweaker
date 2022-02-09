package com.blamejared.crafttweaker.impl.registry.zencode;

import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.resources.ResourceLocation;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Contains all info on Bracket resolvers, validators and dumpers
 */
public final class EnumBracketRegistry {
    
    private final Map<ResourceLocation, Class<Enum<?>>> enums = new HashMap<>();
    private final Map<ResourceLocation, Class<Enum<?>>> view = Collections.unmodifiableMap(this.enums);
    
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
    
    @SuppressWarnings("unchecked")
    public <T extends Enum<T>> Optional<Class<T>> getEnum(final ResourceLocation type) {
        
        return Optional.ofNullable(this.enums.get(type)).map(it -> (Class<T>) it);
    }
    
    
    public Map<ResourceLocation, Class<Enum<?>>> getEnums() {
        
        return this.view;
    }
    
}
