package com.blamejared.crafttweaker.impl.plugin.core;

import com.blamejared.crafttweaker.api.plugin.ITaggableElementRegistrationHandler;
import com.blamejared.crafttweaker.api.tag.manager.ITagManager;
import com.blamejared.crafttweaker.api.tag.manager.TagManagerFactory;
import net.minecraft.resources.ResourceKey;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

final class TaggableElementsRegistrationHandler implements ITaggableElementRegistrationHandler {
    
    record Elementdata(String loader, ResourceKey<?> key, Class<?> elementClass) {}
    
    record Managerdata(String loader, ResourceKey<?> key, TagManagerFactory<?, ?> factory) {}
    
    private final List<TaggableElementsRegistrationHandler.Elementdata> elementRequests;
    private final List<TaggableElementsRegistrationHandler.Managerdata> managerRequests;
    
    
    private TaggableElementsRegistrationHandler() {
        
        this.elementRequests = new ArrayList<>();
        this.managerRequests = new ArrayList<>();
    }
    
    static TaggableElementsRegistrationHandler of(final Consumer<ITaggableElementRegistrationHandler> consumer) {
        
        final TaggableElementsRegistrationHandler handler = new TaggableElementsRegistrationHandler();
        consumer.accept(handler);
        return handler;
    }
    
    @Override
    public <T> void registerTaggableElement(String loader, ResourceKey<T> key, Class<T> elementClass) {
        
        this.elementRequests.add(new Elementdata(loader, key, elementClass));
    }
    
    @Override
    public <T, U extends ITagManager<T>> void registerManager(String loader, ResourceKey<T> key, TagManagerFactory<T, U> factory) {
        
        this.managerRequests.add(new Managerdata(loader, key, factory));
    }
    
    List<TaggableElementsRegistrationHandler.Elementdata> elementRequests() {
        
        return Collections.unmodifiableList(this.elementRequests);
    }
    
    List<TaggableElementsRegistrationHandler.Managerdata> managerRequests() {
        
        return Collections.unmodifiableList(this.managerRequests);
    }
    
}
