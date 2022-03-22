package com.blamejared.crafttweaker.api.tag.manager;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

@FunctionalInterface
public interface TagManagerFactory<T, U extends ITagManager<?>> {
    
    U apply(ResourceKey<? extends Registry<T>> resourceKey, Class<T> tClass);
    
}
