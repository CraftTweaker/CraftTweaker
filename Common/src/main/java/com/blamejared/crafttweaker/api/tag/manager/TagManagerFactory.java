package com.blamejared.crafttweaker.api.tag.manager;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

@FunctionalInterface
public interface TagManagerFactory<T, U extends ITagManager<?>> {
    
    /**
     * Makes a new {@link ITagManager} from the given key and class
     *
     * @param resourceKey The key of the registry that the manager acts on.
     * @param tClass      The type of element that the manager will manage. This can be ignored.
     *
     * @return a new ITagManager from the given key and class.
     */
    U apply(ResourceKey<? extends Registry<T>> resourceKey, Class<T> tClass);
    
}
