package com.blamejared.crafttweaker.platform.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class ForgeRegistryWrapper<T extends IForgeRegistryEntry<T>> implements RegistryWrapper<T> {
    
    private final IForgeRegistry<T> registry;
    
    public ForgeRegistryWrapper(IForgeRegistry<T> registry) {
        
        this.registry = registry;
    }
    
    @Override
    public Optional<T> getOptional(ResourceLocation location) {
        
        return Optional.ofNullable(registry.getValue(location));
    }
    
    @Override
    public ResourceLocation getKey(T object) {
        
        return registry.getKey(object);
    }
    
    @Override
    public T get(ResourceLocation location) {
        
        return registry.getValue(location);
    }
    
    @Override
    public Stream<T> stream() {
        
        return StreamSupport.stream(registry.spliterator(), false);
    }
    
    @Override
    public boolean containsKey(ResourceLocation location) {
        
        return registry.containsKey(location);
    }
    
    @Override
    public Set<ResourceLocation> keySet() {
        
        return registry.getKeys();
    }
    
}
