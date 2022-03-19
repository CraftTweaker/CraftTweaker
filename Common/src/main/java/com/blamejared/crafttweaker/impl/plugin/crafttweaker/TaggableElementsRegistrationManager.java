package com.blamejared.crafttweaker.impl.plugin.crafttweaker;

import com.blamejared.crafttweaker.api.plugin.ITaggableElementRegistrationHandler;
import com.blamejared.crafttweaker.api.tag.manager.ITagManager;
import com.blamejared.crafttweaker.api.tag.manager.TagManagerFactory;
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
        
        if(!clazz.isAnnotationPresent(TaggableElement.class)) {
            return;
        }
        
        this.tryRegistration(clazz, loader, clazz.getAnnotation(TaggableElement.class), handler);
    }
    
    private void tryNativeRegistration(final Class<?> clazz, final String loader, final ITaggableElementRegistrationHandler handler) {
        
        if(!clazz.isAnnotationPresent(TaggableElement.class)) {
            return;
        }
        
        final NativeTypeRegistration ntr = clazz.getAnnotation(NativeTypeRegistration.class);
        final Class<?> nativeType = ntr.value();
        
        this.tryRegistration(nativeType, loader, clazz.getAnnotation(TaggableElement.class), handler);
    }
    
    private void tryRegistration(final Class<?> clazz, final String loader, final TaggableElement data, final ITaggableElementRegistrationHandler handler) {
        
        try {
            final ResourceLocation id = new ResourceLocation(data.value());
            final ResourceKey<Registry<Object>> registryKey = ResourceKey.createRegistryKey(id);
            handler.registerTaggableElement(loader, registryKey, this.uncheck(clazz));
            Class<?> managerFactoryClass = data.managerFactoryClass();
            if(!managerFactoryClass.equals(Object.class)) {
                if(!TagManagerFactory.class.isAssignableFrom(managerFactoryClass)) {
                    throw new IllegalArgumentException("Provided manager factory class: '" + managerFactoryClass + "' is not an instance of TagManagerFactory!");
                }
                handler.registerManager(loader, registryKey, InstantiationUtil.getOrCreateInstance((Class<? extends TagManagerFactory<Registry<Object>, ITagManager<Registry<Object>>>>) managerFactoryClass));
            }
        } catch(final ResourceLocationException e) {
            throw new IllegalArgumentException("Provided resource location '" + data.value() + "' for taggable element " + clazz.getName() + " is invalid", e);
        }
    }
    
    private <T, U> T uncheck(final U u) {
        
        return (T) u;
    }
    
}
