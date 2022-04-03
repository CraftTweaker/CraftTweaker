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
    
    void attemptRegistration(final Class<?> clazz, final ITaggableElementRegistrationHandler handler) {
        
        if(clazz.isAnnotationPresent(NativeTypeRegistration.class)) {
            this.tryNativeRegistration(clazz, handler);
        } else {
            this.tryClassRegistration(clazz, handler);
        }
    }
    
    private void tryClassRegistration(final Class<?> clazz, final ITaggableElementRegistrationHandler handler) {
        
        if(clazz.isAnnotationPresent(TaggableElement.class)) {
            this.tryElementRegistration(clazz, clazz.getAnnotation(TaggableElement.class), handler);
        }
        
        if(clazz.isAnnotationPresent(TaggableElementManagerFactory.class)) {
            if(!TagManagerFactory.class.isAssignableFrom(clazz)) {
                throw new IllegalArgumentException("Provided manager factory class: '" + clazz + "' is not an instance of TagManagerFactory!");
            }
            this.tryManagerRegistration(clazz, clazz.getAnnotation(TaggableElementManagerFactory.class), handler);
        }
        
    }
    
    private void tryNativeRegistration(final Class<?> clazz, final ITaggableElementRegistrationHandler handler) {
        
        if(!clazz.isAnnotationPresent(TaggableElement.class)) {
            return;
        }
        
        final NativeTypeRegistration ntr = clazz.getAnnotation(NativeTypeRegistration.class);
        final Class<?> nativeType = ntr.value();
        
        this.tryElementRegistration(nativeType, clazz.getAnnotation(TaggableElement.class), handler);
    }
    
    private void tryElementRegistration(final Class<?> clazz, final TaggableElement data, final ITaggableElementRegistrationHandler handler) {
        
        try {
            final ResourceLocation id = new ResourceLocation(data.value());
            final ResourceKey<Registry<Object>> registryKey = ResourceKey.createRegistryKey(id);
            handler.registerTaggableElement(registryKey, GenericUtil.uncheck(clazz));
        } catch(final ResourceLocationException e) {
            throw new IllegalArgumentException("Provided resource location '" + data.value() + "' for taggable element " + clazz.getName() + " is invalid", e);
        }
    }
    
    private void tryManagerRegistration(final Class<?> clazz, final TaggableElementManagerFactory data, final ITaggableElementRegistrationHandler handler) {
        
        try {
            final ResourceLocation id = new ResourceLocation(data.value());
            final ResourceKey<Registry<Object>> registryKey = ResourceKey.createRegistryKey(id);
            
            handler.registerManager(GenericUtil.uncheck(registryKey), InstantiationUtil.getOrCreateInstance((Class<? extends TagManagerFactory<Registry<Object>, ITagManager<?>>>) clazz));
        } catch(final ResourceLocationException e) {
            throw new IllegalArgumentException("Provided resource location '" + data.value() + "' for taggable element " + clazz.getName() + " is invalid", e);
        }
    }
    
}
