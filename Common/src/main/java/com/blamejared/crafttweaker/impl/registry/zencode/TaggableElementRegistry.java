package com.blamejared.crafttweaker.impl.registry.zencode;

import com.blamejared.crafttweaker.api.tag.manager.ITagManager;
import com.blamejared.crafttweaker.api.tag.manager.TagManagerFactory;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TaggableElementRegistry {
    
    private final Map<ResourceKey<?>, TagManagerFactory<?, ?>> managerData = new HashMap<>();
    private final Map<ResourceKey<?>, Class<?>> elementData = new HashMap<>();
    
    public <T, U extends ITagManager<T>> void registerManager(final IScriptLoader loader, final ResourceKey<T> id, TagManagerFactory<T, U> factory) {
        
        TagManagerFactory<?, ?> old = this.managerData.get(id);
        if(old != null) {
            throw new IllegalArgumentException("Attempted taggable element manager overriding on id " + id + ": old " + old + ", new " + factory);
        }
        this.managerData.put(id, factory);
    }
    
    public <T> void registerElement(final IScriptLoader loader, final ResourceKey<T> id, final Class<T> clazz) {
        
        final Class<?> old = this.elementData.get(id);
        if(old != null) {
            throw new IllegalArgumentException("Attempted taggable element overriding on id " + id + ": old " + old.getName() + ", new " + clazz.getName());
        }
        this.elementData.put(id, clazz);
    }
    
    public <T> Optional<Class<T>> getTaggableElement(final ResourceKey<T> key) {
        
        return Optional.ofNullable(elementData.get(key)).map(it -> (Class<T>) it);
    }
    
    public Map<ResourceKey<?>, Class<?>> getTaggableElements() {
        
        return ImmutableMap.copyOf(this.elementData);
    }
    
    public <T> Optional<TagManagerFactory<T, ? extends ITagManager<T>>> getManager(final ResourceKey<Registry<T>> key) {
        
        return Optional.ofNullable(managerData.get(key)).map(it -> (TagManagerFactory<T, ? extends ITagManager<T>>) it);
    }
    
    public Map<ResourceKey<?>, TagManagerFactory<?, ?>> getManagers() {
        
        return ImmutableMap.copyOf(this.managerData);
    }
    
}
