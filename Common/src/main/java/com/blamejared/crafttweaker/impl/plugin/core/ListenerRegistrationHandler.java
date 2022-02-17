package com.blamejared.crafttweaker.impl.plugin.core;

import com.blamejared.crafttweaker.api.plugin.IListenerRegistrationHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

final class ListenerRegistrationHandler implements IListenerRegistrationHandler {
    
    private final List<Runnable> listeners;
    
    private ListenerRegistrationHandler() {
        
        this.listeners = new ArrayList<>();
    }
    
    static List<Runnable> gather(final Consumer<IListenerRegistrationHandler> consumer) {
        
        final ListenerRegistrationHandler handler = new ListenerRegistrationHandler();
        consumer.accept(handler);
        return Collections.unmodifiableList(handler.listeners);
    }
    
    @Override
    public void registerCraftTweakerLoadCompleteListener(final Runnable runnable) {
        
        this.listeners.add(runnable);
    }
    
}
