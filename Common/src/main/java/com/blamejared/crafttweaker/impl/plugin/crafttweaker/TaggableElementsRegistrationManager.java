package com.blamejared.crafttweaker.impl.plugin.crafttweaker;

import com.blamejared.crafttweaker.api.annotation.TaggableElementManagerFactory;
import com.blamejared.crafttweaker.api.plugin.ITaggableElementRegistrationHandler;
import com.blamejared.crafttweaker.api.tag.manager.ITagManager;
import com.blamejared.crafttweaker.api.tag.manager.TagManagerFactory;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.api.util.InstantiationUtil;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.blamejared.crafttweaker_annotations.annotations.TaggableElement;
import net.minecraft.ResourceLocationException;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

final class TaggableElementsRegistrationManager {
    
    void attemptRegistration(final Class<?> clazz, final String loader, final ITaggableElementRegistrationHandler handler) {
        
        if(clazz.isAnnotationPresent(NativeTypeRegistration.class)) {
            this.tryNativeRegistration(clazz, loader, handler);
        } else {
            this.tryClassRegistration(clazz, loader, handler);
        }
    }
    
    private void tryClassRegistration(final Class<?> clazz, final String loader, final ITaggableElementRegistrationHandler handler) {
        
        if(clazz.isAnnotationPresent(TaggableElement.class)) {
            this.tryElementRegistration(clazz, loader, clazz.getAnnotation(TaggableElement.class), handler);
        }
        
        if(clazz.isAnnotationPresent(TaggableElementManagerFactory.class)) {
            if(!TagManagerFactory.class.isAssignableFrom(clazz)) {
                throw new IllegalArgumentException("Provided manager factory class: '" + clazz + "' is not an instance of TagManagerFactory!");
            }
            this.tryManagerRegistration(clazz, loader, clazz.getAnnotation(TaggableElementManagerFactory.class), handler);
        }
        
    }
    
    private void tryNativeRegistration(final Class<?> clazz, final String loader, final ITaggableElementRegistrationHandler handler) {
        
        if(!clazz.isAnnotationPresent(TaggableElement.class)) {
            return;
        }
        
        final NativeTypeRegistration ntr = clazz.getAnnotation(NativeTypeRegistration.class);
        final Class<?> nativeType = ntr.value();
        
        this.tryElementRegistration(nativeType, loader, clazz.getAnnotation(TaggableElement.class), handler);
    }
    
    private void tryElementRegistration(final Class<?> clazz, final String loader, final TaggableElement data, final ITaggableElementRegistrationHandler handler) {
        
        try {
            final ResourceLocation id = new ResourceLocation(data.value());
            final ResourceKey<Registry<Object>> registryKey = ResourceKey.createRegistryKey(id);
            handler.registerTaggableElement(loader, registryKey, GenericUtil.uncheck(clazz));
        } catch(final ResourceLocationException e) {
            throw new IllegalArgumentException("Provided resource location '" + data.value() + "' for taggable element " + clazz.getName() + " is invalid", e);
        }
    }
    
    private void tryManagerRegistration(final Class<?> clazz, final String loader, final TaggableElementManagerFactory data, final ITaggableElementRegistrationHandler handler) {
        
        try {
            final ResourceLocation id = new ResourceLocation(data.value());
            final ResourceKey<Registry<Object>> registryKey = ResourceKey.createRegistryKey(id);
            
            handler.registerManager(loader, registryKey, InstantiationUtil.getOrCreateInstance((Class<? extends TagManagerFactory<Registry<Object>, ITagManager<Registry<Object>>>>) clazz));
        } catch(final ResourceLocationException e) {
            throw new IllegalArgumentException("Provided resource location '" + data.value() + "' for taggable element " + clazz.getName() + " is invalid", e);
        }
    }
    
}
