package com.blamejared.crafttweaker.platform.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public class VanillaRegistryWrapper<T> implements RegistryWrapper<T> {
    
    private final Registry<T> registry;
    
    public VanillaRegistryWrapper(Registry<T> registry) {
        
        this.registry = registry;
    }
    
    @Override
    public Optional<T> getOptional(ResourceLocation location) {
        
        return registry.getOptional(location);
    }
    
    @Override
    public ResourceLocation getKey(T object) {
        
        return registry.getKey(object);
    }
    
    @Override
    public T get(ResourceLocation location) {
        
        return registry.get(location);
    }
    
    @Override
    public Stream<T> stream() {
        
        return registry.stream();
    }
    
    @Override
    public boolean containsKey(ResourceLocation location) {
        
        return registry.containsKey(location);
    }
    
    @Override
    public Set<ResourceLocation> keySet() {
        
        return registry.keySet();
    }
    
}
