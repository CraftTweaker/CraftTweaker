package com.blamejared.crafttweaker.api.plugin;

import com.blamejared.crafttweaker.api.tag.manager.ITagManager;
import com.blamejared.crafttweaker.api.tag.manager.TagManagerFactory;
import net.minecraft.resources.ResourceKey;

public interface ITaggableElementRegistrationHandler {
    
    <T> void registerTaggableElement(final String loader, final ResourceKey<T> key, final Class<T> elementClass);
    
    <T, U extends ITagManager<T>> void registerManager(final String loader, final ResourceKey<T> key, final TagManagerFactory<T, U> factory);
    
    
}
