package com.blamejared.crafttweaker.platform.registry;

import net.minecraft.resources.ResourceLocation;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public interface RegistryWrapper<T> {
    
    Optional<T> getOptional(ResourceLocation location);
    
    ResourceLocation getKey(T object);
    
    T get(ResourceLocation location);
    
    Stream<T> stream();
    
    Set<ResourceLocation> keySet();
    
    boolean containsKey(ResourceLocation location);
    
    default Stream<ResourceLocation> keyStream() {
        
        return keySet().stream();
    }
    
    default Stream<T> getForNamespace(String namespace) {
        
        return this.keyStream()
                .filter(resourceLocation -> resourceLocation.getNamespace().equals(namespace))
                .map(location -> getOptional(location).orElseThrow(() -> new IllegalArgumentException("Cannot get registry object from name: '" + location + "'! This should never happen!")));
    }
    
}
