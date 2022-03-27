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
    
    record ElementData(ResourceKey<?> key, Class<?> elementClass) {}
    
    record ManagerData(ResourceKey<?> key, TagManagerFactory<?, ?> factory) {}
    
    private final List<ElementData> elementRequests;
    private final List<ManagerData> managerRequests;
    
    
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
    public <T> void registerTaggableElement(ResourceKey<T> key, Class<T> elementClass) {
        
        this.elementRequests.add(new ElementData(key, elementClass));
    }
    
    @Override
    public <T, U extends ITagManager<?>> void registerManager(ResourceKey<T> key, TagManagerFactory<T, U> factory) {
        
        this.managerRequests.add(new ManagerData(key, factory));
    }
    
    List<ElementData> elementRequests() {
        
        return Collections.unmodifiableList(this.elementRequests);
    }
    
    List<ManagerData> managerRequests() {
        
        return Collections.unmodifiableList(this.managerRequests);
    }
    
}
